package org.vilojona.topsecurityflaws.deserialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.Set;

import org.vilojona.topsecurityflaws.common.User;

class SecureObjectInputStream extends ObjectInputStream {
    private static final Set<String> ALLOWED_CLASSES = Set.of(User.class.getName());

    public SecureObjectInputStream(InputStream inputStream) throws IOException {
        super(inputStream);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
      if (!ALLOWED_CLASSES.contains(osc.getName())) {
        throw new InvalidClassException("Unauthorized deserialization", osc.getName());
      }
      return super.resolveClass(osc);
    }
  }