package vn.com.unit.cms.admin.all.enumdef;

/**
 * CompensationRegistrationImportEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author thuydtn
 */
public enum ShareholderImportEnum {
	
	code("0"),
	name("1"),
	address("2"),
	idNumber("3"),
	dateOfIssue("4"),
	placeOfIssue("5"),
	ownedSharesAmount("6"),
	dividendAmount("7"),
	updateDate("8");
	
	private String value;

    private ShareholderImportEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
