package vn.com.unit.ep2p.core.ers.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassListBancasExportDto {
    private Long no;
    private String candidateName;
    private Integer dayOfBirth;
    private Integer monthOfBirth;
    private Integer yearOfBirth;
    private String gender;
    private String idNo;
    private Date idDateOfIssue;
    private String idPlaceOfIssueName;
    private String mobile;
    private String email;
    private String applyForPosition;
    private String area;
    private String amTeam;
    private String referer;
    private String status;
    private String result;
    private String certificate;
    private Date dateOfCertificate;
    private String note;
}
