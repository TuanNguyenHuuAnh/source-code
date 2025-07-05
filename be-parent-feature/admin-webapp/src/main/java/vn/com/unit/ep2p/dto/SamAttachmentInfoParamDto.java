package vn.com.unit.ep2p.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.ResultSet;

public class SamAttachmentInfoParamDto {
    @ResultSet
    public List<SamAttachmentInfoDto> data;
}
