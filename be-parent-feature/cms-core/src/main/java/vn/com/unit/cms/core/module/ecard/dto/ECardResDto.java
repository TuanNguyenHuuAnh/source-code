/*******************************************************************************
 * Class        ：ECardEditDto
 * Created date ：2017/04/20
 * Lasted date  ：2017/04/20
 * Author       ：TaiTM
 * Change log   ：2017/04/20：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.ecard.dto;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.cms.core.dto.CmsCommonEditDto;

/**
* @author: TriNT
* @since: 29/09/2021 2:05 CH
* @description:  class ECardResDto
* @update:
*
* */
@Getter
@Setter
public class ECardResDto {
    private String id;
    private String urlImage;
    private String urlPdf;
    private String sort;
    private String name;
}
