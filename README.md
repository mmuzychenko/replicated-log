# Replicated Log
Echo Client-Server application


## Run in Docker 
1. Execute command: mvn clean package
2. Run docker-compose.yml file

Master server base URL: http://localhost:8080/master/

# API<br />
Available endpoints: 
- POST: master/messages/{w}<br />
  (appends a message into the in-memory list)<br />
  w value specifies how many ACKs the master should receive from secondaries before
  responding to the client<br />
  w = 1 - only from master<br />
  w = 2 - from master and one secondary<br />
  w = 3 - from master and two secondaries<br />
  body example {some text}<br />
<br />
- GET: master/messages<br />
(returns all replicated messages from the in-memory list)<br />
  <br />
- GET: master/health<br />
  (check secondaries’ health (status):
  Healthy -> Suspected -> Unhealthy.)<br />
<br />
- POST: master/acknowledges<br />
(for communication between client and servers)

