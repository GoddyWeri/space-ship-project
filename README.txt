##PROJECT
This project was made with the MVC architecture(Model, View, Controller), for best practices of encapsulation, High coherence and low cohesion.

##Docker
To build dockerize the app and run follow the steps:
1. run docker build -t space-ship-project-image:tag . for example
2. Then run the image


##Kafka(Topic: spaceship-topic)
1. Open cmd, Move to directory where Kafka folder is stored
2. First start the Zookeeper server with 

bin/zookeeper-server-start.sh config/zookeeper.properties
or
bin/kafka-server-start.sh config/server.properties

In Windows:
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
OR
.\bin\windows\kafka-server-start.bat .\config\server.properties

3. You can now acces the API concerned.