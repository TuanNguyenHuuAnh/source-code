/*******************************************************************************
 * Class        ：AbstractResultUpload
 * Created date ：2020/12/31
 * Lasted date  ：2020/12/31
 * Author       ：taitt
 * Change log   ：2020/12/31：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.storage.dto;

import lombok.Setter;
import lombok.Getter;

/**
 * AbstractResultUpload
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public abstract class AbstractResultUpload {

    private String filePath;
    private Long repositoryId;
}
