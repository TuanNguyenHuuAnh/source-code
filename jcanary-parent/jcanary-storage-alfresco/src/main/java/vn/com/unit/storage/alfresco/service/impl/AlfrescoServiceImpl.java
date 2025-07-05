/*******************************************************************************
 * Class        AlfrescoServiceImpl
 * Created date ：2020/07/28
 * Lasted date  ：2020/07/28
 * Author       ：tantm
 * Change log   ：2020/07/28：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.service.impl;

import java.io.File;
import java.io.InputStream;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.storage.alfresco.core.AlfrescoBaseOnPrem;
import vn.com.unit.storage.alfresco.service.AlfrescoService;

/**
 * 
 * AlfrescoServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AlfrescoServiceImpl implements AlfrescoService {

    @Override
    public Folder getFolder(String host, String username, String password, String site, String path) throws Exception {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        try {
            return alfresco.getFolder(path);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public String createOrUpdateDocument(String host, String username, String password, String site, String path, byte[] content,
            String fileName, String mime, boolean isMajor) throws Exception {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        try {
            // Remove version in id
            return alfresco.createOrUpdateDocument(path, content, fileName, mime, isMajor).getId().split(";")[0];
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public String createOrUpdateDocument(String host, String username, String password, String site, String path, File file,
            String fileName, String mime, boolean isMajor) throws Exception {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        try {
            // Remove version in id
            return alfresco.createOrUpdateDocument(path, file, fileName, mime, isMajor).getId().split(";")[0];
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public Document revertDocument(String host, String username, String password, String site, String documentId, String version,
            boolean isMajor, String comment) throws Exception {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        try {
            return alfresco.revertDocument(documentId, version, isMajor, comment);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public Document getDocumentById(String host, String username, String password, String site, String documentId) {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        documentId = documentId.split(";")[0];
        try {
            return alfresco.getDocumentById(documentId);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public boolean downloadDocument(String host, String username, String password, String site, String destinationPath, String documentId) {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        documentId = documentId.split(";")[0];
        try {
            alfresco.downloadDocument(destinationPath, documentId);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public InputStream downloadDocumentStream(String host, String username, String password, String site, String documentId) {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        documentId = documentId.split(";")[0];
        try {
            return alfresco.downloadDocumentStream(documentId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public InputStream downloadDocumentStream(String host, String username, String password, String site, String documentId,
            String version) {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        documentId = documentId.split(";")[0] + ";" + version;
        try {
            return alfresco.downloadDocumentStream(documentId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean downloadDocument(String host, String username, String password, String site, String destinationPath, String documentId,
            String version) {
        documentId = documentId.split(";")[0] + ";" + version;
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        try {
            alfresco.downloadDocument(destinationPath, documentId);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validateConnection(String host, String username, String password, String site) {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        return alfresco.validateConnection();
    }

    @Override
    public void executeQuery(String host, String username, String password, String site, String query) {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        alfresco.executeQuery(query);
    }

    @Override
    public boolean downloadDocumentWithVersion(String host, String username, String password, String site, String destinationPath,
            String documentId) {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        try {
            alfresco.downloadDocument(destinationPath, documentId);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public String createOrUpdateDocumentWithVersion(String host, String username, String password, String site, String path, byte[] content,
            String fileName, String mime, boolean isMajor) throws Exception {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        try {
            // Remove version in id
            return alfresco.createOrUpdateDocument(path, content, fileName, mime, isMajor).getId();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public boolean deleteDocument(String host, String username, String password, String site, String documentId, boolean deleteAllVersion) {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        documentId = documentId.split(";")[0];
        boolean res = true;
        try {
            Document document = alfresco.getDocumentById(documentId);
            document.delete(deleteAllVersion);
        } catch (Exception ex) {
            ex.printStackTrace();
            res = false;
        }
        return res;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.jcanary.service.AlfrescoService#getDocumentById(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Document getDocumentById(String host, String username, String password, String site, String documentId, String version) {
        AlfrescoBaseOnPrem alfresco = new AlfrescoBaseOnPrem(host, username, password, site);
        documentId = documentId.split(";")[0] + ";" + version;
        try {
            return alfresco.getDocumentById(documentId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
