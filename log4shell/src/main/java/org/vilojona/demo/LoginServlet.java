package org.vilojona.demo;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("my message : user {}", request.getParameter("username"));
        log.info("my env : ${java:version}");
        //log.info("${jndi:ldap://ipa.demo1.freeipa.org/uid=admin,cn=users,cn=accounts,dc=demo1,dc=freeipa,dc=org}");
        //log.info("${jndi:ldap://localhost:10389/ou=people,dc=planetexpress,dc=com,cn=admin?GoodNewsEveryone}");
        response.getWriter().println("Hello, World!XXXXXyyy");
    }

}
