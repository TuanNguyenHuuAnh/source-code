package vn.com.unit.ep2p.core.ers.enumdef;


public enum ClassListBancasExportEnum {
    NO("0"),
    CANDIDATENAME("1"),
    DAYOFBIRTH("2"),
    MONTHOFBIRTH("3"),
    YEAROFBIRTH("4"),
    GENDER("5"),
    IDNO("6"),
    IDDATEOFISSUE("7"),
    IDPLACEOFISSUENAME("8"),
    MOBILE("9"),
    EMAIL("10"),
    APPLYFORPOSITION("11"),
    AREA("12"),
    AMTEAM("13"),
    REFERER("14"),
    RESULT("15"),
    CERTIFICATE("16"),
    DATEOFCERTIFICATE("17"),
    NOTE("18"),
    ;
    
    private String value;

    private ClassListBancasExportEnum(String value) {
        this.value = value;
    }

    @Override // fix NoClassDefFoundError
    public String toString() {
        return value;
    }
}
