==================== standard (for deployment) ==================
1. mvn clean compile
compile all classes

2. mvn test
insert data to database, hsqldb or mysql depend on your choice
see : \src\main\resources\META-INF\persistence.xml

3. mvn jetty:run-exploded
startup this application

4. visit http://localhost:8888/Serendipity.html
run this application

===================== simply (for develop) ======================
1. mvn test
insert data to database, hsqldb or mysql depend on your choice
see : \src\main\resources\META-INF\persistence.xml

2. mvn gwt:run
run this application


