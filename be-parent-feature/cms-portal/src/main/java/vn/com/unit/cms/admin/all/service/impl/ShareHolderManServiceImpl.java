package vn.com.unit.cms.admin.all.service.impl;

import java.awt.Image;
//import java.text.NumberFormat;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Date;
//import java.util.HashMap;
//import java.util.Hashtable;
//import java.util.Iterator;
import java.util.List;
import java.util.Locale;
//import java.util.Map;

//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;

//import vn.com.unit.cms.admin.all.constant.CommonConstant;
//import vn.com.unit.cms.admin.all.dto.ShareHolderSearchDto;
import vn.com.unit.cms.admin.all.dto.ShareholderDto;
import vn.com.unit.cms.admin.all.dto.SortPageDto;
import vn.com.unit.cms.admin.all.entity.ShareHolder;
import vn.com.unit.cms.admin.all.enumdef.ShareHolderProcessEnum;
//import vn.com.unit.cms.admin.all.enumdef.DocumentProcessEnum;
//import vn.com.unit.cms.admin.all.enumdef.ShareHolderProcessEnum;
//import vn.com.unit.cms.admin.all.enumdef.ShareholderImportEnum;
//import vn.com.unit.cms.admin.all.jcanary.common.util.ConstantImport;
import vn.com.unit.cms.admin.all.repository.ShareHolderManRepository;
import vn.com.unit.cms.admin.all.service.ShareHolderManService;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.cms.core.constant.CmsRoleConstant;
//import vn.com.unit.jcanary.authentication.UserProfile;
import vn.com.unit.core.security.UserProfileUtils;
//import vn.com.unit.cms.admin.all.dto.ShareholderImportDto;
import vn.com.unit.ep2p.admin.dto.SortOrderDto;
//import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.core.ers.service.impl.DocumentWorkflowCommonServiceImpl;
//import vn.com.unit.dto.ItemColsExcelDto;
import vn.com.unit.ep2p.core.exception.BusinessException;
//import vn.com.unit.common.dto.SearchKeyDto;
//import vn.com.unit.cms.admin.constant.ConstantHistoryApprove;
//import vn.com.unit.jcanary.constant.RoleConstant;
//import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;
//import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
//import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
//import vn.com.unit.cms.admin.all.jcanary.enumdef.MasterType;
//import vn.com.unit.jcanary.repository.ProcessRepository;
//import vn.com.unit.jcanary.service.HistoryApproveService;
//import vn.com.unit.jcanary.service.JBPMService;
//import vn.com.unit.jcanary.service.ProcessService;
//import vn.com.unit.ep2p.utils.SearchUtil;
//import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
//import vn.com.unit.util.ConstantImport;
import vn.com.unit.ep2p.core.utils.Utility;
//import vn.com.unit.cms.admin.all.jcanary.dto.CommonSearchDto;

