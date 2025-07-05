/*******************************************************************************
 * Class        ：DocumentMembershipService
 * Created date ：2019/10/25
 * Lasted date  ：2019/10/25
 * Author       ：KhuongTH
 * Change log   ：2019/10/25：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.core.dto.JcaAccountDto;
import vn.com.unit.ep2p.dto.OZDocDto;

/**
 * DocumentMembershipService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public interface DocumentMembershipService {

    /**
     * updateMembershipDocumentFreeProcess
     * @param objectDto
     * @author KhuongTH
     */
    public void updateMembershipDocumentFreeProcess(OZDocDto objectDto);
    
    /**
     * 
     * deleteMembershipDocumentFreeProcess
     * @param accountId
     * @param documentId
     * @param type
     * @author taitt
     */
    public void deleteMembershipDocumentFreeProcess(Long accountId,Long documentId,String type);
    
    /**
     * 
     * addMembershipDocumentFreeProcess
     * @param accountId
     * @param documentId
     * @param type
     * @author taitt
     */
    public void addMembershipDocumentFreeProcess(List<Long> accountId,Long documentId);
    
    /**
     * 
     * addMembershipDocumentReference
     * @param accountId
     * @param documentId
     * @author taitt
     */
    public void addMembershipDocumentReference(OZDocDto objectDto);
    
    /**
     * 
     * @param reference
     * @param referenceId
     * @return
     */
//    public  List<DocumentMembership> getMembershipDocumentByType(String reference,Long referenceId);
    
    /**
     * 
     * checkUserIdWithRoleReferenceDocument
     * @param userId
     * @param reference
     * @param referenceId
     * @return
     * @author taitt
     */
    public boolean checkUserIdWithRoleReferenceDocument(Long userId,String reference,Long referenceId,boolean isDataArchive);
    
    /**
     * 
     * @param reference
     * @param referenceId
     * @param memberType
     * @return
     */
    public List<JcaAccountDto> findMembershipDocumentByMemberType(String reference,Long referenceId,String memberType);
}
