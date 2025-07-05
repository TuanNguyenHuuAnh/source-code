package vn.com.unit.ep2p.admin.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConstantDisplayDto {

    private Long id;
    private String name;
    private Long constantCode;
    private String type;
//    private String kind;
    private String cat;
    private String code;
    private String catOfficialName;
    private String catOfficialNameVi;
    private String catAbbrName;
    private String catAbbrNameVi;
    private Integer displayOrder;
    private Boolean delFlg;
    private Date deletedDate;
    private Long deletedId;
    private List<ConstantDisplayDto> datas;
    private int strDelFlg;
    public ConstantDisplayDto(long id, String cat, String code) {
        this.id = id;
        this.cat = cat;
        this.code = code;
    }
    public ConstantDisplayDto() {
        super();
        // TODO Auto-generated constructor stub
    }

}