/**
 * ShareHolderManagementServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
@Service
@Transactional
public class ShareHolderManServiceImpl extends DocumentWorkflowCommonServiceImpl<ShareholderDto, ShareholderDto> implements ShareHolderManService {

    @Autowired
    ShareHolderManRepository shareHolderManRepo;

//    @Autowired
//    private MessageSource msg;
    
//    @Autowired 
//    private ProcessService processService;
//    
//    @Autowired
//    private ProcessRepository processRepository;
//    
//    @Autowired
//    private JBPMService jbpmService;
//    
//    @Autowired
//    private HistoryApproveService historyApproveService;

//    private static final String STATUS = "status";
//    private static final String OWNED_SHARE_AMOUNT = "ownedShareAmount";
//    private static final String DIVIDEND_AMOUNT = "dividendAmount";
//    private static final String DATE_OF_ISSUE = "dateOfIssue";
//    private static final String PLACE_OF_ISSUE = "placeOfIssue";
//    private static final String ID_NUMBER = "idNumber";
//    private static final String ADDRESS = "address";
//    private static final String NAME = "name";
//    private static final String CODE = "code";

//    private static final String[] searchFieldDispNames = { "searchfield.disp.code", "searchfield.disp.name",
//            "searchfield.disp.address", "searchfield.disp.idnumber", "searchfield.disp.idnumber.placeofissue",
//            "searchfield.disp.idnumber.dateofissue","searchfield.disp.dividendamount","searchfield.disp.ownedshareamount", 
//            "searchfield.disp.status" };
//    private static final String[] searchFieldIds = { CODE, NAME, ADDRESS, ID_NUMBER, PLACE_OF_ISSUE, DATE_OF_ISSUE,
//            DIVIDEND_AMOUNT, OWNED_SHARE_AMOUNT, STATUS };

    /**
     * Return all of shareholders
     *
     * @param code
     *            substring of shareholder code
     * @param name
     *            substring of shareholder name
     * @param idNumber
     *            substring of human identifier
     * @param status
     *            shareholder status
     * @return shareholder list
     * @see Image
     */
    public PageWrapper<ShareholderDto> getAllActive(int page, int sizeOfPage) {
        PageWrapper<ShareholderDto> pageWrapper = new PageWrapper<ShareholderDto>(page, sizeOfPage);

        List<ShareHolder> shareHolders = new ArrayList<>();
        int count = shareHolderManRepo.findActiveItemCount();

        if (count > 0) {
            int itemOffset = Utility.calculateOffsetSQL(page, sizeOfPage);
            shareHolders = shareHolderManRepo.findAllActive(itemOffset, sizeOfPage);
        }
        List<ShareholderDto> dtoList = new ArrayList<ShareholderDto>();
        for (ShareHolder entity : shareHolders) {
            ShareholderDto dto = new ShareholderDto(entity);
            dtoList.add(dto);
        }
        pageWrapper.setDataAndCount(dtoList, count);
        return pageWrapper;
    }

//    @Override
//    public List<SearchKeyDto> genSearchKeyList(Locale locale) {
//        List<SearchKeyDto> searchKeys = SearchUtil.genSearchKeyList(searchFieldIds, searchFieldDispNames, locale, msg);
//        return searchKeys;
//    }

//    private ShareHolderSearchDto genSearchCondition(CommonSearchDto searchDto) {
//        String searchValue = searchDto.getSearchValue() == null?null:searchDto.getSearchValue().replace(" ", "");
//        ShareHolderSearchDto searchCondition = new ShareHolderSearchDto();
//        if (searchDto.getSearchKeyIds() == null || searchDto.getSearchKeyIds().isEmpty()) {
//            searchCondition.setCode(searchValue);
//            searchCondition.setName(searchValue);
//            searchCondition.setAddress(searchValue);
//            searchCondition.setIdNumber(searchValue);
//            searchCondition.setPlaceOfIssue(searchValue);
//            searchCondition.setDateOfIssue(searchValue);
//            try{
//                NumberFormat numberFormat = NumberFormat.getInstance();
//                Double ownedShareAmount = numberFormat.parse(searchValue).doubleValue();
//                searchCondition.setOwnedSharesAmount(ownedShareAmount);
//            }catch(Exception ex){
//                
//            }
//            
//            try{
//                NumberFormat numberFormat = NumberFormat.getInstance();
//                Double dividendAmount = numberFormat.parse(searchValue).doubleValue();
//                searchCondition.setDividendAmount(dividendAmount);
//            }catch(Exception ex){
//                
//            }
//            
//            searchCondition.setStatus(searchValue);
//        } else {
//            List<String> searchKeyIds = searchDto.getSearchKeyIds();
//            for (String searchKeyId : searchKeyIds) {
//                if (searchKeyId.equals(CODE)) {
//                    searchCondition.setCode(searchValue);
//                }
//                if (searchKeyId.equals(NAME)) {
//                    searchCondition.setName(searchValue);
//                }
//                if (searchKeyId.equals(ADDRESS)) {
//                    searchCondition.setAddress(searchValue);
//                }
//                if (searchKeyId.equals(ID_NUMBER)) {
//                    searchCondition.setIdNumber(searchValue);
//                }
//                if (searchKeyId.equals(DATE_OF_ISSUE)) {
//                    searchCondition.setDateOfIssue(searchValue);
//                }
//                if (searchKeyId.equals(PLACE_OF_ISSUE)) {
//                    searchCondition.setPlaceOfIssue(searchValue);
//                }
//                if (searchKeyId.equals(OWNED_SHARE_AMOUNT)) {
//                    try{
//                        NumberFormat numberFormat = NumberFormat.getInstance();
//                        Double ownedShareAmount = numberFormat.parse(searchValue).doubleValue();
//                        searchCondition.setOwnedSharesAmount(ownedShareAmount);
//                    }catch(Exception ex){
//                        
//                    }
//                }
//                if (searchKeyId.equals(DIVIDEND_AMOUNT)) {
//                    try{
//                        NumberFormat numberFormat = NumberFormat.getInstance();
//                        Double dividendAmount = numberFormat.parse(searchValue).doubleValue();
//                        searchCondition.setDividendAmount(dividendAmount);
//                    }catch(Exception ex){
//                        
//                    }
//                }
//                if (searchKeyId.equals(STATUS)) {
//                    searchCondition.setStatus(searchValue);
//                }
//            }
//        }
//        return searchCondition;
//    }

    /**
     * Return list of shareholders with field values contain respect parameter
     * value
     *
     * @param code
     *            substring of shareholder code
     * @param name
     *            substring of shareholder name
     * @param idNumber
     *            substring of human identifier
     * @param status
     *            shareholder status
     * @return shareholder list
     * @see findAll
     */
