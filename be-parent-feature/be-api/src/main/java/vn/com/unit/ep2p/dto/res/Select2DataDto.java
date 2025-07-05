package vn.com.unit.ep2p.dto.res;

import java.util.ArrayList;
import java.util.List;


public class Select2DataDto<T> {

    private int totalCount;

    private List<T> dataList;

    public Select2DataDto() {
        dataList = new ArrayList<>();
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
