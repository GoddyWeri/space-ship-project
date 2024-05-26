##PROJECT
This project was made with the MVC architecture(Model, View, Controller), for best practices of encapsulation, High coherence and low cohesion.

##Flyway
(Custom static Scripts)
The sql script is stored in folder src/main/resources/db/migration. called V1__Create_Table.
This scripts automatically runs upon starting the application, to create the spaceship table and store some spaceships, to start with.
So that we can do some API consultations without necessarily creating new spaceships first.

##Docker
To build the docker image, and run the app, follow the following steps:
1. run docker build -t spaceship-image:tag . 
2. Then run with docker compose up -d.
To stop:
run docker compose down

OR if you want to create an image with a custom name, do the following:
1. run docker build -t your-custom-name:your-custom-tag . 
2. Then run docker run -p 8080:8080 your-custom-name:your-custom-tag



##Kafka
(Topic: spaceship-topic)
1. Open cmd, Move to directory where Kafka folder is stored
2. First start the Zookeeper server with 
3. Then start the broker

bin/zookeeper-server-start.sh config/zookeeper.properties
or
bin/kafka-server-start.sh config/server.properties

In Windows:
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

For Broker:
.\bin\windows\kafka-server-start.bat .\config\server.properties

4. You can now acces the API concerned. localhost:8080/spaceships/message-spaceships

