/*******************************************************************************
 * Class        ：PageAbleDto
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：taitt
 * Change log   ：2020/12/09：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.core.dto;

import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.Setter;

/**
 * PageAbleDto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
public abstract class PageAbleDto {

    private Pageable pageable;
}
