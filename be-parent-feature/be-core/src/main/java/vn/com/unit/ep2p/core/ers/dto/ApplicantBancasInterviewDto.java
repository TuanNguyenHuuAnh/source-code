package vn.com.unit.ep2p.core.ers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantBancasInterviewDto {
    private String interviewer;
    private String[] id;
    private String name;
    private int type; //1: Region Manager; 2: Area Manager
}