//    @Override
//    public PageWrapper<ShareholderDto> getActiveByConditions(int page, int sizeOfPage, CommonSearchDto searchDto) {
//        ShareHolderSearchDto condition = this.genSearchCondition(searchDto);
//        PageWrapper<ShareholderDto> pageWrapper = new PageWrapper<ShareholderDto>(page, sizeOfPage);
//
//        List<ShareHolder> shareHolders = null;
//        int count = shareHolderManRepo.countActiveByConditions(condition);
//
//        if (count > 0) {
//            int itemOffset = Utility.calculateOffsetSQL(page, sizeOfPage);
//            shareHolders = shareHolderManRepo.findActiveByConditions(itemOffset, sizeOfPage, condition);
//        }
//        List<ShareholderDto> dtoList = new ArrayList<ShareholderDto>();
//        if (shareHolders != null) {
//            for (ShareHolder entity : shareHolders) {
//                ShareholderDto dto = new ShareholderDto(entity);
//                dtoList.add(dto);
//            }
//        }
//        pageWrapper.setDataAndCount(dtoList, count);
//        return pageWrapper;
//    }

    /**
     * Return shareholder detail
     *
     * @param code
     *            substring of shareholder code
     * @return shareholder
     * @see
     */
    @Override
    public ShareholderDto getDetailById(Long id) {
        ShareHolder entity = shareHolderManRepo.findDetailById(id);
        ShareholderDto retVal = new ShareholderDto(entity);
        if(retVal.getStatus() == null){
            retVal.setStatus(ShareHolderProcessEnum.CREATE.toString());
        }
        return retVal;
    }

    /**
     * Return update result
     *
     * @param updateModel
     * @return true/false update result
     * @see
     */
//    @Override
//    public ShareholderDto saveNew(ShareholderDto updateModel, ShareHolderProcessEnum status) {
//        ShareHolder entity = updateModel.createEntity();
////        UserProfile userProfile = UserProfileUtils.getUserProfile();
//        entity.setProcessId(processService.getProcessIdByBusinessCode(MasterType.SHAREHOLDER.toString()));
//        entity.setOwnerBranchId(UserProfileUtils.getBranchId());
//        entity.setOwnerId(UserProfileUtils.getAccountId());
//        entity.setOwnerSectionId(UserProfileUtils.getDepartmentId());
//        entity.setStatus(status.toString());
//        entity.setCreateDate(new Date());
//        entity.setCreateBy(UserProfileUtils.getUserNameLogin());
//        if(ShareHolderProcessEnum.CREATE.equals(status)){
//            this.startSaveDraftJbpmProcess(entity, entity.getProcessId());
//        }else{
//            this.startSubmitJbpmProcess(entity, entity.getProcessId());
//        }
//        return new ShareholderDto(shareHolderManRepo.save(entity));
//    }

    
    
