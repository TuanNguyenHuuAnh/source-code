/*******************************************************************************
 * Class        ：CompanyListRes
 * Created date ：2020/11/23
 * Lasted date  ：2020/11/23
 * Author       ：taitt
 * Change log   ：2020/11/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.common.api.dto.ResRESTApi;

/**
 * CompanyListRes
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public class CompanyListRes extends ResRESTApi{
    private List<CompanyDetailRes> result;
}
