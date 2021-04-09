const http = require("http");
const fs = require("fs"); //(1)

http
  .createServer((request, response) => {
    fs.readFile("./server2.html", (err, data) => {
      //(2)

      if (err) {
        throw err;
      }

      response.end(data);
    });
  })
  .listen(8081, () => {
    console.log("server2 : 8081번 포트에서 대기 중");
  });
