package ar.com.bbva.web.services.usrdtws.client;

import org.apache.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.sa.su.util.PropUtil;

import ar.com.bbva.web.services.usrdtws.UserGroups;
import ar.com.bbva.web.services.usrdtws.UserGroupsResponse;
import ar.com.bbva.web.services.usrdtws.UsersMails;
import ar.com.bbva.web.services.usrdtws.UsersMailsResponse;
import ar.com.bbva.web.services.usrdtws.ldapops.xsd.LdapWSEntry;
import ar.com.bbva.web.services.usrdtws.ldapops.xsd.LdapWSPairValue;


public class WebsealWSClient {
	Logger log = Logger.getLogger(WebsealWSClient.class);

	private WebServiceTemplate wsTemplate;
	private String wsUri = "http://was70aacc1/UsersData/services/userDataws";

	public WebsealWSClient() {
	}

	public Object doCall(Object request) {
		Object response = null;
		this.wsTemplate = new WebServiceTemplate();
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		
		marshaller.setClassesToBeBound(LdapWSEntry.class, LdapWSPairValue.class, UsersMails.class,UsersMailsResponse.class,UserGroups.class,UserGroupsResponse.class);
		
		wsTemplate.setMarshaller(marshaller);
		wsTemplate.setUnmarshaller(marshaller);
		try {
			log.info("wsUri.toLowerCase(1): " + wsUri.toLowerCase());
			log.info("wsUri.toLowerCase(2): " + PropUtil.getProp().getProperty("userDataws.url"));
			if(PropUtil.getProp().getProperty("userDataws.url") != null)
				wsUri = PropUtil.getProp().getProperty("userDataws.url");
		} catch (Exception e) {
			log.error(e);
		}
		response = wsTemplate.marshalSendAndReceive(wsUri, request);

		return response;
	}

	public void setWsTemplate(WebServiceTemplate wsTemplate) {
		this.wsTemplate = wsTemplate;
	}

	public void setWsUri(String wsUri) {
		this.wsUri = wsUri;
	}

}
