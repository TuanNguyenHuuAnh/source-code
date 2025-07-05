package vn.com.unit.ep2p.enumdef;

public enum CertificateExportEnum {
	NO("0"),
	CERTIFICATENAME("1"),
	PRODUCTCODE("2"),
	CATEGORYNAME("3"),
	DATECERT("4"),;

	private String value;
	
	private CertificateExportEnum(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}
