const http = require("http"); //(1)

http
  .createServer((request, response) => {
    //(2)

    response.write("<h1>Hello Node!</h1>");

    response.end("<p>Hello Server!</p>");
  })
  .listen(8080, () => {
    //(3)

    console.log("server1 : 8080포트 대기 중");
  });
