package vn.com.unit.ep2p.workflow.enumdef;

/**
 * 
 * @author KhuongTH
 *
 */
public enum JpmProcessDeploySearchEnum {
	PROCESS_CODE("jpm.process.code"),

    PROCESS_NAME("jpm.process.name");

    private String value;

    private JpmProcessDeploySearchEnum(String value){
        this.value = value;
    }

    public String toString (){
        return value;
    }
}
