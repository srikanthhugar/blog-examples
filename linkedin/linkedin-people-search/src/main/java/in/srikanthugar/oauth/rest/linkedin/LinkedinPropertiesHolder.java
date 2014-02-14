package in.srikanthugar.oauth.rest.linkedin;

public class LinkedinPropertiesHolder {

	private String linkedinResourceURL;
	private String linkedinAPIKey;
	private String linkedinSecreteKey;
	private String linkedinCallbackURL;

	public String getLinkedinResourceURL() {
		return linkedinResourceURL;
	}

	public void setLinkedinResourceURL(String linkedinResourceURL) {
		this.linkedinResourceURL = linkedinResourceURL;
	}

	public String getLinkedinAPIKey() {
		return linkedinAPIKey;
	}

	public void setLinkedinAPIKey(String linkedinAPIKey) {
		this.linkedinAPIKey = linkedinAPIKey;
	}

	public String getLinkedinSecreteKey() {
		return linkedinSecreteKey;
	}

	public void setLinkedinSecreteKey(String linkedinSecreteKey) {
		this.linkedinSecreteKey = linkedinSecreteKey;
	}

	public String getLinkedinCallbackURL() {
		return linkedinCallbackURL;
	}

	public void setLinkedinCallbackURL(String linkedinCallbackURL) {
		this.linkedinCallbackURL = linkedinCallbackURL;
	}

}
