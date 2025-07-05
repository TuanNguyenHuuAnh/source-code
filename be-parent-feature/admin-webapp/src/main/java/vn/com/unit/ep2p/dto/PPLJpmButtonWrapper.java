/*******************************************************************************
 * Class        JpmButtonWrapper
 * Created date 2019/04/12
 * Lasted date  2019/04/12
 * Author       KhoaNA
 * Change log   2019/04/12 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * JpmButtonWrapper
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class PPLJpmButtonWrapper<T> {

    private boolean isSave;
    
    private boolean isSaveEform;
    
    private boolean isReference;
    
    private String stepCode;
    
    private List<T> data;
    
    public boolean getIsSave() {
        return isSave;
    }

    public void setIsSave(boolean save) {
        isSave = save;
    }

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	public boolean getIsReference() {
		return isReference;
	}

	public void setIsReference(boolean isReference) {
		this.isReference = isReference;
	}
	
	public boolean isEmpty() {
		 boolean isEmptyFlag = true;
		 
		 if( data != null && !data.isEmpty() ) {
			 isEmptyFlag = false;
		 }
		 
		 return isEmptyFlag;
	}
	
	public boolean getIsSaveEform() {
        return isSaveEform;
    }

    public void setIsSaveEform(boolean isSaveEform) {
        this.isSaveEform = isSaveEform;
    }

    public String getStepCode() {
        return stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }
    
}
