package net.xzh.openfire;

import javax.ws.rs.core.Response;

import org.igniterealtime.restclient.RestApiClient;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.igniterealtime.restclient.entity.RosterItemEntity;
import org.igniterealtime.restclient.entity.UserEntity;

public class JavaOpenfire {
	static AuthenticationToken authenticationToken = new AuthenticationToken("p9M5L3mSofk5Kpsc");
	static RestApiClient restApiClient = new RestApiClient("http://172.17.17.80", 9090, authenticationToken);

	public static void main(String[] args) {

		UserEntity userEntity = new UserEntity("test3", "test3", "test@google.com", "123456");
		Response response = restApiClient.createUser(userEntity);
		if (response.getStatus() == 201) {
			System.out.println("sucess");
		}
		
		
		RosterItemEntity rosterItemEntity =new RosterItemEntity();
		rosterItemEntity.setJid("admin@xuzhihao");
		rosterItemEntity.setNickname("admin");
		rosterItemEntity.setSubscriptionType(3);
//		Response response2 = restApiClient.addRosterEntry("test400", rosterItemEntity);
//		if (response2.getStatus() == 201) {
//			System.out.println("sucess");
//		}

	}

}
