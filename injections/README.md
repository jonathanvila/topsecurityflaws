# Top Security Flaws - Injection

This project goal is to test several injections vulnerabilities like SQL Injection, Deserealization Injection and Log forging injection.

## SQL Injection
This vulnerability involves sending malicious values that we know are going to be used in DB queries.

So instead of passing `admin` as user, we can pass `admin' -- ` in order to explicitly finish the query and comment out the rest of the filters used.

So a query like this :

`SELECT userId FROM db.USERS WHERE username = 'admin' AND password = 'aeik%%348900'`

Would be converted into this :

`SELECT userId FROM db.USERS WHERE username = 'admin' -- AND password = ''`

### Steps to test SQL Injection

1. Put the value of `DBService.java:48` to false to make our application vulnerable
2. excute the application

    `mvn quarkus:dev`

3. in the pop up window that opens, enter `admin` as User, empty for password and click enter while the cursor is in the password textfield
4. it answers with `Invalid Credentials`
5. now we enter `admin` for User, `admin` for Password
6. it answers with `User authenticated`
7. Now we are going to execute the vulnerability entering `admin' --` as user and whatever value you like in password
8. it answers with `User authenticated`. We have exploited the system loging into the system without not knowing the password of a user
9. we could even not use a known user with the expression `' OR true --` and it will login as the first user in the query result
10. now we can stop the application `ctrl+c`, modify the boolean variable in DBService.java:48 as true and try again the steps. Only with `admin` as user and `admin` as pass we will be able to succesfully log in.

## Log forging injection

This vulnerability involves sending information, known that is going to be logged, in a special format that will look like genuine log information, in order to confus developers debugging the system or even leading them to change the system in a certain way.

### Steps to test Log Forging Injection

1. execute the application

    `mvn quarkus:dev`
2. we are going to send this text as our user 

    ``` 
       :  Invalid Credentials
    2024-08-12 12:34:56,888 INFO [org.vil.top.com.DBService] (executor-thread-1) admin : User Authenticated (fake)
    2024-08-12 12:34:56,888 ERROR [org.vil.top.com.DBService] (executor-thread-1) john&password=X
    ```


3. execute curl command to send a value to non sanitized endpoint
   
    `curl "localhost:8080/loginjection/login?username=john%20%3A%20%20Invalid%20Credentials%0A2024-08-12%2012%3A34%3A56%2C888%20INFO%20%5Borg.vil.top.com.DBService%5D%20%28executor-thread-1%29%20admin%20%3A%20User%20Authenticated (fake) %0A2024-08-12%2012%3A34%3A56%2C888%20ERROR%20%5Borg.vil.top.com.DBService%5D%20%28executor-thread-1%29%20john&password=X"`

4. check in the application log that we have the following confusing log

    ```
    2024-10-16 16:19:44,936 ERROR [org.vil.top.com.DBService] (executor-thread-1) john :  Invalid Credentials
    2024-08-12 12:34:56,888 INFO [org.vil.top.com.DBService] (executor-thread-1) admin : User Authenticated (fake)
    2024-08-12 12:34:56,888 ERROR [org.vil.top.com.DBService] (executor-thread-1) john :  Invalid credentials
    ```

5. excute curl command with same content but to the sanitized endpoint

    `curl "localhost:8080/loginjection/login/sanitized?username=john%20%3A%20%20Invalid%20Credentials%0A2024-08-12%2012%3A34%3A56%2C888%20INFO%20%5Borg.vil.top.com.DBService%5D%20%28executor-thread-1%29%20admin%20%3A%20User%20Authenticated (fake) %0A2024-08-12%2012%3A34%3A56%2C888%20ERROR%20%5Borg.vil.top.com.DBService%5D%20%28executor-thread-1%29%20john&password=X"`    

6. check that now the response is less confusing as there are no new lines

    ```
    2024-10-16 16:19:44,936 ERROR [org.vil.top.com.DBService] (executor-thread-1) john :  Invalid Credentials 2024-08-12 12:34:56,888 INFO [org.vil.top.com.DBService] (executor-thread-1) admin : User Authenticated (fake)
    2024-08-12 12:34:56,888 ERROR [org.vil.top.com.DBService] (executor-thread-1) john :  Invalid credentials
    ```

## Deserialization Injection

This vulnerability involves using the Java object serialization to send content, expecting that the class received is the one expected.
To exploiting this vulnerability the malicious attacker will send a wrong object, expecting that it will get rejected but having the opportunity to inject malicious code before the rejection.


### Steps to test Deserialization Injection

1. execute the application

    `mvn quarkus:dev`

2. create a serialized version of a legal User (user.ser file)

    `mvn test -Dtest=ExploitTest#testUser`
3. execute the endpoint sending the correct class

    `curl -v -X POST --data-binary @user.ser http://localhost:8080/deserialization/user/binary`

4. check that the response is an HTTP 200 and 1 is the number return as payload
5. created a serialized version of the exploit (exploit.ser file) that contains remote execution

    `mvn test -Dtest=ExploitTest#testExploit`

6. execute the endpoint sending the malicious class

    `curl -v -X POST --data-binary @exploit.ser http://localhost:8080/deserialization/user/binary`

7. check that the response returns an Error occurred during deserialization
8. check that also the Calculator app has open
9. execute the secure endpoint sending the malicious class

    `curl -v -X POST --data-binary @exploit.ser http://localhost:8080/deserialization/user/binary-secure`

10. check that the response returns an Error occurred during deserialization
11. check that the Calculator app has not open