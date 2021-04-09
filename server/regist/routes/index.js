var express = require('express');
var router = express.Router();
const userController = require("../controllers")


router.get('/',userController.test)
router.get("/regist",userController.regist)
router.get("/login",userController.login)
router.get("/seatinfo",userController.setStartInfo)
router.get("/registseat",userController.registSeats)

module.exports = router;