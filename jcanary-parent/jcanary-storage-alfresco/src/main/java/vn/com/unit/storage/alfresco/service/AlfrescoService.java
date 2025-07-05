/*******************************************************************************
 * Class        ：AlfrescoService
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：tantm
 * Change log   ：2020/12/08：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.service;

import java.io.File;
import java.io.InputStream;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;

/**
 * AlfrescoService.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface AlfrescoService {

    /**
     * Gets the folder.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param path
     *            the path type String
     * @return the folder
     * @throws Exception
     *             the exception
     * @author tantm
     */
    Folder getFolder(String host, String username, String password, String site, String path) throws Exception;

    /**
     * Creates the or update document.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param path
     *            the path type String
     * @param content
     *            the content type byte[]
     * @param fileName
     *            the file name type String
     * @param mime
     *            the mime type String
     * @param isMajor
     *            the is major type boolean
     * @return the string
     * @throws Exception
     *             the exception
     * @author tantm
     */
    String createOrUpdateDocument(String host, String username, String password, String site, String path, byte[] content, String fileName,
            String mime, boolean isMajor) throws Exception;

    /**
     * Creates the or update document.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param path
     *            the path type String
     * @param file
     *            the file type File
     * @param fileName
     *            the file name type String
     * @param mime
     *            the mime type String
     * @param isMajor
     *            the is major type boolean
     * @return the string
     * @throws Exception
     *             the exception
     * @author tantm
     */
    String createOrUpdateDocument(String host, String username, String password, String site, String path, File file, String fileName,
            String mime, boolean isMajor) throws Exception;

    /**
     * Revert document.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param documentId
     *            the document id type String
     * @param version
     *            the version type String
     * @param isMajor
     *            the is major type boolean
     * @param comment
     *            the comment type String
     * @return the document
     * @throws Exception
     *             the exception
     * @author tantm
     */
    Document revertDocument(String host, String username, String password, String site, String documentId, String version, boolean isMajor,
            String comment) throws Exception;

    /**
     * Gets the document by id.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param documentId
     *            the document id type String
     * @return the document by id
     * @author tantm
     */
    Document getDocumentById(String host, String username, String password, String site, String documentId);

    /**
     * Gets the document by id.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param documentId
     *            the document id type String
     * @param version
     *            the version type String
     * @return the document by id
     * @author tantm
     */
    Document getDocumentById(String host, String username, String password, String site, String documentId, String version);

    /**
     * Download document.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param destinationPath
     *            the destination path type String
     * @param documentId
     *            the document id type String
     * @return true, if successful
     * @author tantm
     */
    boolean downloadDocument(String host, String username, String password, String site, String destinationPath, String documentId);

    /**
     * Download document.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param destinationPath
     *            the destination path type String
     * @param documentId
     *            the document id type String
     * @param version
     *            the version type String
     * @return true, if successful
     * @author tantm
     */
    boolean downloadDocument(String host, String username, String password, String site, String destinationPath, String documentId,
            String version);

    /**
     * Download document stream.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param documentId
     *            the document id type String
     * @return the input stream
     * @author tantm
     */
    InputStream downloadDocumentStream(String host, String username, String password, String site, String documentId);

    /**
     * Download document stream.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param documentId
     *            the document id type String
     * @param version
     *            the version type String
     * @return the input stream
     * @author tantm
     */
    InputStream downloadDocumentStream(String host, String username, String password, String site, String documentId, String version);

    /**
     * Validate connection.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @return true, if successful
     * @author tantm
     */
    boolean validateConnection(String host, String username, String password, String site);

    /**
     * Execute query.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param query
     *            the query type String
     * @author tantm
     */
    void executeQuery(String host, String username, String password, String site, String query);

    /**
     * Download document with version.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param destinationPath
     *            the destination path type String
     * @param documentId
     *            the document id type String
     * @return true, if successful
     * @author tantm
     */
    boolean downloadDocumentWithVersion(String host, String username, String password, String site, String destinationPath,
            String documentId);

    /**
     * Creates the or update document with version.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param path
     *            the path type String
     * @param content
     *            the content type byte[]
     * @param fileName
     *            the file name type String
     * @param mime
     *            the mime type String
     * @param isMajor
     *            the is major type boolean
     * @return the string
     * @throws Exception
     *             the exception
     * @author tantm
     */
    String createOrUpdateDocumentWithVersion(String host, String username, String password, String site, String path, byte[] content,
            String fileName, String mime, boolean isMajor) throws Exception;

    /**
     * Delete document.
     *
     * @param host
     *            the host type String
     * @param username
     *            the username type String
     * @param password
     *            the password type String
     * @param site
     *            the site type String
     * @param documentId
     *            the document id type String
     * @param deleteAllVersion
     *            the delete all version type boolean
     * @return true, if successful
     * @author tantm
     */
    boolean deleteDocument(String host, String username, String password, String site, String documentId, boolean deleteAllVersion);
}
