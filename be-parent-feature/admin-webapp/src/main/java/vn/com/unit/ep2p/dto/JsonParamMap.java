/*******************************************************************************
 * Class        ：JsonParamMap
 * Created date ：2019/11/08
 * Lasted date  ：2019/11/08
 * Author       ：KhuongTH
 * Change log   ：2019/11/08：01-00 KhuongTH create a new
 * 			    :2019/11/30:01-00 Add jsonUser - jsonHistory
 *              :2019/12/02:01-00 Add jsonProcess
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

/**
 * JsonParamMap for OZ
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
public class JsonParamMap {
	
	private String jsonData;
	private String jsonDoc;
	private String jsonUser;
	private String jsonHistory;
	private String jsonProcess;
	/**
	 * @return the jsonData
	 */
	public String getJsonData() {
		return jsonData;
	}
	/**
	 * @param jsonData the jsonData to set
	 */
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	/**
	 * @return the jsonDoc
	 */
	public String getJsonDoc() {
		return jsonDoc;
	}
	/**
	 * @param jsonDoc the jsonDoc to set
	 */
	public void setJsonDoc(String jsonDoc) {
		this.jsonDoc = jsonDoc;
	}
	/**
	 * @return the jsonUser
	 */
	public String getJsonUser() {
		return jsonUser;
	}
	/**
	 * @param jsonUser the jsonUser to set
	 */
	public void setJsonUser(String jsonUser) {
		this.jsonUser = jsonUser;
	}
	/**
	 * @return the jsonHistory
	 */
	public String getJsonHistory() {
		return jsonHistory;
	}
	/**
	 * @param jsonHistory the jsonHistory to set
	 */
	public void setJsonHistory(String jsonHistory) {
		this.jsonHistory = jsonHistory;
	}
    
    public String getJsonProcess() {
        return jsonProcess;
    }
    
    public void setJsonProcess(String jsonProcess) {
        this.jsonProcess = jsonProcess;
    }
}
