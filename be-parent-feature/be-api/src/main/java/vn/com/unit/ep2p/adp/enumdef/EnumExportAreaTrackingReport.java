package vn.com.unit.ep2p.adp.enumdef;

public enum EnumExportAreaTrackingReport {
	areaName("0"),
	regionalName("1"),
	zoneName("2"),
    tp("3"),
    ep("4"),
    total("5"),
    fyp("6"),
    tpRyp("7"),
    epRyp("8"),
    totalRyp("9"),
    ryp("10");

    private String value;

    private EnumExportAreaTrackingReport(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
    }
