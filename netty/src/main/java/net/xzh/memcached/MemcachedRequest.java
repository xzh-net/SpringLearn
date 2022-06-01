package net.xzh.memcached;

import java.util.Random;

/**
 * Created by dimi on 2018/12/12.
 */
public class MemcachedRequest {

    private static final Random rand = new Random();

    private int magic = 0x80;
    private byte opCode ;// get,set 等
    private String key;
    private int flags = 0xdeadbeaf;
    private int expires;
    private String body;
    private int id = rand.nextInt();
    private long cas;
    private boolean hasExtras;

    public MemcachedRequest(byte opcode, String key, String value){
        this.opCode = opcode;
        this.key = key;
        this.body = value == null ? "" : value;
        //only set command has extras in our example
        hasExtras = opcode == Opcode.SET;
    }
    public MemcachedRequest(byte opCode, String key) {
        this(opCode, key, null);
    }

    public int getMagic() {
        return magic;
    }

    public byte getOpCode() {
        return opCode;
    }

    public String getKey() {
        return key;
    }

    public int getFlags() {
        return flags;
    }

    public int getExpires() {
        return expires;
    }

    public String getBody() {
        return body;
    }

    public int getId() {
        return id;
    }

    public long getCas() {
        return cas;
    }

    public boolean hasExtras() {
        return hasExtras;
    }
}
