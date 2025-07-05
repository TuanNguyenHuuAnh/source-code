/*******************************************************************************
 * Class        AlfrescoBasePublicAPI
 * Created date ：2020/07/27
 * Lasted date  ：2020/07/27
 * Author       ：tantm
 * Change log   ：2020/07/27：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.util.FileUtils;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;

import vn.com.unit.storage.alfresco.model.ContainerEntry;
import vn.com.unit.storage.alfresco.model.ContainerList;
import vn.com.unit.storage.alfresco.model.NetworkEntry;
import vn.com.unit.storage.alfresco.model.NetworkList;

/**
 * This class contains constants and methods that are common across the Alfresco Public API regardless of where the target repository is
 * hosted.
 *
 * @author tantm
 *
 */
abstract public class AlfrescoBasePublicAPI {

    /** The Constant SITES_URL. */
    public static final String SITES_URL = "/public/alfresco/versions/1/sites/";

    /** The Constant PEOPLE_URL. */
    public static final String PEOPLE_URL = "/public/alfresco/versions/1/people/";

    /** The Constant NODES_URL. */
    public static final String NODES_URL = "/public/alfresco/versions/1/nodes/";

    /** The site. */
    protected String api, site;

    /** The home network. */
    private String homeNetwork;

    /**
     * Check if path is existed If no, create all folder in path If yes, do nothing.
     *
     * @param path
     *            the path
     * @return folder at the end of path
     * @throws Exception
     *             the exception
     * @author tantm
     */
    public Folder getFolder(String path) throws Exception {
        Session cmisSession = getCmisSession();
        Folder currentFolder = (Folder) cmisSession.getObject(getRootFolderId(site));

        path = path.replaceAll("\\\\", "/"); // Replace \ to /

        Map<String, Object> props = new HashMap<String, Object>();
        props.put("cmis:objectTypeId", "cmis:folder");
        props.put("cmis:name", "");

        for (String folderName : path.split("/")) {
            if (folderName.length() == 0)
                continue;

            try {
                // Making an assumption here that you probably wouldn't normally do
                currentFolder = (Folder) cmisSession.getObjectByPath(currentFolder.getPath() + "/" + folderName);
            } catch (CmisObjectNotFoundException onfe) {
                props.put("cmis:name", folderName);
                currentFolder = currentFolder.createFolder(props);
            }
        }

        return currentFolder;
    }

    /**
     * Creates the or update document.
     *
     * @param path
     *            the path
     * @param content
     *            the content
     * @param fileName
     *            the file name
     * @param mime
     *            the mime
     * @param isMajor
     *            the is major
     * @return the document
     * @throws Exception
     *             the exception
     * @author tantm
     */
    public Document createOrUpdateDocument(String path, byte[] content, String fileName, String mime, boolean isMajor) throws Exception {
        Session cmisSession = getCmisSession();
        Folder parentFolder = getFolder(path);

        Map<String, Object> props = new HashMap<String, Object>();
        ;

        // Add the object type ID if it wasn't already
        if (props.get("cmis:objectTypeId") == null) {
            props.put("cmis:objectTypeId", "cmis:document");
        }

        // Add the name if it wasn't already
        if (props.get("cmis:name") == null) {
            props.put("cmis:name", fileName);
        }

        Document document = null;

        try {
            ContentStream contentStream = cmisSession.getObjectFactory().createContentStream(fileName, content.length, mime,
                    new ByteArrayInputStream(content));

            document = parentFolder.createDocument(props, contentStream, isMajor ? VersioningState.MAJOR : VersioningState.MINOR);

        } catch (CmisContentAlreadyExistsException ccaee) {
            ContentStream contentStream = cmisSession.getObjectFactory().createContentStream(fileName, content.length, mime,
                    new ByteArrayInputStream(content));

            document = (Document) cmisSession.getObjectByPath(parentFolder.getPath() + "/" + fileName);

            Document pwc = (Document) cmisSession.getObject(document.checkOut());
            pwc.checkIn(isMajor, props, contentStream, null);
        }

        // Remove version in id
        document = (Document) cmisSession.getObject(document.getId().split(";")[0]);

        return document;
    }

