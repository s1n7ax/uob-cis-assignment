# Customer feedback system for online marketplace

Customer feedback system written in Java native client and Spring RESTful backend

## How to build and run

### Prerequisites
* Javafx <= 15
* Java <= 11

### Steps

* clone the project `git clone https://github.com/s1n7ax/uob-cis-assignment.git`
* clean the modules (for Eclipse IDE)

```bash
# Linux
cd uob-cis-assignment
cd modules
chmod u+x cleanEclipse.sh
./cleanEclipse.sh

# Windows
# go to modules and open all sub modules
# open CMD in each project root
.\gradlew cleanEclipse eclipse
```

* import all projects in to Eclipse
* right click on each project and "Gradle -> Refresh Gradle Project"
* select the client project ("uob-cis-assignment-client") and click on Run icon
and select "Run Configurations"
* go to "Arguments" tab and add following arguments (replace "/usr/lib/jvm/java-11-openjdk/lib/" with the path to javafx library directory)
* OpenJDK 11 ships Javafx in the standard library. To avoid same library being
  added multiple times, required libraries are added one by one.

```java
--module-path /usr/lib/jvm/java-11-openjdk/lib/javafx.controls.jar:/usr/lib/jvm/java-11-openjdk/lib/javafx.fxml.jar:/usr/lib/jvm/java-11-openjdk/lib/javafx.graphics.jar:/usr/lib/jvm/java-11-openjdk/lib/javafx.base.jar --add-modules javafx.controls,javafx.fxml
```

* Run the server "uob-cis-assignment-server.Application"
* Run the client "uob-cis-assignment-client.Main"
* Basic auth credentials
	* username: srinesh@gmail.com
	* password: 123
