AirCraft REservation System - Showcase for PAC
==============================================

Welcome to ACRES.

Continuous Integration Server
-----------------------------

https://buildhive.cloudbees.com/job/mbisanz/job/acres/

System requirements
-------------------

The project is build using Java 7 (Java SDK 1.7) or better, Maven 3.0 or better.

For building the client (acres-web), node, npm and grunt are required. The last client build is included by maven, so it is possible to build the application without (re-)building the client.  

The application is designed to be run on JBoss WildFly 8.

You need a Hibernate supported database for this application. Setup scripts and instructions for MySQL 5.1 are included. 

Prepare the database
--------------------

Instructions for MySQL:

- Create database user and database, e.g.
```
create user 'acres'@'localhost' identified by 'acres';
create database acres;
grant all on acres.* to 'acres'@'localhost';
```
- Create the schema using acres-dist/sql/ddl/acres-schema-mysql.sql
- Create initial users using acres-dist/sql/data/acres-initial-mysql.sql. This creates the showcase users guest/guest, admin/admin, pilot1/pilot1, pilot2/pilot2. At least the admin user is required in order to be able to connect to the rest services and create new users.
  
Prepare the application server
------------------------------

The application requires a data source and a security domain to be configured in WildFly.

Instructions for MySQL:

- Deploy MySQL JDBC driver to standalone/deployments
- Optional: Add a JBoss administrator via add-user script to be able to access admin GUI
- Add data source "AcresDS" via admin GUI (localhost:9990) or standalone.xml:
```
<datasource jta="false" jndi-name="java:jboss/datasources/AcresDS" pool-name="AcresDS" enabled="true" use-ccm="false">
    <connection-url>jdbc:mysql://localhost:3306/acres</connection-url>
    <driver-class>com.mysql.jdbc.Driver</driver-class>
    <driver>mysql-connector-java-5.1.31-bin.jar_com.mysql.jdbc.Driver_5_1</driver>
    <security>
        <user-name>acres</user-name>
        <password>acres</password>
    </security>
    <validation>
        <validate-on-match>false</validate-on-match>
        <background-validation>false</background-validation>
    </validation>
    <statement>
        <share-prepared-statements>false</share-prepared-statements>
    </statement>
</datasource>
```
- Add security domain AcresDomain via admin GUI or standalone.xml
```
<security-domain name="AcresDomain" cache-type="default">
    <authentication>
        <login-module code="Database" flag="required">
            <module-option name="dsJndiName" value="java:jboss/datasources/AcresDS"/>
            <module-option name="hashAlgorithm" value="SHA-256"/>
            <module-option name="hashEncoding" value="hex"/>
            <module-option name="unauthenticatedIdentity" value="guest"/>
            <module-option name="rolesQuery" value="select r.roles as 'Role', 'Roles' as 'RoleGroup' from acres_user_user u left join acres_user_user_roles r on (u.id = r.user_id) where upper(u.login)=upper(?)"/>
            <module-option name="principalsQuery" value="select passwordHash as 'Password' from acres_user_user where login=?"/>
            <module-option name="ignorePasswordCase" value="true"/>
        </login-module>
    </authentication>
</security-domain>
```

Build and test the application
------------------------------

From this directory, use maven to build the application ear file:

        mvn clean package
        
This also executes all JUnit test within the application, especially Arquillian integration tests in acres-test.

_NOTE: Make sure you have not started wildfly when executing the tests as the tests will start up an embedded WildFly server using the standard ports, which could interfere with a running WildFly or other web or application server._

Then copy "acres-ear/target/acres.ear" and "acres-web/target/acres-web.war" to your wildfly deployment directory.

Browse the client
-----------------
Open a browser and go to
        http://localhost:8080/acres-web

Press the "Reset showcase" button in order to populate the database with the showcase users and some other data.

Enjoy!