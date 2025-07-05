/*******************************************************************************
 * Class        ：Select2Dto
 * Created date ：2020/12/23
 * Lasted date  ：2020/12/23
 * Author       ：taitt
 * Change log   ：2020/12/23：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.dto;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Select2Dto
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Select2Dto {
    private String id;
    private String text;
    private String note;
    private String name;
    private String clazz;
    private String kind;
    private Long orders;
    private String title;
    public Select2Dto(String id, String text, String name) {
        super();
        this.id = id;
        this.text = text;
        this.name = name;
    }
    public Select2Dto(String id, String text, String name, String kind) {
        super();
        this.id = id;
        this.text = text;
        this.name = name;
        this.kind = kind;
    }
}
