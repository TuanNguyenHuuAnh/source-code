/*******************************************************************************
 * Class        NetworkList
 * Created date ：2020/07/27
 * Lasted date  ：2020/07/27
 * Author       ：tantm
 * Change log   ：2020/07/27：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.model;

import com.google.api.client.util.Key;

/**
 * NetworkList.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class NetworkList {

    /** The list. */
    @Key
    public List<NetworkEntry> list;
}
