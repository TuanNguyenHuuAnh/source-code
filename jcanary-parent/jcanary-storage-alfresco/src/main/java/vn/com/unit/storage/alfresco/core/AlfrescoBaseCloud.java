/*******************************************************************************
 * Class        AlfrescoBaseCloud
 * Created date ：2020/07/27
 * Lasted date  ：2020/07/27
 * Author       ：tantm
 * Change log   ：2020/07/27：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.core;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * This class contains only the logic specific to using the Alfresco Public API against Alfresco in the cloud.
 *
 * @author tantm
 */
public class AlfrescoBaseCloud extends AlfrescoBasePublicAPI {

    /** The Constant CMIS_URL. */
    public static final String CMIS_URL = "cmis/versions/1.0/atom";

    /** The Constant HTTP_TRANSPORT. */
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /** The Constant JSON_FACTORY. */
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /** The Constant ALFRESCO_API_URL. */
    public static final String ALFRESCO_API_URL = "https://api.alfresco.com/";

    /** The Constant TOKEN_SERVER_URL. */
    public static final String TOKEN_SERVER_URL = ALFRESCO_API_URL + "auth/oauth/versions/2/token";

    /** The Constant AUTHORIZATION_SERVER_URL. */
    public static final String AUTHORIZATION_SERVER_URL = ALFRESCO_API_URL + "auth/oauth/versions/2/authorize";

    /** The Constant SCOPE. */
    public static final String SCOPE = "public_api";

    /** The Constant SCOPES. */
    public static final List<String> SCOPES = Arrays.asList(SCOPE);

    /** The request factory. */
    private HttpRequestFactory requestFactory;

    /** The credential. */
    private Credential credential;

    /** The cmis session. */
    private Session cmisSession;

    /**
     * Gets the alfresco API url.
     *
     * @return the alfresco API url
     * @author tantm
     */
    public String getAlfrescoAPIUrl() {
        return ALFRESCO_API_URL;
    }

    /**
     * Gets the atom pub URL.
     *
     * @return the atom pub URL
     * @author tantm
     */
    public String getAtomPubURL() {
        return ALFRESCO_API_URL + CMIS_URL;
    }

    /**
     * Launch in browser.
     *
     * @param browser
     *            the browser
     * @param redirectUrl
     *            the redirect url
     * @param clientId
     *            the client id
     * @param scope
     *            the scope
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    public void launchInBrowser(String browser, String redirectUrl, String clientId, String scope) throws IOException {

        String authorizationUrl = new AuthorizationCodeRequestUrl(AUTHORIZATION_SERVER_URL, clientId).setRedirectUri(redirectUrl)
                .setScopes(Arrays.asList(scope)).build();

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Action.BROWSE)) {
                desktop.browse(URI.create(authorizationUrl));
                return;
            }
        }

        if (browser != null) {
            Runtime.getRuntime().exec(new String[] { browser, authorizationUrl });
        } else {
            System.out.println("Open the following address in your favorite browser:");
            System.out.println("  " + authorizationUrl);
        }
    }

    /**
     * Does the OAuth dance by starting up a local server to handle the redirect. The credential gets saved off because it needs to be used
     * when/if a CMIS session is needed.
     *
     * @return HttpRequestFactory
     * @author tantm
     */
    public HttpRequestFactory getRequestFactory() {
        if (this.requestFactory == null) {
            AlfrescoVerificationCodeReceiver receiver = new AlfrescoLocalServerReceiver();
            try {
                String redirectUri = receiver.getRedirectUri();
                launchInBrowser("google-chrome", redirectUri, AlfrescoBaseCloud.getAPIKey(), SCOPE);
                this.credential = authorize(receiver, redirectUri);

                this.requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {

                    @Override
                    public void initialize(HttpRequest request) throws IOException {
                        credential.initialize(request);
                        request.setParser(new JsonObjectParser(new JacksonFactory()));
                    }
                });

                System.out.println("Access token:" + credential.getAccessToken());

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    receiver.stop();
                } catch (Exception e) {
                }
            }
        }
        return this.requestFactory;
    }

    /**
     * Authorize.
     *
     * @param receiver
     *            the receiver
     * @param redirectUri
     *            the redirect uri
     * @return the credential
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    public Credential authorize(AlfrescoVerificationCodeReceiver receiver, String redirectUri) throws IOException {

        String code = receiver.waitForCode();

        AuthorizationCodeFlow codeFlow = new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(), HTTP_TRANSPORT,
                JSON_FACTORY, new GenericUrl(TOKEN_SERVER_URL),
                new ClientParametersAuthentication(AlfrescoBaseCloud.getAPIKey(), AlfrescoBaseCloud.getAPISecret()),
                AlfrescoBaseCloud.getAPIKey(), AUTHORIZATION_SERVER_URL).setScopes(SCOPES).build();

        TokenResponse response = codeFlow.newTokenRequest(code).setRedirectUri(redirectUri).setScopes(SCOPES).execute();

        return codeFlow.createAndStoreCredential(response, null);

    }

    /**
     * Gets a CMIS Session by connecting to the Alfresco Cloud.
     *
     * @return Session
     * @author tantm
     */
    public Session getCmisSession() {
        if (cmisSession == null) {
            String accessToken = getCredential().getAccessToken();
            System.out.println("Access token:" + accessToken);

            // default factory implementation
            SessionFactory factory = SessionFactoryImpl.newInstance();
            Map<String, String> parameter = new HashMap<String, String>();

            // connection settings
            parameter.put(SessionParameter.ATOMPUB_URL, this.getAtomPubURL());
            parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
            parameter.put(SessionParameter.AUTH_HTTP_BASIC, "false");
            parameter.put(SessionParameter.HEADER + ".0", "Authorization: Bearer " + accessToken);
            parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");

            List<Repository> repositories = factory.getRepositories(parameter);

            cmisSession = repositories.get(0).createSession();
        }
        return cmisSession;
    }

    /**
     * Gets the credential.
     *
     * @return the credential
     * @author tantm
     */
    public Credential getCredential() {
        if (this.credential == null) {
            getRequestFactory(); // Yuck, depending on a side-effect
        }
        return this.credential;
    }

    /**
     * Gets the API key.
     *
     * @return the API key
     * @author tantm
     */
    public static String getAPIKey() {
        return AlfrescoConfig.getConfig().getProperty("api_key");
    }

    /**
     * Gets the API secret.
     *
     * @return the API secret
     * @author tantm
     */
    public static String getAPISecret() {
        return AlfrescoConfig.getConfig().getProperty("api_secret");
    }
}
