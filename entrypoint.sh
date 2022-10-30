set -x 

Build(){
    mvn clean compile assembly:single
    chmod +x target/dualsub-1.1.0-jar-with-dependencies.jar
}

Run(){
    java -jar target/dualsub-1.1.0-jar-with-dependencies.jar
}

Build
Run