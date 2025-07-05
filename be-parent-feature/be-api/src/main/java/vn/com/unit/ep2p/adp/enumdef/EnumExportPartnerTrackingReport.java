package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportPartnerTrackingReport {
	partner("0"),
    tp("1"),
    ep("2"),
    total("3"),
    fyp("4"),
    tpRyp("5"),
    epRyp("6"),
    totalRyp("7"),
    ryp("8");

    private String value;

    private EnumExportPartnerTrackingReport(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
