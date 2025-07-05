package vn.com.unit.cms.admin.all.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import vn.com.unit.cms.admin.all.jcanary.dto.HistoryApproveDto;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;
import vn.com.unit.cms.core.utils.CmsUtils;
import vn.com.unit.ep2p.core.res.dto.DocumentActionReq;
import vn.com.unit.ep2p.dto.JProcessStepDto;

@Getter
@Setter
public class ProductManagementEditDto  extends CmsCommonEditDto {
	 	
		private String productCode;
	    
	    private String productName;
	    
	    private String productType;
	    
	    private Float unitPrice;
	    
	    private Date effectiveDate;
	    
	    private Date expiredDate;
	    
	    private String productImg;
	    
	    private String productPhysicalImg;
}