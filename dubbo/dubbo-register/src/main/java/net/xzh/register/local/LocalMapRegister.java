package net.xzh.register.local;



import java.util.HashMap;
import java.util.Map;

import net.xzh.register.LocalRegister;

public class LocalMapRegister implements LocalRegister {
    private Map<String, Class> registerMap = new HashMap<String,Class>(1024);
    public void register(String interfaceName, Class interfaceImplClass) {
        registerMap.put(interfaceName,interfaceImplClass);
    }

    public Class get(String interfaceName) {
        return registerMap.get(interfaceName);
    }
}
