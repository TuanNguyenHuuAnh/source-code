package vn.com.unit.cms.core.module.customerManagement.dto;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.Out;
import jp.sf.amateras.mirage.annotation.ResultSet;

import java.util.List;

public class FileSubmittedParamDto {

    @In
    public String docNo;
    @In
    public String agentCode;
    @In
    public Integer page;
    @In
    public Integer size;
    @ResultSet
    public List<FileSubmittedDto> lstData;
    @Out
    public Integer totalRows;
}
