package vn.com.unit.cms.core.module.customerManagement.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import vn.com.unit.cms.core.module.customer.dto.ProductInformationDto;

public class SubmittedDetailParam {
    @In
    public String policyNo;
    @ResultSet
    public List<ProductInformationDto> datas;
}
