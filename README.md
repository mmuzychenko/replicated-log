# Replicated Log
Echo Client-Server application

## Run in Docker 
1. Execute command: ```mvn clean package```
2. Run docker-compose.yml file<br/>
``docker-compose up -d``
3. Stop<br/>
``docker-compose down -v``

Master server base URL: http://localhost:8080/master

## API
Available endpoints: 
- POST: /messages/{w}<br/>
  (Appends a message into the secondary service)<br/>
  w value specifies how many ACKs the master should receive from secondaries before
  responding to the client<br/>
  w = 1 - only from master<br/>
  w = 2 - from master and one secondary<br/>
  w = 3 - from master and two secondaries<br/>
  Request body example<br/>
```{some text}```
<br/>
<br/>
- GET: /messages<br />
(Returns all replicated messages from secondaries services)<br />
<br/>