//    @SuppressWarnings("resource")
//    @Override
//	public String importExcel(MultipartHttpServletRequest multipartHttpServletRequest) {
//		try {
//	    	Iterator<String> itr = multipartHttpServletRequest.getFileNames();
//			MultipartFile multipartFile = multipartHttpServletRequest.getFile(itr.next());
//			if (multipartFile == null) {
//				return "false";
//			}
//			String contentType = multipartFile.getContentType();
//			Workbook workbook = null;
//			if (contentType.equals("application/vnd.ms-excel")) {
//				workbook = new HSSFWorkbook(multipartFile.getInputStream());
//			} else {
//				workbook = new XSSFWorkbook(multipartFile.getInputStream());
//			}
//			//
//			Sheet sheet = workbook.getSheetAt(0);
//			Iterator<Row> iterator = sheet.iterator();
//			//get row header
//			Row rowHeader = iterator.next();
//			iterator.next();
//			iterator.next();
//    		List<ItemColsExcelDto> cols = new ArrayList<ItemColsExcelDto>();
//    		
//    		List<ShareholderImportDto> listDataImport = new ArrayList<ShareholderImportDto>();
//    		String compensationType = null;
//
//		    ConstantImport.setListColumnExcel(ShareholderImportEnum.class, cols);		
//		    //get data file excel
//		    ConstantImport<ShareholderImportDto> compenRegistrationExcel = new ConstantImport<ShareholderImportDto>();
//		    compenRegistrationExcel.setDataFileExcel(iterator, cols, rowHeader, ShareholderImportDto.class);
//		    listDataImport  = compenRegistrationExcel.getData();
//		
//    		int j=0;
//    		try {
//				for(int i = 0; i < listDataImport.size(); i++) {
//        		    for(j = i+1; j < listDataImport.size(); j++) {
//                        if(listDataImport.get(i).getCode().equals(listDataImport.get(j).getCode()))
//                        {
//                            return "fail"+(j+1);
//                        }
//                    }
//        		}
//    		} catch(Exception e1) {
//    		    return "fail"+j;
//    		}
//    		String rowExcep = null;
//            rowExcep = this.importExcelData(listDataImport, compensationType);
//            if(rowExcep != null) {
//                return "fail: "+rowExcep;
//            }
//	    }catch(Exception e){
//	        return "fail";
//	    }
//    	return "success";
//	}
    
//    private String validateImportDataList(List<ShareholderImportDto> listDataImport){
//    	return null;
//    }
    
