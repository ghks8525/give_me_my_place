const mysql = require("mysql");

module.exports = {
  getConnection: mysql.createConnection({
    host: "localhost",
    user: "root",
    port: 3305,
    password: "123456",
    database: "give_me_db",
  }),
};
