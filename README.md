# Replicated Log
Echo Client-Server application


## Run in Docker 
1. execute command: mvn clean package
2. run docker-compose.yml file

Master server base URL: http://localhost:8080/master/

Available endpoints: 
- POST: master/messages/{writeConcern}
body {some text}
- GET: master/messages
- GET: master/health
- POST: master/acknowledges
(for communication between client and servers)

