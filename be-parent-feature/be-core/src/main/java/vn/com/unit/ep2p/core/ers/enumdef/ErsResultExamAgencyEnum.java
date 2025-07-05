/***************************************************************
 * @author vunt
 * @date Apr 28, 2021
 * @project mbal-core
 * @version 1.0 
 * @description 
 ***************************************************************/
package vn.com.unit.ep2p.core.ers.enumdef;

public enum ErsResultExamAgencyEnum {
	NO("0"),
	APPLYFORPOSITION("1"),
	FULLNAME("2"),
	COLUMN1("3"),
	COLUMN2("4"),
	COLUMN3("5"),
	IDNO("6"),
	COLUMN4("7"),
	COLUMN5("8"),
	COLUMN6("9"),
	COLUMN7("10"),
	COLUMN8("11"),
	COLUMN9("12"),
	COLUMN10("13"),
	COLUMN11("14"),
	COLUMN12("15"),
	COLUMN13("16"),
	COLUMN14("17"),
	COLUMN15("18"),
	COLUMN16("19"),
	MBSUCLASSREGISTRATION("20"),
	ORGANIZATIONCLASSPROVINCE("21"),
	REGISTRATIONKIND("22"),
	COLUMN17("23"),
	COLUMN18("24"),
	COLUMN19("25"),
	COLUMN20("26"),
	COLUMN21("27"),
	COLUMN22("28"),
	COLUMN23("29"),
	COLUMN24("30"),
	COLUMN25("31"),
	COLUMN26("32"),
	COLUMN27("33"),
	COLUMN28("34"),
	COLUMN29("35"),
	COLUMN30("36"),
	COLUMN31("37"),
	COLUMN32("38"),
	COLUMN33("39"),
	COLUMN34("40"),
	COLUMN35("41"),
	COLUMN36("42"),
	COLUMN37("43"),
	COLUMN38("44"),
	COLUMN39("45"),
	COLUMN40("46"),
	ATTENDDAYONE("47"),
	ELIGIBLEEXAM("48"),
	CONTEST("49"),
	COLUMN41("50"),
	COLUMN42("51"),
	COLUMN43("52"),
	COLUMN44("53"),
	COLUMN45("54"),
	COMPLETEMBSU("55"),
	COLUMN46("56"),
	COLUMN47("57"),
	COLUMN48("58"),
	COLUMN49("59"),
	COMPLETEMBFS("60"),
	MBFSCLASSREGISTRATION("61"),
	COMPLETETRAINING("62"),
	MESSAGEERROR("63");
	private String value;

    private ErsResultExamAgencyEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
}