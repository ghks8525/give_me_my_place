package com.example.myapplication.core.net

import android.graphics.BitmapFactory
import com.example.myapplication.core.sys.Config
import com.example.myapplication.core.sys.Const
import com.example.myapplication.core.sys.Trace
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.*
import java.net.URLEncoder


@Suppress("UNREACHABLE_CODE")
abstract class AbstractHttpProtocol<RES> : HttpRequestable
{
    private var mnMethod: Int = HttpConst.HTTP_GET
    private var mRequestHeaderMap: MutableMap<String, String> = mutableMapOf()
    private var mQueryMap: MutableMap<String, String> = mutableMapOf()
    private var mResponseHeaderMap: Map<String, List<String>>? = null
    private var mBodyObject: Any? = "{}"
    private var mJsonBody: String? = null
    private var mResponsable: HttpResponsable<RES>? = null
    private var mResponseData: Class<*>? = null
    private var mRedirectionUrl: String? = null
    private var mnRetyCountMax: Int = HttpConst.HTTP_RETRY_COUNT_MAX
    private var mnRetyCount: Int = 0

    fun setMethod(nMethod: Int) {
        mnMethod = nMethod
    }

    override fun getMethod(): Int = mnMethod

    override fun getDomain(): String {
        return if (Config.SUPPORT_DEBUG) {
            Const.NETWORK.DEV_SERVER.DOMAIN
        } else {
            Const.NETWORK.OPR_SERVER.DOMAIN
        }
    }

    fun getPath(): String {
        return if (Config.SUPPORT_DEBUG) {
            Const.NETWORK.DEV_SERVER.PATH
        } else {
            Const.NETWORK.OPR_SERVER.PATH
        }
    }

    abstract override fun getUrl(): String

    override fun setRedirection(url: String?) {
        mRedirectionUrl = url
    }

    override fun getRedirection() = mRedirectionUrl

    override fun getConnectTimeout(): Int = HttpConst.HTTP_CONNECT_TIMEOUT

    override fun getReadTimeout(): Int = HttpConst.HTTP_READ_TIMEOUT

    override fun getRequestHeaderMap(): Map<String, String> = mRequestHeaderMap

    fun addRequestHeader(strKey: String, strVal: String) {
        mRequestHeaderMap[strKey] = strVal
    }

    override fun getRequestQueryMap(): Map<String, String> = mQueryMap

    fun addQuery(strKey: String, strVal: String) {
        mQueryMap[strKey] = strVal
    }

    fun getQuery(strKey: String) : String? = mQueryMap[strKey]

    fun getQueries(): String {
        var strQuery = ""

        if (mQueryMap.isNotEmpty()) {
            strQuery += "?"

            for ((k, v) in mQueryMap) {
                strQuery += String.format("%s=%s&", k, URLEncoder.encode(v, "UTF-8"))
            }

            strQuery = strQuery.substring(0 until strQuery.length - 1)
        }

        return strQuery
    }

    override fun getContentType(): String = HttpConst.HTTP_MIME_TYPE_JSON

    override fun getRequestBody(): Any? = mBodyObject

    fun setRequestBody(obj: Any) {
        mBodyObject = obj
    }

    override fun getJsonRequestBody(): String? = mJsonBody

    fun setJsonRequestBody(obj: Any) {
        setJsonRequestBody(obj, false)
    }

    fun setJsonRequestBody(obj: Any, bExcludeExpose: Boolean) {
        mBodyObject = obj

        var gson: Gson = if (bExcludeExpose) {
            GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        } else {
            Gson()
        }

        mJsonBody = gson.toJson(mBodyObject)
    }

    override fun setResponseHeaderMap(responseHeaders: Map<String, List<String>>?) {
        mResponseHeaderMap = responseHeaders
    }

    override fun getResponseHeader(strKey: String, nSubIndex: Int): String {
        Trace.debug("++ getResponseHeader()")   // mResponseHeaderMap = $mResponseHeaderMap")

        var strVal: String? = null
        val values: List<String>? = mResponseHeaderMap?.get(strKey)
        values?: return ""

        strVal = values[0]

        if (nSubIndex < 0) {
            return strVal
        }

        val nIndex = strVal.indexOf(";")

        if (nIndex <= 0) {
            return strVal
        }

        val strVals = strVal.split(";")

        return if (nSubIndex < strVals.size) {
            strVals[nSubIndex]
        } else strVal
    }

    fun setHttpResponsable(responsable: HttpResponsable<RES>) {
        var strClassName: String = responsable.javaClass.genericInterfaces[0].toString()
        strClassName = strClassName.substring(strClassName.indexOf("<") + 1, strClassName.indexOf(">"))
        Trace.debug(">> setHttpResponsable() strClassName = $strClassName")

        mResponseData = Class.forName(strClassName)
        mResponsable = responsable
    }

