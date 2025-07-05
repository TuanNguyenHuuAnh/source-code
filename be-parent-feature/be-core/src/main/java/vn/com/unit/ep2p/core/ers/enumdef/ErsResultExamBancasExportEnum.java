/***************************************************************
 * @author vunt
 * @date Apr 28, 2021
 * @project mbal-core
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.ers.enumdef;

public enum ErsResultExamBancasExportEnum {
	NO("0"),
	FULLNAME("1"),
	DOB("2"),
	MOB("3"),
	YOB("4"),
	GENDER("5"),
	IDNUMBER("6"),
	IDDATE("7"),
	IDPLACE("8"),
	PHONENUMBER("9"),
	PERSONALEMAIL("10"),
	POSITION("11"),
	AREA("12"),
	AMTEAM("13"),
	REFERER("14"),
	RESULT("15"),
	CERTIFICATENO("16"),
	DATEOFCERTIFICATE("17"),
	NOTE("18"),
	MESSAGEERROR("19");

	private String value;
	
	private ErsResultExamBancasExportEnum(String value) {
	        this.value = value;
	    }
	
	public String toString() {
	return value;
	}
}