    /**
     * Creates the or update document.
     *
     * @param path
     *            the path
     * @param file
     *            the file
     * @param fileName
     *            the file name
     * @param mime
     *            the mime
     * @param isMajor
     *            the is major
     * @return the document
     * @throws Exception
     *             the exception
     * @author tantm
     */
    public Document createOrUpdateDocument(String path, File file, String fileName, String mime, boolean isMajor) throws Exception {
        return createOrUpdateDocument(getFolder(path), file, fileName, mime, isMajor, null);
    }

    /**
     * Creates the or update document.
     *
     * @param parentFolder
     *            the parent folder
     * @param file
     *            the file
     * @param fileName
     *            the file name
     * @param mime
     *            the mime
     * @param isMajor
     *            the is major
     * @param props
     *            the props
     * @return the document
     * @throws Exception
     *             the exception
     * @author tantm
     */
    private Document createOrUpdateDocument(Folder parentFolder, File file, String fileName, String mime, boolean isMajor,
            Map<String, Object> props) throws Exception {
        Session cmisSession = getCmisSession();

        // create a map of properties if one wasn't passed in
        if (props == null) {
            props = new HashMap<String, Object>();
        }

        // Add the object type ID if it wasn't already
        if (props.get("cmis:objectTypeId") == null) {
            props.put("cmis:objectTypeId", "cmis:document");
        }

        // Add the name if it wasn't already
        if (props.get("cmis:name") == null) {
            props.put("cmis:name", fileName);
        }

        Document document = null;

        try {
            ContentStream contentStream = cmisSession.getObjectFactory().createContentStream(fileName, file.length(), mime,
                    new FileInputStream(file));
            document = parentFolder.createDocument(props, contentStream, isMajor ? VersioningState.MAJOR : VersioningState.MINOR);
        } catch (CmisContentAlreadyExistsException ccaee) {
            ContentStream contentStream = cmisSession.getObjectFactory().createContentStream(fileName, file.length(), mime,
                    new FileInputStream(file));

            document = (Document) cmisSession.getObjectByPath(parentFolder.getPath() + "/" + fileName);

            Document pwc = (Document) cmisSession.getObject(document.checkOut());
            pwc.checkIn(isMajor, props, contentStream, null);
        }

        // Remove version in id
        document = (Document) cmisSession.getObject(document.getId().split(";")[0]);

        return document;
    }

    /**
     * Revert document.
     *
     * @param documentId
     *            the document id
     * @param version
     *            the version
     * @param isMajor
     *            the is major
     * @param comment
     *            the comment
     * @return the document
     * @throws Exception
     *             the exception
     * @author tantm
     */
    public Document revertDocument(String documentId, String version, boolean isMajor, String comment) throws Exception {
        Session cmisSession = getCmisSession();
        Document document = (Document) cmisSession.getObject(documentId.split(";")[0]);

        if (document == null)
            throw new Exception("Can not find document with Id: " + documentId);

        Document targetVersion = null;

        for (Document docVersion : document.getAllVersions()) {
            if (docVersion.getVersionLabel().equals(version)) {
                targetVersion = docVersion;
                break;
            }
        }

        if (targetVersion == null)
            throw new Exception("Can not find document with version: " + version);

        Document pwc = (Document) cmisSession.getObject(document.checkOut());
        ObjectId updatedId = pwc.checkIn(isMajor, null, targetVersion.getContentStream(), comment);
        document = (Document) cmisSession.getObject(updatedId);
        return document;
    }

    /**
     * Gets the document by id.
     *
     * @param documentId
     *            the document id
     * @return the document by id
     * @author tantm
     */
    public Document getDocumentById(String documentId) {
        Session cmisSession = getCmisSession();
        Document document = (Document) cmisSession.getObject(documentId);

        return document;
    }

