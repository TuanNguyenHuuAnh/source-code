package vn.com.unit.ep2p.core.ers.enumdef;


public enum ErsWaitingInterviewListExportEnum {
    NO("0"),
    CANDIDATENAME("1"),
    APPLYFORPOSITION("2"),
    IDNO("3"),
    RECRUITERCODEORIDCARD("4"),
    RECRUITERNAME("5"),
    MANAGERCODEORIDCARD("6"),
    MANAGERNAME("7"),
    MANAGERINDIRECTCODEORIDCARD("8"),
    MANAGERINDIRECTNAME("9"),
    ADCODE("10"),
    ADNAME("11"),
    RDNAME("12"),
    STATUSPROCESS("13"),
    ;
    
    private String value;

    private ErsWaitingInterviewListExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
