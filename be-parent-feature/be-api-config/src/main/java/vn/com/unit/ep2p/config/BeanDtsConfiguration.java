package vn.com.unit.ep2p.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.exception.SuccessHandler;
import vn.com.unit.dts.exception.impl.ErrorHandlerApiImpl;
import vn.com.unit.dts.exception.impl.SuccessHandlerApiImpl;


@Configuration
@ComponentScan("vn.com.unit")
public class BeanDtsConfiguration {

    @Bean
    public ErrorHandler getErrorHandler() {
        return new ErrorHandlerApiImpl();
    }
    
    @Bean
    public SuccessHandler getSuccessHandler() {
        return new SuccessHandlerApiImpl();
    }
    
}