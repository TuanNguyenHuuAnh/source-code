package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportTrackingDetailReport {
	no("0"),
	partner("1"),
	policyNo("2"),
	policyStatus("3"),
    tp("4"),
    ep("5"),
    premiumYear("6"),
    regionalPartnerName("7"),
    zonePartnerName("8"),
    uoName("9"),
    areaName("10"),
    regionalName("11"),
    zoneName("12");

    private String value;

    private EnumExportTrackingDetailReport(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
