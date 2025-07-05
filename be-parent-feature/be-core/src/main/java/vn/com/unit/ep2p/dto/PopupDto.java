package vn.com.unit.ep2p.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PopupDto {
    private Long id;
    private String templateContent;
    private Long companyId;
    private String url;
    private String code;
    private String name;
    private Date createdDate;
    private Long createdId;
    private Date updatedDate;
    private Long updatedId;
    private Date deletedDate;
    private Long deletedId;

}
