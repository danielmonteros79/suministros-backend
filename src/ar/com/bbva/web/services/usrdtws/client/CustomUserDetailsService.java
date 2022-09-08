package ar.com.bbva.web.services.usrdtws.client;

import java.util.Iterator;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import com.sa.su.model.Usuario;

import ar.com.bbva.web.services.usrdtws.UserGroups;
import ar.com.bbva.web.services.usrdtws.UserGroupsResponse;
import ar.com.bbva.web.services.usrdtws.UsersMails;
import ar.com.bbva.web.services.usrdtws.UsersMailsResponse;
import ar.com.bbva.web.services.usrdtws.ldapops.xsd.LdapWSEntry;
import ar.com.bbva.web.services.usrdtws.ldapops.xsd.LdapWSPairValue;

public class CustomUserDetailsService {
	public static final Logger log = Logger.getLogger(CustomUserDetailsService.class);

	private WebsealWSClient websealWSClient;

	public Usuario processUsers(String username) throws Exception {
		log.info(" entering processUsers username: " + username);
		UsersMails websealUserQuery = new UsersMails();
		websealUserQuery.getLegajo().add(username);
		UsersMailsResponse userMailsResponse = (UsersMailsResponse) websealWSClient.doCall(websealUserQuery);
		Usuario user = new Usuario();
		user.setLegajo(username);
		if (userMailsResponse != null) {
			LdapWSEntry wsEntry = null;
			for (Iterator<?> iterator = userMailsResponse.getReturn().iterator(); iterator.hasNext();) {

				LdapWSEntry wsEntryAux = (LdapWSEntry) iterator.next();
				if ((wsEntryAux.getName().getValue()).toString().toUpperCase()
						.contains((new StringBuilder("UID=")).append(username).toString())) {
					wsEntry = wsEntryAux;
					break;
				}
			}
			if (wsEntry != null) {
				for (Iterator<?> iterator2 = wsEntry.getAttribs().iterator(); iterator2.hasNext();) {
					LdapWSPairValue ldapWSPairValue = (LdapWSPairValue) iterator2.next();

					log.info("ldapWSPairValue.getName(): " + (ldapWSPairValue.getName().getName()));
					log.info("ldapWSPairValue.getName().getValue(): " + (ldapWSPairValue.getName().getValue()).toString().trim().toLowerCase());
					log.info("ldapWSPairValue.getValue().getValue(): " + (ldapWSPairValue.getValue().getValue()).toString().trim().toLowerCase());

					String key = (ldapWSPairValue.getName().getValue()).toString().trim().toLowerCase();
					String value = (ldapWSPairValue.getValue().getValue()).toString().trim().toUpperCase();
					
					if (key.equals("cn"))
						user.setNombre(value);
					else if (key.equals("sn"))
						user.setApellido(value);
					else if (key.equals("areacod"))
						user.setCodArea(Integer.parseInt(value));
					else if (key.equals("area"))
						user.setDescArea(value);
					else if (key.equals("puestocod"))
						user.setCodPuesto(Integer.parseInt(value));
					else if (key.equals("puesto"))
						user.setDescPuesto(value);
					else if (key.equals("gmail"))
						user.setEmail(value);
				}

				UserGroups groupsReq = new UserGroups();

				QName fooQName = new QName("http://usrdtws.services.web.bbva.com.ar", "legajo");
				JAXBElement<String> fooValue = new JAXBElement<String>(fooQName, String.class, username.toLowerCase());
				groupsReq.setLegajo(fooValue);
				UserGroupsResponse response = (UserGroupsResponse) websealWSClient.doCall(groupsReq);
				if (response != null) {
					for (Iterator<?> iterator = response.getReturn().iterator(); iterator.hasNext();) {
						LdapWSEntry wsEntryAux = (LdapWSEntry) iterator.next();
						log.info("wsEntryAux.getName().getValue(): " + wsEntryAux.getName().getValue());
//						if ((wsEntryAux.getName().getValue()).toString().toUpperCase().contains("CN=RH08"))
//							user.setFacultad(("RH08"));

					}
				}
			} else
				throw new Exception("No fue posible recuperar la informaci\363n del usuario de WebSeal.");
		} else
			throw new Exception("No fue posible recuperar la informaci\363n del usuario de WebSeal.");

		return user;
	}

	public void setWebsealWSClient(WebsealWSClient websealWSClient) {
		this.websealWSClient = websealWSClient;
	}

}
