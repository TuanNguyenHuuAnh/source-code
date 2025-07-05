package vn.com.unit.ep2p.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.jmx.export.MBeanExporter;

import com.codahale.metrics.MetricRegistry;

import vn.com.unit.ep2p.core.constant.AppApiConstant;

@Configuration
public class DataSourceConfiguration {

    @Autowired
    private Environment env;

    @Autowired
    private MetricRegistry metricRegistry;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfiguration.class);

    @Profile("!dev")
    @Bean(name="dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        dataSourceLookup.setResourceRef(true);
        DataSource dataSourceTemp = null;
        try {
            dataSourceTemp = dataSourceLookup.getDataSource(env.getProperty(AppApiConstant.SPRING_DATASOURCE_JNDI_NAME));
        } catch (DataSourceLookupFailureException e) {
            LOGGER.error("JNDI name" + env.getProperty(AppApiConstant.SPRING_DATASOURCE_JNDI_NAME) + " not found.");
        }
        return dataSourceTemp;
    }
    @Profile("!dev")
    @Bean(name="dataSourceDb2")
    @ConfigurationProperties(prefix = "spring.datasource-db2")
    public DataSource dataSourceDb2() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        dataSourceLookup.setResourceRef(true);
        DataSource dataSourceTemp = null;
        try {
            dataSourceTemp = dataSourceLookup.getDataSource(env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB_2_NAME));
        } catch (DataSourceLookupFailureException e) {
            LOGGER.error("JNDI name" + env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB_2_NAME) + " not found.");
        }
        return dataSourceTemp;
    }
    @Profile("!dev")
    @Bean(name="dataSourceLog")
    @ConfigurationProperties(prefix = "spring.datasource-dblog")
    public DataSource dataSourceLog() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        dataSourceLookup.setResourceRef(true);
        DataSource dataSourceTemp = null;
        try {
            dataSourceTemp = dataSourceLookup.getDataSource(env.getProperty(AppApiConstant.SPRING_DATASOURCE_LOG_NAME));
        } catch (DataSourceLookupFailureException e) {
            LOGGER.error("JNDI name" + env.getProperty(AppApiConstant.SPRING_DATASOURCE_LOG_NAME) + " not found.");
        }
        return dataSourceTemp;
    }
    
    @Bean
    public MBeanExporter exporter() {
        final MBeanExporter exporter = new MBeanExporter();
        exporter.setAutodetect(true);
        exporter.setExcludedBeans("dataSource","dataSourceDb2","dataSourceLog");
        return exporter;
    }
}