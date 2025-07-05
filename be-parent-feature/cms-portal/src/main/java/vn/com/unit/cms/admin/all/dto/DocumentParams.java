package vn.com.unit.cms.admin.all.dto;

import jp.sf.amateras.mirage.annotation.In;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentParams {
    
    @In
    public String sessionKey;
}
