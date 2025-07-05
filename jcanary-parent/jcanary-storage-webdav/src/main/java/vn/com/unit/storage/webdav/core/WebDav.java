/*******************************************************************************
 * Class        ：WebDav
 * Created date ：2020/12/08
 * Lasted date  ：2020/12/08
 * Author       ：tantm
 * Change log   ：2020/12/08：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.webdav.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.DeleteMethod;
import org.apache.jackrabbit.webdav.client.methods.MkColMethod;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.unit.common.logger.DebugLogger;
import vn.com.unit.common.utils.CommonFileUtil;
import vn.com.unit.common.utils.CommonStringUtil;

/**
 * WebDav
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class WebDav {

    private HttpClient client;
    private HostConfiguration hostConfig;
    private String host;
    private String context;
    private static final int MAX_HOST_CONNECTIONS = 20;

    private static final Logger logger = LoggerFactory.getLogger(WebDav.class);

    public WebDav(String urlWebDAV, String userId, String password) throws MalformedURLException {
        URL url = new URL(urlWebDAV);
        this.host = url.getProtocol().concat("://").concat(url.getHost());
        if (url.getPort() > 0) {
            this.host += ":".concat(String.valueOf(url.getPort()));
        }
        this.context = url.getPath();
        hostConfig = new HostConfiguration();
        hostConfig.setHost(this.host);
        HttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setMaxConnectionsPerHost(hostConfig, MAX_HOST_CONNECTIONS);
        connectionManager.setParams(params);
        client = new HttpClient(connectionManager);
        Credentials creds = new UsernamePasswordCredentials(userId, password);
        client.getState().setCredentials(AuthScope.ANY, creds);
        client.setHostConfiguration(hostConfig);
    }

    /**
     * Check folder exists
     * 
     * @param resourcePath
     *            path of folder
     * @return {@code true} if folder is exists
     * @throws IOException
     *             type IOException
     * @author tantm
     */
    public boolean checkExistsFolder(String resourcePath) throws IOException {
        boolean result = false;
        DavMethod pFind = null;
        try {
            pFind = new PropFindMethod(toUrlString(this.host, this.context, resourcePath), DavConstants.PROPFIND_ALL_PROP,
                    DavConstants.DEPTH_1);
            client.executeMethod(pFind);
            /*
             * logger.info("WebDav checkExistsFolder resourcePath: " + resourcePath + ", checkExistsFolder status: " +
             * pFind.getStatusText());
             */
            logger.error("[checkExistsFolder] Status Code: ", pFind.getStatusCode());
            if (pFind.getStatusCode() != HttpStatus.SC_NOT_FOUND) {
                result = true;
            }
        } catch (IOException e) {
            logger.error("[checkExistsFolder] Error: ", e.getMessage());
            throw e;
        } finally {
            if (null != pFind) {
                pFind.releaseConnection();
            }
        }
        return result;
    }

    /**
     * Create folder
     * 
     * @param resourcePath
     *            path of folder
     * @throws IOException
     *             type IOException
     * @author tantm
     */
    public void doCreateFolder(String resourcePath) throws IOException {
        if (this.checkExistsFolder(toUrlString(this.host, this.context, CommonFileUtil.getPathNoEndSeparator(resourcePath)))) {
            return;
        }
        String path = CommonStringUtil.EMPTY;
        boolean hasEndSlash = false;
        String context = CommonStringUtil.EMPTY;
        if (CommonStringUtil.isNotBlank(this.context)) {
            String[] temp = this.context.split("/");
            if (temp.length > 2) {
                context = "/".concat(temp[1]);
            } else {
                context = this.context;
            }
            if (this.context.endsWith("/")) {
                hasEndSlash = true;
            }
            path = path.concat(this.context);
        }

        if (CommonStringUtil.isNotBlank(resourcePath)) {
            if (resourcePath.startsWith("/") && hasEndSlash) {
                path = path.concat(resourcePath.substring(1, resourcePath.length()));
            } else if (!resourcePath.startsWith("/") && !hasEndSlash) {
                path = path.concat("/").concat(resourcePath);
            } else {
                path = path.concat(resourcePath);
            }
        }
        String[] folders = CommonFileUtil.getPathNoEndSeparator(path).split("/");
        String fullPath = CommonStringUtil.EMPTY;
        DavMethod method = null;
        for (String folder : folders) {
            if (!"/".concat(folder).equals(context)) {
                fullPath = fullPath.concat("/").concat(folder);
            }

            if (!this.checkExistsFolder(fullPath)) {
                try {
                    method = new MkColMethod(toUrlString(this.host, context, fullPath));
                    client.executeMethod(method);
                    /*
                     * logger.info("WebDav doCreateFolder resourcePath: " + fullPath + ", doCreateFolder status : " +
                     * method.getStatusText());
                     */
                } catch (IOException e) {
                    logger.error("[doCreateFolder] Error: ", e);
                    throw e;
                } finally {
                    if (null != method) {
                        method.releaseConnection();
                    }
                }
            }
        }
        return;
    }

    /**
     * Upload file
     * 
     * @param resourcePath
     *            path to upload file
     * @param streamFile
     *            steam of file
     * @param isDeleteAndCreate
     *            true if delete file exist and new create
     * @return {@code true} if upload success
     * @throws IOException
     *             type IOException
     * @author tantm
     */
    public boolean doUploadFile(String resourcePath, InputStream streamFile, Boolean isDeleteAndCreate) throws IOException {
        PutMethod method = null;
        // PT Tracking
        DebugLogger debugLogger = new DebugLogger();
        DebugLogger.debug("[PT Tracking] | [doUploadFile] | [%d] | [Begin] | [%s] | [%d] | [%s]", Thread.currentThread().getId(),
                debugLogger.getStart(), 0, resourcePath);

        try {
            // Create folder if not exists
            this.doCreateFolder(resourcePath);

            // Delete file if exists
            if (isDeleteAndCreate) {
                try {
                    this.doDeleteFile(resourcePath);
                } catch (DavException e) {
                    logger.error("[doDeleteFile] Error: ", e);
                }
            }
            String fullURL = toUrlString(this.host, this.context, resourcePath);
            method = new PutMethod(fullURL);
            RequestEntity requestEntity = new InputStreamRequestEntity(streamFile);
            ((PutMethod) method).setRequestEntity(requestEntity);
            client.executeMethod(method);
            /* logger.info("WebDav resourcePath: " + resourcePath + ", doUploadFile status: " + method.getStatusText()); */
            DebugLogger.debug("[doUploadFile] Status Infor upload file: [%s] | [%d] | [%s]", fullURL, method.getStatusCode(),
                    method.getStatusText());
            if (method.getStatusCode() != HttpStatus.SC_CREATED && method.getStatusCode() != HttpStatus.SC_NO_CONTENT) {
                return false;
            }
            return true;
        } catch (IOException e) {
            logger.error("[doUploadFile] Error: ", e);
            throw e;
        } finally {
            if (null != method) {
                method.releaseConnection();
            }
            // PT Tracking
            debugLogger.setEndTime();
            DebugLogger.debug("[PT Tracking] | [doUploadFile] | [%d] | [End] | [%s] | [%d] | [%s]", Thread.currentThread().getId(),
                    debugLogger.getEnd(), debugLogger.getElapsedTime(), resourcePath);
        }
    }

    /**
     * Download file
     * 
     * @param resourcePath
     *            path of file
     * @return byte of file
     * @throws IOException
     *             type {@link IOException}
     * @author tantm
     */
    public byte[] doDownloadFile(String resourcePath) throws IOException {
        GetMethod method = null;
        // PT Tracking
        DebugLogger debugLogger = new DebugLogger();
        DebugLogger.debug("[PT Tracking] | [doDownloadFile] | [%d] | [Begin] | [%s] | [%d] | [%s]", Thread.currentThread().getId(),
                debugLogger.getStart(), 0, resourcePath);
        byte[] response = null;
        try {
            String fullURL = toUrlString(this.host, this.context, resourcePath);
            method = new GetMethod(fullURL);
            client.executeMethod(method);
            DebugLogger.debug("[doDownloadFile] Status info: [%s] | [%d] | [%s]", fullURL, method.getStatusCode(), method.getStatusText());
            if (method.getStatusCode() != HttpStatus.SC_NOT_FOUND) {
                InputStream inputStream = method.getResponseBodyAsStream();
                response = CommonFileUtil.toByteArray(inputStream);
            }
        } catch (IOException e) {
            logger.error("[doDownloadFile] Error: ", e.getMessage());
            throw e;
        } finally {
            if (null != method) {
                method.releaseConnection();
            }
            // PT Tracking
            debugLogger.setEndTime();
            DebugLogger.debug("[PT Tracking] | [doDownloadFile] | [%d] | [End] | [%s] | [%d] | [%s]", Thread.currentThread().getId(),
                    debugLogger.getEnd(), debugLogger.getElapsedTime(), resourcePath);
        }
        return response;
    }

    /**
     * Delete file
     * 
     * @param resourcePath
     *            path of file
     * @return {code true} if deleted success
     * @throws IOException
     *             type {@link IOException}
     * @throws DavException
     *             type {@link DavException}
     * @author tantm
     */
    public boolean doDeleteFile(String resourcePath) throws IOException, DavException {
        DeleteMethod method = null;
        try {
            String fullURL = toUrlString(this.host, this.context, resourcePath);
            method = new DeleteMethod(fullURL);
            client.executeMethod(method);
            if (method.getStatusCode() != HttpStatus.SC_NOT_FOUND) {
                method.checkSuccess();
            }
            return true;
        } catch (IOException | DavException e) {
            logger.error("[doDeleteFile] Error: ", e);
            throw e;
        } finally {
            if (null != method) {
                method.releaseConnection();
            }
        }
    }

    /**
     * Create url from host, context and path
     * 
     * @param host
     *            host of webdav
     * @param context
     *            as subfolder
     * @param resourcePath
     *            path of file
     * @return url of file
     * @author tantm
     */
    private String toUrlString(String host, String context, String resourcePath) {
        String path = CommonStringUtil.EMPTY;
        if (CommonStringUtil.isNoneBlank(context)) {
            path = context;
        }
        if (CommonStringUtil.isNoneBlank(resourcePath)) {
            path += "/".concat(resourcePath);
        }
        path = path.replace("//", "/");
        return host.concat(path);
    }

    /**
     * Check file exists
     * 
     * @param resourcePath
     *            path of file
     * @return {@code true} if file is exists
     * @author tantm
     */
    public boolean checkExistsFile(String resourcePath) {
        boolean result = false;
        GetMethod method = null;
        try {
            String fullURL = toUrlString(this.host, this.context, resourcePath);
            method = new GetMethod(fullURL);
            client.executeMethod(method);
            // TODO Huetq : check file size
            if (method.getStatusCode() != HttpStatus.SC_NOT_FOUND) {
                result = true;
            }
            DebugLogger.debug("[checkExistsFile] Status info: [%s] | [%d] | [%s] ", fullURL, method.getStatusCode(),
                    method.getStatusText());
        } catch (Exception e) {
            logger.error("[checkExistsFile] Error: ", e.getMessage());
        } finally {
            if (null != method) {
                method.releaseConnection();
            }
        }
        return result;
    }

}
