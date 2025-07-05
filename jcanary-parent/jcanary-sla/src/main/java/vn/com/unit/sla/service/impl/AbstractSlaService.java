/*******************************************************************************
 * Class        ：AbstractSlaService
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：TrieuVD
 * Change log   ：2020/12/15：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.sla.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.service.JCommonService;

/**
 * AbstractSlaService
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public abstract class AbstractSlaService {
    
    @Autowired
    protected ObjectMapper mapper;
    
    @Autowired
    protected JCommonService commonService;
}
