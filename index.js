//Add allowed IPs to this array!
const allowed_ips = ["::ffff:127.0.0.1"];
////////////////////////////////////////




const express = require("express");
const ipfilter = require('express-ipfilter').IpFilter
const app = express();
const path = require('path');
const router = express.Router();
app.listen(2000, () => console.log("\nHesperius' Minecraft-Advertisement-API was enabled successfully!"));
app.use(express.json());



let current = new Set();
let complete = new Set();


app.get('/verify/:id', (request, response) => {
  let id = request.params.id;
  for (let cr of current) {
    if (cr.link == id) {
      complete.add(cr.UUID);
      current.delete(cr);
      response.sendFile(path.join(__dirname + '/success.html'));
      return;
    }
  }
  response.sendFile(path.join(__dirname + '/error.html'));
});

app.use(ipfilter(allowed_ips, { mode: 'allow' }));

app.post("/hasverified", (request, response) => {
  console.log(request.body.UUID);
  for (let ud of complete) {
    if (request.body.UUID == ud) {
      response.send({
        completed: true
      });
      console.log(true);
      complete.delete(ud);
      return;
    }
  }
  console.log(false);
  response.send({
    completed: false
  });
});

app.post("/getcomplete", (request, response) => {
  console.log(request.body);
  let rs = { active: [] };
  let counter = 0;
  if (complete.size == 0) {
    response.send();
    return;
  }
  for (let id of complete) {
    rs.active[counter] = id;
    counter += 1;
    complete.delete(id);
  }
  response.send(rs);
});

app.post('/add', function (request, response) {
  console.log(request.body);      
  current.add({
    UUID: request.body.UUID,
    link: request.body.link,
  });
  response.send({
    proceed: true
  });    
});













