package net.xzh.smack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.sasl.javax.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MucEnterConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatException.MucNotJoinedException;
import org.jivesoftware.smackx.muc.MultiUserChatException.NotAMucServiceException;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.ping.PingManager;
import org.jivesoftware.smackx.xdata.form.FillableForm;
import org.jivesoftware.smackx.xdata.form.Form;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;

import cn.hutool.core.date.DateUtil;
import net.xzh.smack.domain.User;
import net.xzh.smack.listener.InGroupChatMessageListener;
import net.xzh.smack.listener.MyConnectionListener;

public class boot {
	private static XMPPTCPConnectionConfiguration.Builder config;
	private static XMPPTCPConnection conn;
	private static String XmppDomain = "2021openfire.vjsp.cn";

	public static void main(String[] args) throws Exception {
		// 13998417419-bd2260b6873d43fcb8685838385c6cdc
		// 18141789337-6d36aface3e945de89d15ac458b263e8
//		sendMessage("bd2260b6873d43fcb8685838385c6cdc", "bd2260b6873d43fcb8685838385c6cdc","6d36aface3e945de89d15ac458b263e8", "hello");
//		List<User> list = new ArrayList<>();
//		User user = new User();
//		user.setUsername("bd2260b6873d43fcb8685838385c6cdc");
//		list.add(user);
		login("6593d4e1aefa4a8e9087db198c872ee8", "6593d4e1aefa4a8e9087db198c872ee8");
//    	createChatRoom("ewqew321321qewq", list, "eeeeeee2321321eeeeeeww");
		joinMultiUserChat("room20220530131720195", "6593d4e1aefa4a8e9087db198c872ee8");
//		sendChatGroupMessage("room20220523182600001", "hello " + System.currentTimeMillis());
//		quitRoom("room20220523182600001");
//		System.in.read();
	}

