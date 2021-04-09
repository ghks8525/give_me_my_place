const http = require("http");
// const mysql = require("mysql");
const config = require("./config"); //config.js파일 require

var conn = config.getConnection;

// var conn = mysql.createConnection({
//   host: "localhost",
//   user: "root",
//   password: "password",
//   database: "nodejsdb",
// });

http
  .createServer((req, res) => {
    conn.query("select * from users ", (err, results, fields) => {
      if (err) throw err;
      console.log(results);
      res.end();
    });
  })
  .listen(8001, () => {
    console.log("8001 : server start!");
  });