//    @Transactional
//    public String importExcelData(List<ShareholderImportDto> listDataImport, String compensationType) {
////    	String validateError = validateImportDataList(listDataImport);
////    	if(validateError != null){
////    		return validateError;
////    	}
//    	List<ShareHolder> listShareholderCode = shareHolderManRepo.findAllActiveNonPaging();
//    	Map<String,ShareHolder> mapBanks = new HashMap<String,ShareHolder>();
//    	for(ShareHolder shareholder : listShareholderCode){
//    		mapBanks.put(shareholder.getCode().trim(), shareholder);
//    	}
//    	// get list insert , update
//    	String userNameLogin = UserProfileUtils.getUserNameLogin();
//    	long sortIndex = 0;
//    	try{
//        	for(ShareholderImportDto shareholderImportDto : listDataImport){
//        		if(mapBanks.get(shareholderImportDto.getCode().trim()) == null){
//        		    ShareHolder shareholder = new ShareHolder();
//        		    shareholder.setIdNumber(shareholderImportDto.getIdNumber());
//                    shareholder.setCode(shareholderImportDto.getCode());
//                    shareholder.setName(shareholderImportDto.getName());
//                    shareholder.setAddress(shareholderImportDto.getAddress());
//                    shareholder.setDateOfIssue(shareholderImportDto.getDateOfIssue());
//                    shareholder.setPlaceOfIssue(shareholderImportDto.getPlaceOfIssue());
//                    shareholder.setOwnedSharesAmount(shareholderImportDto.getOwnedSharesAmount());
//                    shareholder.setDividendAmount(shareholderImportDto.getDividendAmount());
//                    shareholder.setCreateDate(shareholderImportDto.getUpdateDate());
//                    shareholder.setCreateBy(userNameLogin);
//                    shareholder.setSort(sortIndex);
//                    importItemJbpmProcessing(shareholder);
//                    
//                    shareHolderManRepo.save(shareholder);
//                    sortIndex ++;
//        		}
//        		else{
//        		    // update
//        			 ShareHolder shareholder = mapBanks.get(shareholderImportDto.getCode());
//        			 shareholder.setIdNumber(shareholderImportDto.getIdNumber());
//                     shareholder.setCode(shareholderImportDto.getCode());
//                     shareholder.setName(shareholderImportDto.getName());
//                     shareholder.setAddress(shareholderImportDto.getAddress());
//                     shareholder.setDateOfIssue(shareholderImportDto.getDateOfIssue());
//                     shareholder.setPlaceOfIssue(shareholderImportDto.getPlaceOfIssue());
//                     shareholder.setOwnedSharesAmount(shareholderImportDto.getOwnedSharesAmount());
//                     shareholder.setDividendAmount(shareholderImportDto.getDividendAmount());
//                     shareholder.setUpdateDate(shareholderImportDto.getUpdateDate());
//                     shareholder.setUpdateBy(userNameLogin);
//                     shareholder.setSort(sortIndex);
//                     shareHolderManRepo.save(shareholder);
//                     sortIndex ++;
//        		}
//        	}
//        	
//    	}catch(Exception e){
//    		return e.getMessage();
//    	}
//    	return null;
//    }

//	private void importItemJbpmProcessing(ShareHolder shareholder) {
////		UserProfile userProfile = UserProfileUtils.getUserProfile();
//		shareholder.setProcessId(processService.getProcessIdByBusinessCode(MasterType.SHAREHOLDER.toString()));
//		shareholder.setOwnerBranchId(UserProfileUtils.getBranchId());
//		shareholder.setOwnerId(UserProfileUtils.getAccountId());
//		shareholder.setOwnerSectionId(UserProfileUtils.getDepartmentId());
//		shareholder.setStatus(ShareHolderProcessEnum.CREATE.toString());
//		
//		this.startSaveDraftJbpmProcess(shareholder, shareholder.getProcessId());
//	}
    /**
     * Return update result
     *
     * @param updateModel
     * @return true/false update result
     * @see
     */
//    @Override
//    public ShareholderDto saveUpdate(ShareholderDto updateModel, ShareHolderProcessEnum status) {
//        Long sHolderId = updateModel.getId();
//        ShareHolder entity = shareHolderManRepo.findDetailById(sHolderId);
//        if (entity == null) {
//            throw new BusinessException("illegal data");
//        }
//        entity.copyDtoProperties(updateModel);
//        // update existing record
////        UserProfile userProfile = UserProfileUtils.getUserProfile();
//        entity.setProcessId(processService.getProcessIdByBusinessCode(MasterType.SHAREHOLDER.toString()));
//        entity.setStatus(status.toString());
//        entity.setUpdateDate(new Date());
//        entity.setUpdateBy(UserProfileUtils.getUserNameLogin());
//
//        
//        if(ShareHolderProcessEnum.CREATE.equals(status)){
//            this.startSaveDraftJbpmProcess(entity, entity.getProcessId());
//        }else{
//            this.startSubmitJbpmProcess(entity, entity.getProcessId());
//        }
//        return new ShareholderDto(shareHolderManRepo.save(entity));
//    }

    /**
     * Count item by shareholder id
     *
     * @param id
     *            long
     * @return item count
     * @see
     */
