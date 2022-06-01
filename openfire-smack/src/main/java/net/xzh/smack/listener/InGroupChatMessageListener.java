package net.xzh.smack.listener;


import java.io.IOException;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.sasl.javax.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
 
/**
 * 群消息监听
 */
public class InGroupChatMessageListener implements MessageListener {
 
	private static XMPPTCPConnectionConfiguration.Builder config;
    private static AbstractXMPPConnection conn;
    private static String XmppDomain = "2021openfire.vjsp.cn";
    private String jid;
 
    public InGroupChatMessageListener(String jid) {
        this.jid = jid;
    }
 
 
    static {
		try {
			config = XMPPTCPConnectionConfiguration.builder().setCompressionEnabled(false).setSendPresence(true)
					.setHost(XmppDomain)// IP地址
					.setXmppDomain(XmppDomain)// 服务器名称
					.setResource("test")// 资源
					.setPort(5223)// 端口
					.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
					.setSocketFactory(SSLSocketFactory.getDefault()).setCompressionEnabled(true);
		} catch (XmppStringprepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	SASLAuthentication.registerSASLMechanism(new SASLDigestMD5Mechanism());
	conn = new XMPPTCPConnection(config.build());
	try {
		conn.connect();
	} catch (SmackException | IOException | XMPPException | InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
}
 
    @Override
    public void processMessage(Message message) {
        try {
            //创建jid实体
            EntityBareJid groupJid = JidCreate.entityBareFrom(jid);
 
            //群管理对象
            MultiUserChatManager multiUserChatManager = MultiUserChatManager.getInstanceFor(conn);
            MultiUserChat multiUserChat = multiUserChatManager.getMultiUserChat(groupJid);
 
            //发送信息
            multiUserChat.sendMessage(message);
            System.out.println("聊天室："+jid+",收到消息："+message.getBody());
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
    }
    
    
 
}