    /**
     * Download document.
     *
     * @param destinationPath
     *            the destination path
     * @param documentId
     *            the document id
     * @throws Exception
     *             the exception
     * @author tantm
     */
    public void downloadDocument(String destinationPath, String documentId) throws Exception {
        try {
            Document document = getDocumentById(documentId);
            FileUtils.download(document, destinationPath);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Download document stream.
     *
     * @param documentId
     *            the document id
     * @return the input stream
     * @throws Exception
     *             the exception
     * @author tantm
     */
    public InputStream downloadDocumentStream(String documentId) throws Exception {
        try {
            Document document = getDocumentById(documentId);
            return document.getContentStream().getStream();
            // document.getExtensions();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Validate connection.
     *
     * @return true, if successful
     * @author tantm
     */
    public boolean validateConnection() {
        try {
            Session cmisSession = getCmisSession();
            if (cmisSession == null)
                throw new Exception();
            @SuppressWarnings("unused")
            Folder rootFolder = (Folder) cmisSession.getObject(getRootFolderId(site));

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Creates the user.
     *
     * @param username
     *            the username
     * @param password
     *            the password
     * @param email
     *            the email
     * @return true, if successful
     * @throws Exception
     *             the exception
     * @author tantm
     */
    public boolean createUser(String username, String password, String email) throws Exception {
        try {
            GenericUrl peopleUrl = new GenericUrl(api + getHomeNetwork() + PEOPLE_URL);
            HttpContent body = new ByteArrayContent("application/json", ("{\"id\": \"" + username + "\", \"firstName\": \"" + username
                    + "\", \"email\":\"" + email + "\", \"password\":\"" + password + "\"}").getBytes());
            HttpRequest request = getRequestFactory().buildPostRequest(peopleUrl, body);
            HttpResponse response = request.execute();
            return response.getStatusCode() == 201;

        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * Execute query.
     *
     * @param query
     *            the query
     * @author tantm
     */
    public void executeQuery(String query) {
        // query = "SELECT cmis:objectId, cmis:name, SCORE() AS \"score\" FROM cmis:document WHERE CONTAINS('test')";

        Session cmisSession = getCmisSession();
        ItemIterable<QueryResult> results = cmisSession.query(query, false);

        for (QueryResult hit : results) {
            for (PropertyData<?> property : hit.getProperties()) {

                String queryName = property.getQueryName();
                Object value = property.getFirstValue();

                System.out.println(queryName + ": " + value);
            }
            System.out.println("--------------------------------------");
        }
    }

    /**
     * Use the CMIS API to get a handle to the root folder of the target site, then create a new folder, then create a new document in the
     * new folder.
     *
     * @param parentFolderId
     *            the parent folder id
     * @param folderName
     *            the folder name
     * @return Folder
     * @throws Exception
     *             the exception
     * @author jpotts
     */
    public Folder createFolder(String parentFolderId, String folderName) throws Exception {
        Session cmisSession = getCmisSession();
        Folder rootFolder = (Folder) cmisSession.getObject(getRootFolderId(site));

        Folder subFolder = null;
        try {
            // Making an assumption here that you probably wouldn't normally do
            subFolder = (Folder) cmisSession.getObjectByPath(rootFolder.getPath() + "/" + folderName);
            System.out.println("Folder already existed!");
        } catch (CmisObjectNotFoundException onfe) {
            Map<String, Object> props = new HashMap<String, Object>();
            props.put("cmis:objectTypeId", "cmis:folder");
            props.put("cmis:name", folderName);
            subFolder = rootFolder.createFolder(props);
            String subFolderId = subFolder.getId();
            System.out.println("Created new folder: " + subFolderId);
        }

        return subFolder;
    }

    /**
     * Gets the home network.
     *
     * @return the home network
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    public String getHomeNetwork() throws IOException {
        if (this.homeNetwork == null) {
            GenericUrl url = new GenericUrl(api);

            HttpRequest request = getRequestFactory().buildGetRequest(url);

            NetworkList networkList = request.execute().parseAs(NetworkList.class);
            System.out.println("Found " + networkList.list.pagination.totalItems + " networks.");
            for (NetworkEntry networkEntry : networkList.list.entries) {
                if (networkEntry.entry.homeNetwork) {
                    this.homeNetwork = networkEntry.entry.id;
                }
            }

            if (this.homeNetwork == null) {
                this.homeNetwork = "-default-";
            }

            System.out.println("Your home network appears to be: " + homeNetwork);
        }
        return this.homeNetwork;
    }

    /**
     * Creates the document.
     *
     * @param parentFolder
     *            the parent folder
     * @param file
     *            the file
     * @param fileType
     *            the file type
     * @return the document
     * @throws FileNotFoundException
     *             the file not found exception
     * @author tantm
     */
    public Document createDocument(Folder parentFolder, File file, String fileType) throws FileNotFoundException {
        return createDocument(parentFolder, file, fileType, null);
    }

    /**
     * Use the CMIS API to create a document in a folder.
     *
     * @param parentFolder
     *            the parent folder
     * @param file
     *            the file
     * @param fileType
     *            the file type
     * @param props
     *            the props
     * @return the document
     * @throws FileNotFoundException
     *             the file not found exception
     * @author jpotts
     */
    public Document createDocument(Folder parentFolder, File file, String fileType, Map<String, Object> props)
            throws FileNotFoundException {

        Session cmisSession = getCmisSession();

        String fileName = file.getName();

        // create a map of properties if one wasn't passed in
        if (props == null) {
            props = new HashMap<String, Object>();
        }

        // Add the object type ID if it wasn't already
        if (props.get("cmis:objectTypeId") == null) {
            props.put("cmis:objectTypeId", "cmis:document");
        }

        // Add the name if it wasn't already
        if (props.get("cmis:name") == null) {
            props.put("cmis:name", fileName);
        }

        ContentStream contentStream = cmisSession.getObjectFactory().createContentStream(fileName, file.length(), fileType,
                new FileInputStream(file));

        Document document = null;
        try {
            document = parentFolder.createDocument(props, contentStream, null);
            System.out.println("Created new document: " + document.getId());
        } catch (CmisContentAlreadyExistsException ccaee) {
            document = (Document) cmisSession.getObjectByPath(parentFolder.getPath() + "/" + fileName);
            System.out.println("Document already exists: " + fileName);
        }

        return document;
    }

    /**
     * Use the REST API to find the documentLibrary folder for the target site.
     *
     * @param site
     *            the site
     * @return String
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author jpotts
     */
    public String getRootFolderId(String site) throws IOException {

        GenericUrl containersUrl = new GenericUrl(api + getHomeNetwork() + SITES_URL + site + "/containers");
        System.out.println(containersUrl);
        HttpRequest request = getRequestFactory().buildGetRequest(containersUrl);
        ContainerList containerList = request.execute().parseAs(ContainerList.class);
        String rootFolderId = null;
        for (ContainerEntry containerEntry : containerList.list.entries) {
            if (containerEntry.entry.folderId.equals("documentLibrary")) {
                rootFolderId = containerEntry.entry.id;
                break;
            }
        }
        return rootFolderId;
    }

    /**
     * Use the REST API to "like" an object.
     *
     * @param objectId
     *            the object id
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    public void like(String objectId) throws IOException {
        GenericUrl likeUrl = new GenericUrl(api + getHomeNetwork() + NODES_URL + objectId + "/ratings");
        HttpContent body = new ByteArrayContent("application/json", "{\"id\": \"likes\", \"myRating\": true}".getBytes());
        HttpRequest request = getRequestFactory().buildPostRequest(likeUrl, body);
        request.execute();
        System.out.println("You liked: " + objectId);
    }

    /**
     * Use the REST API to comment on an object.
     *
     * @param objectId
     *            the object id
     * @param comment
     *            the comment
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    public void comment(String objectId, String comment) throws IOException {
        GenericUrl commentUrl = new GenericUrl(api + getHomeNetwork() + NODES_URL + objectId + "/comments");
        HttpContent body = new ByteArrayContent("application/json", ("{\"content\": \"" + comment + "\"}").getBytes());
        HttpRequest request = getRequestFactory().buildPostRequest(commentUrl, body);
        request.execute();
        System.out.println("You commented on: " + objectId);
    }

    /**
     * Gets the local file.
     *
     * @return the local file
     * @author tantm
     */
    public File getLocalFile() {
        String filePath = AlfrescoConfig.getConfig().getProperty("local_file_path");
        return new File(filePath);
    }

    /**
     * Gets the local file type.
     *
     * @return the local file type
     * @author tantm
     */
    public String getLocalFileType() {
        return AlfrescoConfig.getConfig().getProperty("local_file_type");
    }

    /**
     * Gets the cmis session.
     *
     * @return the cmis session
     * @author tantm
     */
    abstract public Session getCmisSession();

    /**
     * Gets the request factory.
     *
     * @return the request factory
     * @author tantm
     */
    abstract public HttpRequestFactory getRequestFactory();
}
