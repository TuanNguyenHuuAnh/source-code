/*******************************************************************************
* Class        :BeanCoreConfiguration
* Created date :2020/11/25
* Lasted date  :2020/11/25
* Author       :KhoaNA
* Change log   :2020/11/25 01-00 KhoaNA create a new
******************************************************************************/
package vn.com.unit.ep2p.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.service.impl.JCommonServiceImpl;

/**
 * BeanCoreConfiguration
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Configuration
public class BeanCoreConfiguration {

    @Bean
    public JCommonService commonService() {
        return new JCommonServiceImpl();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}