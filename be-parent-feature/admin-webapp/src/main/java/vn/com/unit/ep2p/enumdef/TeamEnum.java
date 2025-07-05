/*******************************************************************************
 * Class        AccountTeamEnum
 * Created date 2017/09/12
 * Lasted date  2017/09/12
 * Author       phatvt
 * Change log   2017/09/1201-00 phatvt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.enumdef;

/**
 * AccountTeamEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author phatvt
 */
public enum TeamEnum {

    TEAMCODE("account.team.group.code"),
    TEAMNAME("account.team.group.name")
    ;
    private String value;
    private TeamEnum(String value){
        this.value = value;
    }
    
    public String toString(){
       return value; 
    }
}
