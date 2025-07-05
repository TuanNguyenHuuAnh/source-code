/*******************************************************************************
 * Class        ：DocumentService
 * Created date ：2017/04/18
 * Lasted date  ：2017/04/18
 * Author       ：TaiTM
 * Change log   ：2017/04/18：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.com.unit.cms.admin.all.core.DocumentCategoryNode;
import vn.com.unit.cms.admin.all.dto.DocumentCategoryEditDto;
import vn.com.unit.cms.admin.all.dto.DocumentViewAuthoritySelectDto;
import vn.com.unit.cms.core.module.document.dto.DocumentEditDto;
import vn.com.unit.cms.core.module.document.dto.DocumentSearchDto;
import vn.com.unit.cms.core.module.document.dto.DocumentSearchResultDto;
import vn.com.unit.cms.core.module.document.entity.Document;
import vn.com.unit.cms.core.module.document.entity.DocumentLanguage;
import vn.com.unit.core.dto.JcaGroupEmailDto;
import vn.com.unit.ep2p.core.service.DocumentWorkflowCommonService;

/**
 * DocumentService
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public interface DocumentService extends DocumentWorkflowCommonService<DocumentEditDto, DocumentEditDto>,
        CmsCommonSearchFillterService<DocumentSearchDto, DocumentSearchResultDto, DocumentEditDto> {

    /**
     * @param id
     * @return
     */
    public DocumentEditDto getDocumentObject(Long id);

    /**
     * @param exceptCategoryId
     * @return
     */
    public List<DocumentCategoryNode> findSelectionCategoryTree(Long exceptCategoryId, String customerTypeIdText);

    /**
     * @param exceptCategoryId
     * @return
     */
    public List<DocumentCategoryEditDto> getCategoriesForSelection(Long exceptCategoryId, String customerTypeIdText);

    /**
     * @param code
     * @return
     */
    public int countDocumentByCode(String code);

    /**
     * 
     * @param documentId
     * @param versionId
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public boolean requestDownloadDocument(Long documentId, Long versionId, String token, HttpServletRequest request,
            HttpServletResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * 
     */
    public String genDocumentShareToken(Long documentId, Long versionId)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 
     * @return
     */
    public List<DocumentViewAuthoritySelectDto> getSelectionViewAuthorityItems(Locale locale);

    /**
     * @param imgUrl
     * @param request
     * @param response
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public void requestDownloadImage(String imgUrl, HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * Get list dependencies of Product categories
     * 
     * @author tranlth - 23/05/219
     * @param documentTypeId
     * @param lstStatus
     * @return List<Map <String, String>>
     */
    public List<Map<String, String>> listDependencies(Long documentTypeId, List<Long> lstStatus);

    List<String> getListRoleNameByUserName(String userNameLogin);
    
    public Document getlsDocument(Long id); 

    boolean isUserHasRoleMaker(String username);

    public String getLsDocument(Long id, String lang);
    public String getbutton(Long id, Long idp);
    /**
     * @author vUnt
     * @param roleCode
     * @param groupCode
     * @return
     */
	List<JcaGroupEmailDto> lstEmailCcByCondition(String roleCode, String groupCode);

	public List<JcaGroupEmailDto> findGourpMailByTaskId(Long jpmTaskId);

	public List<JcaGroupEmailDto> getListRoleNameByUserNameAndProcessDeployId(String userNameLogin, Long processDeployId, String permissonCode);

}