	static {
		try {
			config = XMPPTCPConnectionConfiguration.builder().setCompressionEnabled(false).setSendPresence(true)
					.setHost(XmppDomain)// IP地址
					.setXmppDomain(XmppDomain)// 服务器名称
					.setResource("xzh")// 资源
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
			conn.addConnectionListener(new MyConnectionListener());
			conn.connect();
			ReconnectionManager.getInstanceFor(conn).enableAutomaticReconnection();
//			PingManager.getInstanceFor(conn).setPingInterval(60);
//			ReconnectionManager.getInstanceFor(conn).setFixedDelay(5);
		} catch (SmackException | IOException | XMPPException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void login(String username, String password) {

		try {
			if (conn != null) {
				conn.login(username, password);
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		} catch (SmackException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void sendMessage(String username, String password, String toId, String msg) {
		// 先登录
		login(username, password);
		// 再构建聊天室
		ChatManager cm = ChatManager.getInstanceFor(conn);
		cm.addIncomingListener(new IncomingChatMessageListener() {
			@Override
			public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
				System.out.println("接收到来自-" + from.toString() + "的消息:" + message.getBody());
			}
		});
		try {
			EntityBareJid jid = JidCreate.entityBareFrom(toId + "@" + XmppDomain);
			Chat chat = cm.chatWith(jid);
			Message message = new Message();
			while (true) {
				message.setBody(msg + DateUtil.now());
				chat.send(message);
				Thread.sleep(5 * 1000); // 设置暂停的时间 5 秒
			}
		} catch (XmppStringprepException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建群聊房间
	 * 
	 * @param groupName     群名称
	 * @param users         创建群完成后添加的群成员
	 * @param groupNickName 群昵称
	 * @return
	 */
	public static MultiUserChat createChatRoom(String groupName, List<User> users, String groupNickName)
			throws Exception {
		// 组装群聊jid,这里需要注意一下,群jid的格式就是 群名称@conference.openfire服务器名称
		String jid = groupName + "@conference." + XmppDomain;
		EntityBareJid groupJid = JidCreate.entityBareFrom(jid);
		MultiUserChatManager multiUserChatManager = MultiUserChatManager.getInstanceFor(conn);
		MultiUserChat muc = multiUserChatManager.getMultiUserChat(groupJid);
		muc.create(Resourcepart.from(groupNickName));
		// 获得聊天室的配置表单
		Form form = muc.getConfigurationForm();
		// 根据原始表单创建一个要提交的新表单。
		FillableForm submitForm = form.getFillableForm();

		// 设置聊天室的新拥有者
		List<String> owners = new ArrayList<>();
		owners.add("admin" + "@" + XmppDomain);
		// 这里的用户实体我要说一下，因为这是我这个项目的实体，实际上这里只需要知道用户的jid获者名称就可以了
		if (users != null && !users.isEmpty()) {
			for (int i = 0; i < users.size(); i++) { // 添加群成员,用户jid格式和之前一样 用户名@openfire服务器名称
				EntityBareJid userJid = JidCreate.entityBareFrom(users.get(i).getUsername() + "@" + XmppDomain);
				owners.add(userJid + "");
			}
		}
		submitForm.setAnswer("muc#roomconfig_roomowners", owners);
		// 设置为公共房间
		submitForm.setAnswer("muc#roomconfig_publicroom", true);
		// 设置聊天室是持久聊天室，即将要被保存下来
		submitForm.setAnswer("muc#roomconfig_persistentroom", true);
		// 房间仅对成员开放
		submitForm.setAnswer("muc#roomconfig_membersonly", false);
		// 允许占有者邀请其他人
		submitForm.setAnswer("muc#roomconfig_allowinvites", true);
		// 进入不需要密码
		submitForm.setAnswer("muc#roomconfig_passwordprotectedroom", false);
		// 设置进入密码 true时设置
		// submitForm.setAnswer("muc#roomconfig_roomsecret", password);

		// 能够发现占有者真实 JID 的角色
		// submitForm.setAnswer("muc#roomconfig_whois", "anyone");
		// 登录房间对话
		submitForm.setAnswer("muc#roomconfig_enablelogging", true);
		// 仅允许注册的昵称登录
		submitForm.setAnswer("x-muc#roomconfig_reservednick", false);
		// 允许使用者修改昵称
		submitForm.setAnswer("x-muc#roomconfig_canchangenick", true);
		// 允许用户注册房间
		submitForm.setAnswer("x-muc#roomconfig_registration", false);
		// 发送已完成的表单（有默认值）到服务器来配置聊天室
		muc.sendConfigurationForm(submitForm);
		// 添加群消息监听
		muc.addMessageListener(new InGroupChatMessageListener(jid));
		return muc;
	}

	/**
	 * 发送群聊普通消息
	 * 
	 * @param groupName
	 * @param body
	 */
	public static void sendChatGroupMessage(String groupName, String body) {
		// 拼凑jid
		String jid = groupName + "@conference." + XmppDomain;
		// 创建jid实体
		EntityBareJid groupJid = null;
		try {
			groupJid = JidCreate.entityBareFrom(jid);
		} catch (XmppStringprepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 群管理对象
		MultiUserChatManager multiUserChatManager = MultiUserChatManager.getInstanceFor(conn);
		MultiUserChat multiUserChat = multiUserChatManager.getMultiUserChat(groupJid);
		// 发送信息
		try {
			multiUserChat.sendMessage(body);
		} catch (NotConnectedException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 加入群聊会议室
	 * 
	 * @param groupName
	 * @param nickName
	 * @return
	 * @throws Exception
	 */
	public static MultiUserChat joinMultiUserChat(String groupName, String nickName) {
		// 群jid
		String jid = groupName + "@conference." + XmppDomain;
		// jid实体创建
		EntityBareJid groupJid = null;
		try {
			groupJid = JidCreate.entityBareFrom(jid);
		} catch (XmppStringprepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获取群管理对象
		MultiUserChatManager multiUserChatManager = MultiUserChatManager.getInstanceFor(conn);
		// 通过群管理对象获取该群房间对象
		MultiUserChat multiUserChat = multiUserChatManager.getMultiUserChat(groupJid);

		MucEnterConfiguration.Builder builder = null;
		try {
			builder = multiUserChat.getEnterConfigurationBuilder(Resourcepart.from(nickName));
		} catch (XmppStringprepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 只获取最后99条历史记录
		builder.requestMaxCharsHistory(99);
		MucEnterConfiguration mucEnterConfiguration = builder.build();
		// 加入群
		try {
			multiUserChat.addMessageListener(new InGroupChatMessageListener(jid));
			multiUserChat.join(mucEnterConfiguration);
		} catch (NotAMucServiceException | XMPPErrorException | NoResponseException | NotConnectedException
				| InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("加入房间：" + groupName);
		return multiUserChat;
	}

	/**
	 * 退出群聊
	 * 
	 * @param groupName
	 * @throws XmppStringprepException
	 */
	public static void quitRoom(String groupName) {
		String jid = groupName + "@conference." + XmppDomain;
		EntityBareJid groupJid = null;
		try {
			groupJid = JidCreate.entityBareFrom(jid);
		} catch (XmppStringprepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MultiUserChatManager multiUserChatManager = MultiUserChatManager.getInstanceFor(conn);
		MultiUserChat multiUserChat = multiUserChatManager.getMultiUserChat(groupJid);
		// 退出群
		try {
			multiUserChat.leave();
		} catch (MucNotJoinedException | NotConnectedException | NoResponseException | XMPPErrorException
				| InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("退出房间：" + groupName);
	}
}