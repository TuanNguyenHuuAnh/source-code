package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import jp.sf.amateras.mirage.annotation.In;
import jp.sf.amateras.mirage.annotation.ResultSet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDocumentParamDto {
	@In
    public String agentCode;
    @ResultSet
    public List<RoleDocumentDto> datas;
}
