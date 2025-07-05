/*******************************************************************************
 * Class        ：SlaReceiverService
 * Created date ：2021/01/13
 * Lasted date  ：2021/01/13
 * Author       ：TrieuVD
 * Change log   ：2021/01/13：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service;

import java.util.List;

import vn.com.unit.sla.dto.SlaReceiverDto;

/**
 * SlaReceiverService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public interface SlaReceiverService {

    /**
     * <p>
     * Get sla receiver dto list by involed type.
     * </p>
     *
     * @author TrieuVD
     * @param involedType
     *            type {@link Long}
     * @param businessKey
     *            type {@link Long}
     * @return {@link List<SlaReceiverDto>}
     */
    public List<SlaReceiverDto> getSlaReceiverDtoListByInvoledType(Long involedType, Long businessKey);

    /**
     * <p>
     * Get list email by list Receiver id.
     * </p>
     *
     * @author khadm
     * @param ReceiverIds
     *            type {@link List<Long>}
     * @return {@link List<String>}
     */
    public List<String> getListEmailByListReceiverId(List<Long> receiverIdList);
}
