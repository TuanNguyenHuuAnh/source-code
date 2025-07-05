package vn.com.unit.ep2p.core.res.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErsCandidateAttachDocumentDto {
    private Long candidateId;
    private String fileString;
    private List<String> fileStrings;
    private String content;
    private String typeDocument;
    private Integer stt;
}
