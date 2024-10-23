#Build and run
In project root folder, i.e. project0am
To compile:
1. javac -d bin --source-path . ./*.java Card/*.java
1. javac --source-path src src/sg/edu/nus/iss/baccarat/**/*.java -d classes

To run the compile bytecode (.class) main entrypoint
java -cp bin <packageName>,<className>

2. java -cp bin project01am.App
2.java -cp classes sg.edu.nus.iss.baccarat.client.ClientApp localhost:12345
2.java -cp classes sg.edu.nus.iss.baccarat.server.ServerApp 12345 4

To package into jar fil, go into the bin folder
3/ jar -cvf day09.jar -e project01am.App .

To run the jar file insdie the bin folder