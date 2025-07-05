/********************************************************************************
* Class        : StorageConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.storage.constant;

import vn.com.unit.common.constant.CommonConstant;

/**
* <p> StorageConstant </p>
* 
* @version : 01-00
* @since 01-00
* @author : TaiTT
 */
public class StorageConstant extends CommonConstant {


	// BEAN POOL
	public static final String BEAN_LOCAL_FILE_STORAGE = "localFileStorage";
	public static final String BEAN_WEB_DAV_FILE_STORAGE = "webDAVFileStorage";
	public static final String BEAN_ECM_ALFRESCO_FILE_STORAGE = "eCMAlfrescoFileStorage";
	public static final String BEAN_FILENET_FILE_STORAGE = "fileNetFileStorage";

	// TABLE
	public static final String TABLE_JCA_REPOSITORY = "JCA_REPOSITORY";
}