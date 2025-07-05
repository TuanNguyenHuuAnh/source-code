package vn.com.unit.core.req;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.core.dto.AbstractDocumentDto;

@Getter
@Setter
public abstract class DocumentReq extends AbstractDocumentDto {

    private String formName;
}
