/*******************************************************************************
 * Class        AbstractTracking
 * Created date 2017/02/14
 * Lasted date  2017/02/14
 * Author       TaiTM
 * Change log   2017/02/1401-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.entity;

import jp.sf.amateras.mirage.annotation.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * AbstractProcessTracking
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
@Getter
@Setter
public abstract class AbstractProcessTracking extends AbstractTracking {

    @Column(name = "DOC_ID")
    private Long docId;
}
