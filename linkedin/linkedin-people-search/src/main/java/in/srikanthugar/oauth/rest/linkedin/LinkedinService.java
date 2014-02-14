package in.srikanthugar.oauth.rest.linkedin;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/")
public class LinkedinService {

	Token requestToken;

	@Autowired
	LinkedinPropertiesHolder linkedinProperties;

	String firstName = "";
	String lastName = "";
	String companyName = "";

	public OAuthService getService() {
		OAuthService service = new ServiceBuilder().provider(LinkedInApi.class)
				.apiKey(linkedinProperties.getLinkedinAPIKey())
				.apiSecret(linkedinProperties.getLinkedinSecreteKey())
				.callback(linkedinProperties.getLinkedinCallbackURL()).build();
		return service;
	}

	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public javax.ws.rs.core.Response linkedinLogin(
			@QueryParam("first-name") String firstName,
			@QueryParam("last-name") String lastName,
			@QueryParam("company-name") String companyName) {

		if (firstName != null)
			this.firstName = firstName;
		if (lastName != null)
			this.lastName = lastName;
		if (companyName != null)
			this.companyName = companyName;

		requestToken = getService().getRequestToken();
		System.out.println("requestToken : " + requestToken);
		return Response.seeOther(
				URI.create(getService().getAuthorizationUrl(requestToken)))
				.build();
	}

	@GET
	@Path("/callback")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response linkedinCallback(
			@QueryParam("oauth_verifier") String oauth_verifier,
			@QueryParam("oauth_token") String oauth_token) {
		Verifier verifier = new Verifier(oauth_verifier);

		System.out.println("oauth_verifier : " + oauth_verifier);
		System.out.println("oauth_token : " + oauth_token);
		System.out.println("requestToken : " + requestToken);

		Token accessToken = getService().getAccessToken(requestToken, verifier);
		OAuthRequest request = new OAuthRequest(Verb.GET,
				linkedinProperties.getLinkedinResourceURL() + "first-name="
						+ firstName + "&last-name=" + lastName
						+ "&company-name=" + companyName);
		getService().signRequest(accessToken, request);
		org.scribe.model.Response response = request.send();
		return Response.ok(response.getBody()).build();
	}

}