    @Suppress("UNCHECKED_CAST")
    override fun parse(responseBody: Any?) {
        Trace.debug("++ parse()")
        var parsedObject: RES? = null

        if (responseBody == null) {
            mResponsable?.onFailure(HttpConst.Error.HTTP_DATA_NOT_EXIST.getCode(),
                HttpConst.Error.HTTP_DATA_NOT_EXIST.getMessage())
            return
        }

        if ((mResponsable == null) or (mResponseData == null)) {
            Trace.debug("-- parse() mResponsable or mResponseData is null")
            return
        }

        when (getResponseHeader(HttpConst.HTTP_HEADER_CONTENT_TYPE, 0)) {
            HttpConst.HTTP_MIME_TYPE_HTML -> {
                requestFailed(HttpConst.Error.HTTP_NOT_SUPPORTED_CONTENT_TYPE.getCode(),
                    HttpConst.Error.HTTP_NOT_SUPPORTED_CONTENT_TYPE.getMessage())

                return
            }

            HttpConst.HTTP_MIME_TYPE_JSON -> {
                if (mResponseData!!.isAssignableFrom(android.graphics.Bitmap::class.java) ||
                    mResponseData!!.isAssignableFrom(java.io.File::class.java)) {
                    mResponsable?.onFailure(HttpConst.Error.HTTP_DATA_NOT_EXIST.getCode(),
                        HttpConst.Error.HTTP_DATA_NOT_EXIST.getMessage())
                    return
                }

                val gson: Gson = Gson()
                parsedObject = gson.fromJson(responseBody as String, mResponseData) as RES
            }

            HttpConst.HTTP_MIME_TYPE_JPEG,
            HttpConst.HTTP_MIME_TYPE_PNG,
            HttpConst.HTTP_MIME_TYPE_GIF,
            -> {
                parsedObject = BitmapFactory.decodeStream(responseBody as InputStream) as RES
            }

            HttpConst.HTTP_MIME_TYPE_APK -> {
                mResponsable ?: return
                val apkFileName: String = getUrl().substring(getUrl().lastIndexOf('/') + 1)
                return
            }

            HttpConst.HTTP_MIME_TYPE_JAR -> {
                mResponsable ?: return
                val jarFileName: String = getUrl().substring(getUrl().lastIndexOf('/') + 1)

                return
            }

            HttpConst.HTTP_MIME_TYPE_OBJECT -> {
                parsedObject = responseBody as RES
            }

            else -> {
                requestFailed(HttpConst.Error.HTTP_NOT_SUPPORTED_CONTENT_TYPE.getCode(),
                    HttpConst.Error.HTTP_NOT_SUPPORTED_CONTENT_TYPE.getMessage())
                return
            }
        }

        if (mResponsable == null) {
            Trace.debug("-- parse() mResponsable is null")
        } else {
            mResponsable?.onResponse(parsedObject)
        }
    }

    fun downloadFile(inputStream: InputStream, file: File): Long {
        val strContentLength = getResponseHeader(HttpConst.HTTP_HEADER_CONTENT_LENGTH, 0)
        val lContentLength = if (strContentLength.isNullOrEmpty()) -1L else strContentLength.toLong()
        var lDownloaded: Long = 0
        var downloadable: HttpDownloadable<RES>? = null
        Trace.debug("++ downloadFile() = ${file.name} ( ${lContentLength}bytes )")

        try {
            downloadable = mResponsable as HttpDownloadable?
        } catch (e: ClassCastException) {
            Trace.debug(">> ${e.message}")
        }

        downloadable?.onStart(lContentLength)

        try {
            inputStream.use { `is` ->
                FileOutputStream(file).use { fos ->
                    val bBuf = ByteArray(Const.STORAGE.FILE_READ_BUFFER_SIZE)
                    var n = 0
                    while (inputStream.read(bBuf).also { n = it } > 0) {
                        fos.write(bBuf, 0, n)
                        lDownloaded += n.toLong()
                        downloadable?.onProgress(lDownloaded, lContentLength)
                    }
                    fos.flush()
                }
            }
        } catch (fe: FileNotFoundException) {
            fe.printStackTrace()
            requestFailed(HttpConst.Error.HTTP_FILE_NOT_FOUND.getCode(),
                HttpConst.Error.HTTP_FILE_NOT_FOUND.getMessage())
        } catch (e: IOException) {
            e.printStackTrace()
            requestFailed(HttpConst.Error.HTTP_DOWNLOAD_FAIL.getCode(),
                HttpConst.Error.HTTP_DOWNLOAD_FAIL.getMessage())
        }

        downloadable?.onComplete(lDownloaded)

        Trace.debug("-- downloadFile() = ${file.name} ( ${file.length()}bytes )")
        return lDownloaded
    }

    override fun requestFailed(nError: Int, strMsg: String) {
        mResponsable?.onFailure(nError, strMsg)
    }

    override fun hasOfflineJob(): Boolean = false

    override fun processOffline() {
    }

    override fun setRetryCount(nCount: Int) {
        mnRetyCountMax = nCount
    }

    override fun retry(): Boolean {
        mnRetyCount++

        if (mnRetyCount >= mnRetyCountMax) return false

        return true
    }
}