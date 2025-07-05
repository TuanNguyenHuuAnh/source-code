package vn.com.unit.cms.core.module.categorizeCustomer.dto;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CommonSearchWithPagingDto;


/**
 * @author  lmi.quan
 * SR16451 - Enhance Phrase 3 Tinh nang phan loai khach hang 
 * @createdDate 07/06/2024 
 * 
 * 			
 */
@Getter
@Setter
public class CategorizeCustomerSearchDto extends CommonSearchWithPagingDto {
    private String agentCode;
    private String proposalNo;
    private String poName;
    private String partnerCode;
    private String status;

}
