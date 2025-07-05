package vn.com.unit.common.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConstantDisplayDto {
	private Long id;
    private String type;
    private String kind;
    private String cat;
    private String code;
    private String catOfficialName;
    private String catOfficialNameVi;
    private String catAbbrName;
    private String catAbbrNameVi;
    private Integer displayOrder;
    private Boolean delFlg;

    private Date deletedDate;

    private String deletedBy;
    
    private Integer stt;
    
    private String isDeleted;
}
