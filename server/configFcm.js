var Fcm = require("fcm-node");

var serverkey = "AAAAqLmQ1K4:APA91bGTmF6JQYA0p4sfNhRtf6yTTkiOkO6MasM2po0tjOl9L7x4MkMjKPfrE2rs93t4NKPD9IkMJLWXPXstnCUXGkisx0lIVGxTKHAjmZZu8yIXiHY4xE33nZ4djRggJagzwytNp-IV"
var client_token = ""

var fcm = new FCM(serverKey);

// fcm.send(push_data, function (err, response) {
//     if (err) {
//         console.error('Push메시지 발송에 실패했습니다.');
//         console.error(err);
//         return;
//     }

//     console.log('Push메시지가 발송되었습니다.');
//     console.log(response);
// });