/*******************************************************************************
 * Class        AlfrescoBaseOnPrem
 * Created date ：2020/07/27
 * Lasted date  ：2020/07/27
 * Author       ：tantm
 * Change log   ：2020/07/27：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * This class contains only the logic that is specific to using the Public API against an Alfresco repository running on-premise (4.2.d or
 * later).
 *
 * @author tantm
 */
public class AlfrescoBaseOnPrem extends AlfrescoBasePublicAPI {

    /**
     * TEST.
     *
     * @param args
     *            the arguments
     * @author tantm
     */
    public static void main(String[] args) {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem("http://10.0.0.241:8081/alfresco", "admin", "Unit@123", "unit");
        try {
            alfresco.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test.
     *
     * @throws Exception
     *             the exception
     * @author tantm
     */
    public void test() throws Exception {
        try {
            // Create a new folder in the root folder
            AlfrescoBaseOnPrem alfrescoBaseOnPrem = new AlfrescoBaseOnPrem("http://10.0.0.241:8081/alfresco", "admin", "Unit@123", "unit");
            alfrescoBaseOnPrem.executeQuery("");
            // Document testDoc = createOrUpdateDocument(testingFolder, new File("C:\\Users\\daibt\\Desktop\\BuBu.txt"), "text.txt",
            // "text/plain; charset=UTF-8", true);

            // Create a test document in the subFolder
            // createDocument(subFolder, new File("C:\\Users\\daibt\\Desktop\\BuBu.txt"), "text/plain; charset=UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Change these to match your environment. */
    // public static final String CMIS_URL = "/public/cmis/versions/1.0/atom";
    public static final String CMIS_URL = "/public/cmis/versions/1.1/atom";

    /** The Constant HTTP_TRANSPORT. */
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /** The Constant JSON_FACTORY. */
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /** The request factory. */
    private HttpRequestFactory requestFactory;

    /** The cmis session. */
    private Session cmisSession;

    /** The password. */
    private String username, password;

    /**
     * Instantiates a new alfresco base on prem.
     *
     * @param host
     *            the host
     * @param username
     *            the username
     * @param password
     *            the password
     * @param site
     *            the site
     */
    public AlfrescoBaseOnPrem(String host, String username, String password, String site) {
        this.api = host + "/api/";
        this.username = username;
        this.password = password;
        this.site = site;
    }

    /**
     * Gets the atom pub URL.
     *
     * @param requestFactory
     *            the request factory
     * @return the atom pub URL
     * @author tantm
     */
    public String getAtomPubURL(HttpRequestFactory requestFactory) {
        String alfrescoAPIUrl = api;
        String atomPubURL = null;

        try {
            atomPubURL = alfrescoAPIUrl + getHomeNetwork() + CMIS_URL;
        } catch (IOException ioe) {
            System.out.println("Warning: Couldn't determine home network, defaulting to -default-");
            atomPubURL = alfrescoAPIUrl + "-default-" + CMIS_URL;
        }

        return atomPubURL;
    }

    /**
     * Gets a CMIS Session by connecting to the local Alfresco server.
     *
     * @return Session
     * @author tantm
     */
    public Session getCmisSession() {
        if (cmisSession == null) {
            // default factory implementation
            SessionFactory factory = SessionFactoryImpl.newInstance();
            Map<String, String> parameter = new HashMap<String, String>();

            // connection settings
            parameter.put(SessionParameter.USER, username);
            parameter.put(SessionParameter.PASSWORD, password);

            parameter.put(SessionParameter.ATOMPUB_URL, getAtomPubURL(getRequestFactory()));
            parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
            parameter.put(SessionParameter.AUTH_HTTP_BASIC, "true");

            // parameter.put(SessionParameter.OBJECT_FACTORY_CLASS, "org.alfresco.cmis.client.impl.AlfrescoObjectFactoryImpl");

            List<Repository> repositories = factory.getRepositories(parameter);

            cmisSession = repositories.get(0).createSession();
        }
        return this.cmisSession;
    }

    /**
     * Uses basic authentication to create an HTTP request factory.
     *
     * @return HttpRequestFactory
     * @author tantm
     */
    public HttpRequestFactory getRequestFactory() {
        if (this.requestFactory == null) {
            this.requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {

                @Override
                public void initialize(HttpRequest request) throws IOException {
                    request.setParser(new JsonObjectParser(new JacksonFactory()));
                    request.getHeaders().setBasicAuthentication(username, password);
                }

            });
        }
        return this.requestFactory;
    }

}
