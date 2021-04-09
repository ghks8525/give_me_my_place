function test(req,res){
  
}

function regist(req, res){
  var account = {
    name: req.name,
    Id: req.id,
    pw: req.pw,
    school: req.school,
    warningLevel: req.warningLevel,
    token: req.token,
    authFlag: req.authFlag,
  };


  res.status(200).json(
    {
      "retcode": 0,
      "retmsg": "success"
    }
  )
}

function login(req, res){
  var id = req.id;
  var pw = req.pw;


  res.status(200).json(
    {
      "retcode": 0,
      "retmsg": "success"
    }
  )
}

function setStartInfo(req, res){
  var account = {
    name: req.name,
    Id: req.id,
    pw: req.pw,
    school: req.school,
    warningLevel: req.warningLevel,
    token: req.token,
    authFlag: req.authFlag,
  };


  res.status(200).json(
    {
      "retcode": 0,
      "retmsg": "success",
      "seatInfo": {"account":"",
        "startedTime":"",
        "outTime":"",
        "warning":"",
        "seatIdx":""}
    });
}

function registSeats(req, res){
  var account = {
    name: req.name,
    Id: req.id,
    pw: req.pw,
    school: req.school,
    warningLevel: req.warningLevel,
    token: req.token,
    authFlag: req.authFlag,
  };
  var flag = req.flag;


  res.status(200).json(
    {
      "retcode": 0,
      "retmsg": "success"
    }
  )
}
  
  module.exports = {
    test: test,
    regist: regist,
    login: login,
    setStartInfo: setStartInfo,
    registSeats: registSeats
  }