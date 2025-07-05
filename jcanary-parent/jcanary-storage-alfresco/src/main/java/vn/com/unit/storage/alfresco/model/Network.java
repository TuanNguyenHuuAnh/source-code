/*******************************************************************************
 * Class        Network
 * Created date ：2020/07/27
 * Lasted date  ：2020/07/27
 * Author       ：tantm
 * Change log   ：2020/07/27：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.model;

import com.google.api.client.util.Key;

/**
 * Network.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class Network {

    /** The id. */
    @Key
    public String id;

    /** The home network. */
    @Key
    public boolean homeNetwork;

    // @Key
    // DateTime createdAt;

    /** The paid network. */
    @Key
    public boolean paidNetwork;

    /** The is enabled. */
    @Key
    public boolean isEnabled;

    /** The subscription level. */
    @Key
    public String subscriptionLevel;
}
