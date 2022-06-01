package net.xzh.spdy;

/**
 * 2018/11/22.
 */
public class SpdyRequestHandler  extends HttpRequestHandler {

    @Override
    protected String getContent() {
        return "This content is transmitted via SPDY\r\n";
    }

}
