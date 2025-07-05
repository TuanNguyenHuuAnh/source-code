package vn.com.unit.ep2p.dto;

import java.util.List;

/**
 * Created by quangnd on 8/1/2018.
 */
public class ConstantDisplaySearchDto {

    /** fieldSearch */
    private String fieldSearch;

    /** fieldValues */
    private List<String> fieldValues;

    private String type;

    private String kind;

    private String cat;

    private String catAbbrName;

    private String code;

    private String catOfficialName;

    public String getFieldSearch() {
        return fieldSearch;
    }

    public void setFieldSearch(String fieldSearch) {
        this.fieldSearch = fieldSearch;
    }

    public List<String> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCatOfficialName() {
        return catOfficialName;
    }

    public void setCatOfficialName(String catOfficialName) {
        this.catOfficialName = catOfficialName;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getCatAbbrName() {
        return catAbbrName;
    }

    public void setCatAbbrName(String catAbbrName) {
        this.catAbbrName = catAbbrName;
    }
}