//    @Override
//    public int countById(Long id) {
//        int count = shareHolderManRepo.countById(id);
//        return count;
//    }

    /**
     * delete share holder => set value for deleteBy and deleteDate field
     *
     * @param id
     *            long
     * @return
     * @see
     */
    @Override
    public void delete(Long id) {
        int itemCount = shareHolderManRepo.countById(id);
        if (itemCount <= 0) {
            throw new BusinessException("data is not existed");
        }
//        UserProfile userProfile = UserProfileUtils.getUserProfile();
        shareHolderManRepo.updateDeleteFields(id, UserProfileUtils.getUserNameLogin(), new Date());
//        ShareHolder entity = shareHolderManRepo.findOne(id);
//        this.abortJbpmProcess(entity.getProcessId(), entity.getProcessIntanceId());
    }

    /**
     * count field number with existed code
     *
     * @param code
     *            String
     * @return
     * @see
     */
    @Override
    public int countByCode(String code) {
        int itemCount = shareHolderManRepo.countByCode(code);
        return itemCount;
    }

//    @Override
//    @Transactional
//    public ShareholderDto approve(ShareholderDto shareholderDto) {
//        ShareholderDto updateResult = saveUpdateDocumentStatus(shareholderDto,  ShareHolderProcessEnum.APPROVAL);
//        this.updateProcess(updateResult, ShareHolderProcessEnum.APPROVAL);
//        return updateResult;
//    }

//    @Override
//    @Transactional
//    public ShareholderDto reject(ShareholderDto shareholderDto) {
//        this.updateProcess(shareholderDto, ShareHolderProcessEnum.REJECT);
//        ShareholderDto updateResult = saveUpdateDocumentStatus(shareholderDto,  ShareHolderProcessEnum.REJECT);
//        return updateResult;
//    }

//    @Override
//    @Transactional
//    public ShareholderDto submit(ShareholderDto shareholderDto) {
//        ShareHolder entity = shareHolderManRepo.findOne(shareholderDto.getId());
//        this.startSubmitJbpmProcess(entity, entity.getProcessId());
//        ShareholderDto retVal = saveUpdateDocumentStatus(shareholderDto,  ShareHolderProcessEnum.SUBMIT);
//        return retVal;
//    }

    /**
     * @param shareholderDto
     * @param status
     * @return
     */
//    private ShareholderDto saveUpdateDocumentStatus(ShareholderDto shareholderDto,
//            ShareHolderProcessEnum status) {
//        ShareHolder entity = shareHolderManRepo.findOne(shareholderDto.getId());
////        UserProfile userProfile = UserProfileUtils.getUserProfile();
//        entity.setUpdateDate(new Date());
//        entity.setUpdateBy(UserProfileUtils.getUserNameLogin());
//        entity.setStatus(status.toString());
//        entity = shareHolderManRepo.save(entity);
//       
//        ShareholderDto resultDto = new ShareholderDto(entity);
//         
//        //set ReferenceId
//        resultDto.setReferenceId(entity.getId());
//        //update history approve    
//        resultDto.setProcessId(entity.getProcessId());
//        HistoryApproveDto historyApproveDto = new HistoryApproveDto();
//        
//        historyApproveDto.setComment(shareholderDto.getComment());
//        historyApproveDto.setProcessId(entity.getProcessId());
//        historyApproveDto.setReferenceId(entity.getId());
//        historyApproveDto.setReferenceType(ConstantHistoryApprove.APPROVE_SHAREHOLDER);
//        historyApproveDto.setStatusCode(status.toString());
//        historyApproveDto.setApprover(UserProfileUtils.getUserNameLogin());
//  
//        historyApproveService.addHistoryApprove(historyApproveDto, ShareHolderProcessEnum.BUSINESS_CODE.toString());
//        return resultDto;
//    }
    
    /**
     * Create new DocumentDto with empty data (for add mode)
     * @return DocumentDto
     */
