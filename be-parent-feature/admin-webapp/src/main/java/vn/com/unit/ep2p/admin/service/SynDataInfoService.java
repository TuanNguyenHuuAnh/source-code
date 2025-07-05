/*******************************************************************************
 * Class        SynDataInfoService
 * Created date 2018/08/22
 * Lasted date  2018/08/22
 * Author       hangnkm
 * Change log   2018/08/2201-00 hangnkm create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

/**
 * SynDataInfoService
 * 
 * @version 01-00
 * @since 01-00
 * @author hangnkm
 */
public interface SynDataInfoService {
    public List<String> getListColumnByTableName(String tableName) throws Exception;
}
