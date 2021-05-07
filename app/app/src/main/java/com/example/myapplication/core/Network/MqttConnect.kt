package com.example.myapplication.core.Network

import android.content.Context
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttClient

class MqttConnect(url:String, context: Context) {

    var mqttAndroidClient =  MqttAndroidClient(context, url, MqttClient.generateClientId())
}