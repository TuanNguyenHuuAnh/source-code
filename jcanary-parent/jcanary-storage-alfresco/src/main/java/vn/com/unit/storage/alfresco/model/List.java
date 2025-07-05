/*******************************************************************************
 * Class        List
 * Created date ：2020/07/27
 * Lasted date  ：2020/07/27
 * Author       ：tantm
 * Change log   ：2020/07/27：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.model;

import java.util.ArrayList;

import com.google.api.client.util.Key;

/**
 * List.
 *
 * @version 01-00
 * @param <T>
 *            the generic type
 * @since 01-00
 * @author tantm
 */
public class List<T extends Entry> {

    /** The entries. */
    @Key
    public ArrayList<T> entries;

    /** The pagination. */
    @Key
    public Pagination pagination;
}
