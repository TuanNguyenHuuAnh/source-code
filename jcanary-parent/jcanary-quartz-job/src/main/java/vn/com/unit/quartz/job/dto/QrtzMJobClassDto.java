/*******************************************************************************
 * Class        ：QrtzMJobClassDto
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.dto;

import lombok.Getter;
import lombok.Setter;


/**
 * <p>
 * QrtzMJobClassDto
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
public class QrtzMJobClassDto {

    /** The id. */
    private Long id;

    /** The job type id. */
    private Long jobTypeId;

    /** The path class. */
    private String pathClass;

    /** The name class. */
    private String nameClass;

    /** The description. */
    private String description;

    /** The company id. */
    private Long companyId;

}
