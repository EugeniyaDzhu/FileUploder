# File uploder

Spring boot REST service with file uploading. Files are persisted in MySQL db.
File should be only csv (with correct structure and mandatory headers). 
If file is already present with specified name upload will be rejected.
REST service provides search files by file name and load to UI correspond content

## Build/Deploy

* [Java 8](http://www.oracle.com/technetwork/java/index.htm)

* [Maven](https://maven.apache.org/) - Dependency Management

### Installing

* [IntelliJ IDEA](https://www.jetbrains.com/idea/)

* [Git](https://git-scm.com/downloads)
*  If you haven't already done, please clone this repository

```
git clone https://github.com/EugeniyaDzhu/FileUploder.git
```
Ensure that the Project SDK is 1.8 
Ensure that you set up working directory `\fupl\web` for running project 

* [MySQL](https://dev.mysql.com/downloads/)
* Create MySQL database and connect it as the data source to the project:
```
create database db_fileuploder;
```

## Running the tests
 Run a mvn test to see tests passed
  ```$xslt
mvn test
```

##Run & Deploy
```$xslt
mvn spring-boot:run
```

##Open the web app in your browser
[http://localhost:8080/](http://localhost:8080/)




