/*******************************************************************************
 * Class        Pagination
 * Created date ：2020/07/27
 * Lasted date  ：2020/07/27
 * Author       ：tantm
 * Change log   ：2020/07/27：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.model;

import com.google.api.client.util.Key;

/**
 * Pagination.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class Pagination {

    /** The count. */
    @Key
    public int count;

    /** The has more items. */
    @Key
    public boolean hasMoreItems;

    /** The total items. */
    @Key
    public int totalItems;

    /** The skip count. */
    @Key
    public int skipCount;

    /** The max items. */
    @Key
    public int maxItems;
}
