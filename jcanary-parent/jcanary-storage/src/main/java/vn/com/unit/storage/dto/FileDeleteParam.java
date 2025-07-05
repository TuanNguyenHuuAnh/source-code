/*******************************************************************************
 * Class        ：FileDeleteParam
 * Created date ：2020/07/21
 * Lasted date  ：2020/07/21
 * Author       ：tantm
 * Change log   ：2020/07/21：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * FileDeleteParam
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class FileDeleteParam {

    private Long repositoryId;
    private String filePath;
    private String docId;
    private boolean deleteAllVersion;

}
