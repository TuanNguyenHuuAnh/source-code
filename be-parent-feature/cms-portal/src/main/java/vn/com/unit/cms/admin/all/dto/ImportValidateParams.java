package vn.com.unit.cms.admin.all.dto;

import jp.sf.amateras.mirage.annotation.In;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportValidateParams {
    
    @In
    public String sessionKey;
}
