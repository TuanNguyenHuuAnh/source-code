/*******************************************************************************
 * Class        RepositorySearchDto
 * Created date 2018/08/08
 * Lasted date  2018/08/08
 * Author       KhoaNA
 * Change log   2018/08/08 01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import lombok.Getter;
import lombok.Setter;
/**
 * RepositorySearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Getter
@Setter
public class RepositorySearchDto extends CommonSearchDto {
    /** fieldValues */
    private String strFieldValues;

    /** code */
    private String code;

    /** name */
    private String name;

    /** physicalPath */
    private String physicalPath;

    /** subFolderRule */
    private String subFolderRule;

    /** url */
    private String url;

}

