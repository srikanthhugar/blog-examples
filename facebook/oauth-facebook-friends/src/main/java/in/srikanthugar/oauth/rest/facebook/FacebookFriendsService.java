package in.srikanthugar.oauth.rest.facebook;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

@Path("/")
public class FacebookFriendsService {

    private static final String FACEBOOK_RESOURCE_URL = "https://graph.facebook.com/me/friends";

    // Replace these with your own api key, secret and callback url.
    private static final String FACEBOOK_APP_ID = "XXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private static final String FACEBOOK_APP_SECRETE = "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY";
    private static final String FACEBOOK_CALLBACK_URL = "http://localhost:8080/oauth-facebook-friends/callback";

    public OAuthService getService() {
        OAuthService service = new ServiceBuilder().provider(FacebookApi.class)
                .apiKey(FACEBOOK_APP_ID).apiSecret(FACEBOOK_APP_SECRETE)
                .callback(FACEBOOK_CALLBACK_URL).build();
        return service;
    }

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response facebookLogin() {
        String authorizationUrl = getService().getAuthorizationUrl(null);
        return Response.seeOther(URI.create(authorizationUrl)).build();
    }

    @GET
    @Path("/callback")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response facebookCallback(@QueryParam("code") String code) {
        Verifier verifier = new Verifier(code);
        Token accessToken = getService().getAccessToken(null, verifier);
        OAuthRequest request = new OAuthRequest(Verb.GET, FACEBOOK_RESOURCE_URL);
        getService().signRequest(accessToken, request);
        org.scribe.model.Response response = request.send();
        return Response.ok(response.getBody()).build();
    }

}