package vn.com.unit.ep2p.core.ers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErsShowPresentationDto {
    private String typeLink;
    private String title;
    private String linkUrl;
    private String typeOpenPage;
    private String content;
    private Integer orderOnPage;
    private Long repoId;
}