//    @Override
//    public ShareholderDto initShareholderDto(){
//        ShareholderDto detailDto = new ShareholderDto();
//        Long maxSort = shareHolderManRepo.findMaxSort();
//        if(maxSort == null){
//        	maxSort = 0L;
//        }
//        Long newSortOrder = maxSort + 1;
//        detailDto.setSortOrder(newSortOrder);
//        detailDto.setStatus(ShareHolderProcessEnum.CREATE.toString());
//        detailDto.setProcessId(processService.getProcessIdByBusinessCode(MasterType.SHAREHOLDER.toString()));
//        
//        return detailDto;
//    }
    
//    private Long startSaveDraftJbpmProcess(ShareHolder entity, Long processId){
//        vn.com.unit.jcanary.entity.Process process = processRepository.findOne(processId);
//        Long processInstanceId = null;
//        if (UserProfileUtils.hasRole(CmsRoleConstant.ROLE_USER.concat(ConstantCore.COLON_EDIT))
//                || UserProfileUtils.hasRole(CmsRoleConstant.ROLE_USER.concat(ConstantCore.COLON_DISP))) {
//                // Check id and status to update jBPM
//                // start jBPM Process and action 'save'
//                Hashtable<String, Object> params = new Hashtable<String, Object>();
//                params.put(CommonConstant.PARAM_TYPE, CommonConstant.PROCESS_INTRODUCTION);
//                params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_SAVE);
//                processInstanceId = jbpmService.startJBPMProcess(
//                        process.getDeploymentId(), process.getProcessDefinitionId(), 
//                        CmsRoleConstant.ROLE_USER, CmsRoleConstant.ROLE_USER, params);
//                entity.setProcessIntanceId(processInstanceId);
//        }
//        return processInstanceId;
//    }
    
//    private Long startSubmitJbpmProcess(ShareHolder entity, Long processId){
//        vn.com.unit.jcanary.entity.Process process = processRepository.findOne(processId);
//        Long processInstanceId = null;
//        if (process != null) {
//            if (UserProfileUtils.hasRole(CmsRoleConstant.ROLE_USER.concat(ConstantCore.COLON_EDIT))
//                    || UserProfileUtils.hasRole(CmsRoleConstant.ROLE_USER.concat(ConstantCore.COLON_DISP))) {
//                    if (null == entity.getId()) {
//                        Hashtable<String, Object> params = new Hashtable<String, Object>();
//                        params.put(CommonConstant.PARAM_TYPE, CommonConstant.PROCESS_INTRODUCTION);
//                        params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_SAVE);
//                        processInstanceId = jbpmService.startJBPMProcess(
//                                process.getDeploymentId(), process.getProcessDefinitionId(), 
//                                CmsRoleConstant.ROLE_USER, CmsRoleConstant.ROLE_USER, params);
//                        entity.setProcessIntanceId(processInstanceId);
//                    }
//                    // action 'submit'
//                    Hashtable<String, Object> params = new Hashtable<String, Object>();
//                    params.put(CommonConstant.PARAM_TYPE, CommonConstant.PROCESS_INTRODUCTION);
//                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_SUBMIT);
//                    
//                    List<String> listCheck = new ArrayList<String>();
//                    listCheck.add(CommonConstant.STATUS_SAVED);
//                    listCheck.add(CommonConstant.STATUS_REJECTED);
//                    processInstanceId = entity.getProcessIntanceId();
//                    jbpmService.updateJBPMStatus(process.getDeploymentId(), entity.getProcessIntanceId(), 
//                    		CmsRoleConstant.ROLE_USER, CmsRoleConstant.ROLE_USER, params, 
//                            CommonConstant.PARAM_STATUS, listCheck);
//            }
//        }
//        return processInstanceId;
//    }
    
    /**
     * updateProcess
     *
     * @param id
     * @param processEnum
     * @author hand
     */
