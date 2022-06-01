package net.xzh.smack.listener;


import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.xzh.smack.boot;


/**
 * 当前类的描述: 链接类的监听
 */

public class MyConnectionListener implements ConnectionListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyConnectionListener.class);
	
	@Override
	public void connecting(XMPPConnection connection) {
		// TODO Auto-generated method stub
		LOGGER.info("正在连接。。。");
	}

	@Override
	public void connected(XMPPConnection connection) {
		// TODO Auto-generated method stub
		LOGGER.info("连接完毕。。。");
	}

	@Override
	public void authenticated(XMPPConnection connection, boolean resumed) {
		// TODO Auto-generated method stub
		LOGGER.info("授权成功。。。");
	}

	@Override
	public void connectionClosed() {
		// TODO Auto-generated method stub
		LOGGER.info("连接关闭。。。");
	}

	@Override
	public void connectionClosedOnError(Exception e) {
		// TODO Auto-generated method stub
		LOGGER.info("连接错误。。。");
	}

  
}
