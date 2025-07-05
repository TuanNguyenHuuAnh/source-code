package vn.com.unit.ep2p.core.ers.enumdef;


public enum ApplicantBancasListExportEnum {
    NO("0"),
    APPLYFORPOSITION("1"),
    REGIONNAME("2"),
    AGENTCODE("3"),
    BPCODE("4"),
    CANDIDATENAME("5"),
    DOB("6"),
    IDNO("7"),
    IDDATEOFISSUE("8"),
    IDPLACEOFISSUENAME("9"),
    RECRUITERCODE("10"),
    RECRUITERNAME("11"),
    CLASSCODE("12"),
    EXAMRESULT("13"),
    STATUSPROCESS("14"),
    STATUSFORM("15"),
    AVICADSTATUS("16"),
    CREATEDBY("17"),
    CREATEDDATE("18"),
    ;
    
    private String value;

    private ApplicantBancasListExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
