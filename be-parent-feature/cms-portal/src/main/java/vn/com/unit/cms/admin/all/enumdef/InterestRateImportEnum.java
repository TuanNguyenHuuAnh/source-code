package vn.com.unit.cms.admin.all.enumdef;

public enum InterestRateImportEnum {
    /** currency id */
    currencyId("0"),
    /** term id */
    termId("1"),
    /** city id */
    cityId("2"),
    /** value */
    value("3");

    private String index;

    private InterestRateImportEnum(String index) {
        this.index = index;
    }

    public String toString() {
        return index;
    }

}
