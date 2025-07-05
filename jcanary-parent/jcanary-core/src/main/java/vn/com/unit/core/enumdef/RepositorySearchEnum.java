/*******************************************************************************
 * Class        RepositorySearchEnum
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/08 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.enumdef;

/**
 * RepositorySearchEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public enum RepositorySearchEnum {
    //code
    CODE("repository.code"),
	//name
    NAME("repository.name"),
    //physical path
    PHYSICAL_PATH("repository.physical.path"),
    //sub folder rule
    SUB_FOLDER_RULE("repository.sub.folder.rule"),
    ;
    
    private String value;
    
    private RepositorySearchEnum(String value){
        this.value = value;        
    }
    
    public String toString(){
        return value;
    }
}
