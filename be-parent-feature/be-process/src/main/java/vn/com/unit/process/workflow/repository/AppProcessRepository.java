/*******************************************************************************
 * Class        :JpmProcessRepository
 * Created date :2019/06/17
 * Lasted date  :2019/06/17
 * Author       :KhoaNA
 * Change log   :2019/06/17:01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.process.workflow.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.process.workflow.dto.AppProcessDto;
import vn.com.unit.process.workflow.dto.AppProcessSearchDto;
import vn.com.unit.workflow.repository.JpmProcessRepository;

/**
 * JpmProcessRepository
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface AppProcessRepository extends JpmProcessRepository {

	/**
     * count JpmProcess by searchDto and deleteBy is null
     * 
     * @param searchDto
     * 			type AppProcessSearchDto
     * @return int
     * @author KhoaNA
     */
    int countJpmProcessByCondition(@Param("searchDto") AppProcessSearchDto searchDto);

    /**
     * find JpmProcess by searchDto and deleteBy is null
     * 
     * @param startIndex
     * 			type int
     * @param sizeOfPage
     * 			type int
     * @param searchDto
     * 			type AppProcessSearchDto
     * @return List<AppProcessDto>
     * @author KhoaNA
     */
    List<AppProcessDto> findJpmProcessDtoByCondition(@Param("offset") int startIndex,
        @Param("sizeOfPage") int sizeOfPage, @Param("searchDto") AppProcessSearchDto searchDto);

    /**
     * Find JpmProcess by code, companyId and deleteBy is null
     * @param code
     * 			type String
     * @param companyId
     * 			type Long
     * @return AppProcessDto
     * @author KhoaNA
     */
    AppProcessDto findOneJpmProcessByCodeAndCompanyId(@Param("code") String code,
                                                     @Param("companyId") Long companyId);
    
	/**
     * Find JpmProcess by deleteBy is null
     * @param id
     * @return AppProcessDto
     * @author KhoaNA
     */
    AppProcessDto findOneJpmProcessById(@Param("id") Long id);

    @Modifying
    void saveHistoryForParam(@Param("id") Long id, 
    						 @Param("majorVersion") Long majorVersion, @Param("minorVersion") Long minorVersion, 
    						 @Param("user") String updatedUser, @Param("sysDate") Date sysDate);

    @Modifying
    void saveHistoryForStatus(@Param("id") Long id, 
    						  @Param("majorVersion") Long majorVersion, @Param("minorVersion") Long minorVersion, 
    						  @Param("user") String updatedUser, @Param("sysDate") Date sysDate);

    @Modifying
    void saveHistoryForFunction(@Param("id") Long id, 
    							@Param("majorVersion") Long majorVersion, @Param("minorVersion") Long minorVersion, 
    							@Param("user") String updatedUser, @Param("sysDate") Date sysDate);

    @Modifying
    void saveHistoryForButton(@Param("id") Long id, 
    						  @Param("majorVersion") Long majorVersion, @Param("minorVersion") Long minorVersion, 
    						  @Param("user") String updatedUser, @Param("sysDate") Date sysDate);
    
    @Modifying
    void saveHistoryForStep(@Param("id") Long id, 
    						@Param("majorVersion") Long majorVersion, @Param("minorVersion") Long minorVersion, 
    						@Param("user") String updatedUser, @Param("sysDate") Date sysDate);

    @Modifying
    void deleteParamByProcessId(@Param("id") Long id);

    @Modifying
    void deleteStatusByProcessId(@Param("id") Long id);

    @Modifying
    void deleteFunctionByProcessId(@Param("id") Long id);

    @Modifying
    void deleteButtonByProcessId(@Param("id") Long id);
    
    @Modifying
    void deleteStepByProcessId(@Param("id") Long id);

    @Modifying
    void deleteButtonForStepByProcessId(@Param("id") Long id);

    @Modifying
    void revertHistoryProcess(@Param("id") Long id,
                              @Param("majorVersion") Long majorVersion, @Param("minorVersion") Long minorVersion,
                              @Param("user") String updatedUser, @Param("sysDate") Date sysDate);

    @Modifying
    void revertHistoryParam(@Param("id") Long id, 
    						@Param("majorVersion") Long majorVersion, @Param("minorVersion") Long minorVersion, 
    						@Param("user") String updatedUser, @Param("sysDate") Date sysDate);

    @Modifying
    void revertHistoryStatus(@Param("id") Long id, 
    						 @Param("majorVersion") Long majorVersion, @Param("minorVersion") Long minorVersion, 
    						 @Param("user") String updatedUser, @Param("sysDate") Date sysDate);

    @Modifying
    void revertHistoryButton(@Param("id") Long id, 
    						 @Param("majorVersion") Long majorVersion, @Param("minorVersion") Long minorVersion, 
    						 @Param("user") String updatedUser, @Param("sysDate") Date sysDate);

    @Modifying
    void revertHistoryFunction(@Param("id") Long id, 
    						   @Param("majorVersion") Long majorVersion, @Param("minorVersion") Long minorVersion, 
    						   @Param("user") String updatedUser, @Param("sysDate") Date sysDate);
    
    @Modifying
    void revertHistoryStep(@Param("id") Long id, 
    					   @Param("majorVersion") Long majorVersion, @Param("minorVersion") Long minorVersion, 
    					   @Param("user") String updatedUser, @Param("sysDate") Date sysDate);

    @Modifying
    void revertHistoryButtonForStep(@Param("id") Long id,
                           @Param("majorVersion") Long majorVersion, @Param("minorVersion") Long minorVersion,
                           @Param("user") String updatedUser, @Param("sysDate") Date sysDate);

    /**
     * Find JpmProcess list by businesId
     * @param businessId
     * @return List<AppProcessDto>
     * @author KhoaNA
     */
    List<AppProcessDto> findJpmProcessDtoByBusinessId(@Param("businessId") Long businessId);
    
	/**
     * findByBusinessCode
     * @param search
     * @return
     * @author TaiTT
     */
	Long findBusinessIdByFormId(@Param("formId") Long formId);
	
	AppProcessDto getJpmProcessByBusinessId(@Param("businessId") Long businessId,@Param("processId") Long processId);
	/**
     * findOneJpmProcessByNameAndCompanyId
     * @param name
     * @param companyId
     * @return
     * @author KhuongTH
     */
	AppProcessDto findOneJpmProcessByNameAndCompanyId(@Param("name") String name, @Param("companyId")Long companyId);
}
