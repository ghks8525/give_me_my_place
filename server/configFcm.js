var Fcm = require("fcm-node");

var serverkey = "AAAAqLmQ1K4:APA91bGTmF6JQYA0p4sfNhRtf6yTTkiOkO6MasM2po0tjOl9L7x4MkMjKPfrE2rs93t4NKPD9IkMJLWXPXstnCUXGkisx0lIVGxTKHAjmZZu8yIXiHY4xE33nZ4djRggJagzwytNp-IV"
var client_token = ""

var fcm = new FCM(serverKey);

// fcm.send(push_data, function (err, response) {
//     if (err) {
//         console.error('Push�޽��� �߼ۿ� �����߽��ϴ�.');
//         console.error(err);
//         return;
//     }

//     console.log('Push�޽����� �߼۵Ǿ����ϴ�.');
//     console.log(response);
// });