//    public void updateProcess(ShareholderDto shareholderDto, ShareHolderProcessEnum processEnum) {
//        ShareHolder document = shareHolderManRepo.findOne(shareholderDto.getId());
//        /* --- Start jBPM process -------------------------------- */
//        vn.com.unit.jcanary.entity.Process process = processRepository.findOne(document.getProcessId());
//        if (process != null) {
//            if (UserProfileUtils.hasRole(CmsRoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_EDIT))
//                    || UserProfileUtils.hasRole(CmsRoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_DISP))) {
//                Hashtable<String, Object> params = new Hashtable<String, Object>();
//                if (ShareHolderProcessEnum.APPROVAL.toString().equals(processEnum.toString())) {
//                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_APPROVE);
//                } else {
//                    params.put(CommonConstant.PARAM_ACTION, CommonConstant.ACTION_VALUE_REJECT);
//                }
//                
//                List<String> listCheck = new ArrayList<String>();
//                listCheck.add(CommonConstant.STATUS_SUBMITTED);
//                
//                jbpmService.updateJBPMStatus(process.getDeploymentId(), document.getProcessIntanceId(), 
//                		CmsRoleConstant.ROLE_MANAGER, CmsRoleConstant.ROLE_MANAGER, params, 
//                        CommonConstant.PARAM_STATUS, listCheck);
//            }
//        }
//        /* --- End jBPM process ---------------------------------- */
//        
//        if (ShareHolderProcessEnum.APPROVAL.toString().equals(processEnum.toString())) {
//            document.setStatus(DocumentProcessEnum.APPROVAL.toString());
//        } else {
//            document.setStatus(DocumentProcessEnum.REJECT.toString());
//        }
//
//        shareHolderManRepo.save(document);
//    }
    
//    private void abortJbpmProcess(Long processId, Long processInstanceId){
//        vn.com.unit.jcanary.entity.Process process = processRepository.findOne(processId);
//        if (process != null) {
//            if (UserProfileUtils.hasRole(CmsRoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_EDIT))
//                    || UserProfileUtils.hasRole(CmsRoleConstant.ROLE_MANAGER.concat(ConstantCore.COLON_DISP))
//                    || UserProfileUtils.hasRole(CmsRoleConstant.ROLE_USER.concat(ConstantCore.COLON_EDIT))
//                    || UserProfileUtils.hasRole(CmsRoleConstant.ROLE_USER.concat(ConstantCore.COLON_DISP))) {
//                jbpmService.abortProcess(process.getDeploymentId(), CmsRoleConstant.ROLE_MANAGER, CmsRoleConstant.ROLE_MANAGER, 
//                        processInstanceId);
//            }
//        }
//    }

	@Override
	public List<ShareholderDto> getAllActive() {
        List<ShareHolder> shareHolders = null;
        shareHolders = shareHolderManRepo.findAllActiveNonPaging();
        List<ShareholderDto> dtoList = new ArrayList<ShareholderDto>();
        for (ShareHolder entity : shareHolders) {
            ShareholderDto dto = new ShareholderDto(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

	@Override
	public void updateModelsSort(SortPageDto sortPageModel) {
		if(sortPageModel.getSortList() != null){
			for(SortOrderDto sortItem : sortPageModel.getSortList()){
				ShareHolder shareholderEntity = shareHolderManRepo.findOne(sortItem.getObjectId());
				shareholderEntity.setSort(sortItem.getSortValue());
				shareHolderManRepo.save(shareholderEntity);
			}
		}
	}

//	@Override
//	public ShareholderDto getEdit(Long id, Locale locale) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public void getDataProcess(ShareholderDto editDto, Locale locale) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void getDataBusiness(ShareholderDto editDto, Locale locale) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public String getTransactionNo(ShareholderDto editDto) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public ShareholderDto getEdit(Long id, String customerAlias, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}
}
