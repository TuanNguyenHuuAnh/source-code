package vn.com.unit.process.workflow.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.process.workflow.dto.AppProcessDeployDto;
import vn.com.unit.process.workflow.dto.AppProcessDeploySearchDto;
import vn.com.unit.workflow.repository.JpmProcessDeployRepository;

public interface AppProcessDeployRepository extends JpmProcessDeployRepository {
	
	/**
     * count JpmProcessDeploy by searchDto and deleteBy is null
     * 
     * @param searchDto
     * 			type AppProcessSearchDto
     * @return int
     * @author KhuongTH
     */
    int countJpmProcessDeployByCondition(@Param("searchDto") AppProcessDeploySearchDto searchDto);
    
    /**
     * find JpmProcess by searchDto and deleteBy is null
     * 
     * @param startIndex
     * 			type int
     * @param sizeOfPage
     * 			type int
     * @param searchDto
     * 			type AppProcessSearchDto
     * @return List<AppProcessDeployDto>
     * @author KhuongTH
     */
    List<AppProcessDeployDto> findJpmProcessDeployDtoByCondition(@Param("offset") int startIndex,
        @Param("sizeOfPage") int sizeOfPage, @Param("searchDto") AppProcessDeploySearchDto searchDto);
    
    /**
     * Find JpmProcessDeploy by deleteBy is null
     * @param id
     * @return AppProcessDeployDto
     * @author KhuongTH
     */
    AppProcessDeployDto findOneJpmProcessDeployById(@Param("id") Long id);
   
    /**
     * Find JpmProcessDeploy by code and companyId
     * @param id
     * @return AppProcessDeployDto
     * @author KhuongTH
     */  
    AppProcessDeployDto findOneJpmProcessDeployByCodeAndCompanyId(@Param("code") String code, @Param("companyId") Long companyId);
    
    /**
     * getJpmProcessByBusinessId
     * @param businessId
     * 			type Long
     * @param processId
     * 			type Long
     * @return AppProcessDeployDto
     * @author KhuongTH
     */
    AppProcessDeployDto getJpmProcessByBusinessId(@Param("businessId")Long businessId, @Param("procesId")Long processId);
    
    /**
     * findJpmProcessDtoListByBusinessId
     * @param businessId
     * 			type Long
     * @return List<AppProcessDeployDto>
     * @author KhuongTH
     */
    List<AppProcessDeployDto> findJpmProcessDtoListByBusinessId(@Param("businessId")Long businessId, @Param("lang")String lang);
    
    /**
     * findJpmProcessDeployByCompanyIdAndBusinessIdAndMaxEffectiveDate
	 *
     * @param companyId
     * 			type Long
     * @param businessId
     * 			type Long
     * @param sysDate
     * 			type Date
     * @return AppProcessDeployDto
     * @author KhoaNA
     */
    AppProcessDeployDto findJpmProcessDeployByCompanyIdAndBusinessIdAndMaxEffectiveDate(@Param("companyId") Long companyId, @Param("businessId") Long businessId, @Param("sysDate") Date sysDate);
    
	/**
     * findJpmProcessDeployListByCompanyIdAndFormId
     * @param keySearch
     * @param formId
     * @param isPaging
     * @return
     * @author TaiTT
     */
	public List<AppProcessDeployDto> findJpmProcessDeployListByCompanyIdAndFormId(@Param("keySearch") String keySearch, @Param("formId") Long formId,
			@Param("isPaging") boolean isPaging, @Param("lang") String lang);
	
	/**
	 * 
	 * deleteJpmProcessDeployById
	 * @param id
	 * @param sysDate
	 * @param user
	 * @return
	 * @author taitt
	 */
	@Modifying
	public int deleteJpmProcessDeployById(@Param("id")Long id, @Param("sysDate")Date sysDate,@Param("user")String user);
	
	/**
	 * 
	 * countCheckDeleteJpmProcessDeployById
	 * @param id
	 * @return
	 * @author taitt
	 */
	public int countCheckDeleteJpmProcessDeployById(@Param("id")Long id);
	
	/**
	 * @param companyId
	 * @return
	 */
	List<AppProcessDeployDto> findJpmProcessDtoListByCompanyId(@Param("companyId")Long companyId);
	
/**
 * findJpmProcessDeployDtoListByProcessId
 * 
 * @param processId
 * @return List<AppProcessDeployDto>
 * @author KhuongTH
 */
List<AppProcessDeployDto> findJpmProcessDeployDtoListByProcessId(@Param("processId") Long processId);

    List<AppProcessDeployDto> findJpmProcessDeployDtoListByProcessIdSQL(@Param("processId") Long processId);
}
