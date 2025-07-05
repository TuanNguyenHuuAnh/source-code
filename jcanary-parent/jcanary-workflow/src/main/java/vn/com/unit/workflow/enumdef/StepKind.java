/*******************************************************************************
 * Class        ：StepKind
 * Created date ：2020/11/24
 * Lasted date  ：2020/11/24
 * Author       ：KhuongTH
 * Change log   ：2020/11/24：01-00 KhuongTH create a new
 ******************************************************************************/
package vn.com.unit.workflow.enumdef;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.com.unit.common.dto.Select2Dto;

/**
 * StepKind
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
@Getter
@AllArgsConstructor
public enum StepKind {

    NORMAL("NORMAL", "process.step.kind.normal")
    , PARALLEL("PARALLEL", "process.step.kind.parallel")
    , PARALLEL_SURVEY("PARALLEL_SURVEY", "process.step.kind.parallel.survey");

    private String value;
    private String text;

    public static List<Select2Dto> getList(){
        List<Select2Dto> resList = new ArrayList<>();
        for (StepKind stepKind : values()) {
            Select2Dto item = new Select2Dto();
            item.setId(stepKind.value);
            item.setText(stepKind.text);
            item.setName(stepKind.text);
            resList.add(item);
        }
        return resList;
    }
}
