package vn.com.unit.ep2p.core.ers.enumdef;


public enum ErsDynamicInputConfListExportEnum {
    NO("0"),
    CHANNEL("1"),
    CREATEDBY("9"),
    CREATEDDATE("10");
    
    private String value;

    private ErsDynamicInputConfListExportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
