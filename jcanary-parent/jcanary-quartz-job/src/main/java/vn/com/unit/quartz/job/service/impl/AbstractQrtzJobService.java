/*******************************************************************************
 * Class        ：AbstractQrtzJobService
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/

package vn.com.unit.quartz.job.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;

/**
 * <p>
 * AbstractQrtzJobService
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public abstract class AbstractQrtzJobService {
    
    /** The mapper. */
    @Autowired
    protected ObjectMapper mapper;
    
    /** The common service. */
    @Autowired
    protected JCommonService commonService;
}
