/*******************************************************************************
 * Class        ：QuartzJobConfig
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：TrieuVD
 * Change log   ：2021/01/19：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.configurer;


import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import vn.com.unit.ep2p.admin.config.AutowiringSpringBeanJobFactory;
import vn.com.unit.ep2p.core.constant.AppApiConstant;

/**
 * QuartzJobConfig
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
@Configuration
public class QuartzJobConfig {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    @Qualifier("transactionManagerSql")
    private DataSourceTransactionManager dataSourceTransactionManager;
    
    @Autowired
    private Environment env;

    private SpringBeanJobFactory springBeanJobFactory;

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        if (springBeanJobFactory == null) {
            springBeanJobFactory = new AutowiringSpringBeanJobFactory();	
        }
        return springBeanJobFactory;
    }
    
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        
        factoryBean.setConfigLocation(new ClassPathResource("spring.quartz.properties"));
        factoryBean.setApplicationContext(applicationContext);
        factoryBean.setDataSource(dataSource);
        factoryBean.setTransactionManager(dataSourceTransactionManager);
        factoryBean.setJobFactory(springBeanJobFactory());
        
        factoryBean.setSchedulerName(env.getProperty(AppApiConstant.JCA_QUARTZ_CONFIG_SCHEDULER_NAME));
        factoryBean.setOverwriteExistingJobs(Boolean.valueOf(env.getProperty(AppApiConstant.JCA_QUARTZ_CONFIG_OVERWRITE_EXISTING_JOBS)));
        factoryBean.setWaitForJobsToCompleteOnShutdown(Boolean.valueOf(env.getProperty(AppApiConstant.JCA_QUARTZ_CONFIG_WAIT_FOR_JOBS_TO_COMPLETE_ON_SHUTDOWN)));
        factoryBean.setAutoStartup(Boolean.valueOf(env.getProperty(AppApiConstant.JCA_QUARTZ_CONFIG_AUTO_STARTUP)));
        
        return factoryBean;
    }
    
}
