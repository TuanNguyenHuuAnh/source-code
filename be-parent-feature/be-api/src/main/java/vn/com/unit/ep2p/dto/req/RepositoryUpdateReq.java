/*******************************************************************************
 * Class        ：RepositoryUpdateReq
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vn.com.unit.ep2p.core.req.dto.RepositoryInfoReq;

/**
 * RepositoryUpdateReq
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
@Getter
@Setter
public class RepositoryUpdateReq extends RepositoryInfoReq {

    @ApiModelProperty(notes = "Id of repository on system", example = "1", required = true, position = 0)
    private Long repositoryId;
}
