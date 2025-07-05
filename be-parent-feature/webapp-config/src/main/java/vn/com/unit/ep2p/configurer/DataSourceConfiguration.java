package vn.com.unit.ep2p.configurer;

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

import com.zaxxer.hikari.HikariDataSource;

import vn.com.unit.ep2p.core.constant.AppApiConstant;

@Configuration
public class DataSourceConfiguration {

    @Autowired
    private Environment env;
//
//    @Autowired
//    private MetricRegistry metricRegistry;

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
    
    @Profile("dev")
    @Bean(name="dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSourceForDev() {
        HikariDataSource datasource = new HikariDataSource();
        datasource.setDriverClassName(env.getProperty(AppApiConstant.SPRING_DATASOURCE_DRIVER_CLASS_NAME));
        datasource.setJdbcUrl(env.getProperty(AppApiConstant.SPRING_DATASOURCE_URL));
        datasource.setUsername(env.getProperty(AppApiConstant.SPRING_DATASOURCE_USERNAME));
        datasource.setPassword(env.getProperty(AppApiConstant.SPRING_DATASOURCE_PASSWORD));
        datasource.setPoolName(env.getProperty(AppApiConstant.SPRING_DATASOURCE_POOL_NAME));
        datasource.setAutoCommit(Boolean.valueOf(env.getProperty(AppApiConstant.SPRING_DATASOURCE_AUTO_COMMIT)));
        boolean isRegisterMbeans = Boolean.valueOf(env.getProperty(AppApiConstant.SPRING_DATASOURCE_REGISTER_MBEANS));
        datasource.setRegisterMbeans(isRegisterMbeans);
//        datasource.setMetricRegistry(metricRegistry);

        String minimumIdle = env.getProperty(AppApiConstant.SPRING_DATASOURCE_MINIMUM_IDLE);
        if (minimumIdle != null) {
            datasource.setMinimumIdle(Integer.valueOf(minimumIdle));
        }
        String idleTimeout = env.getProperty(AppApiConstant.SPRING_DATASOURCE_IDLE_TIMEOUT);
        if (idleTimeout != null) {
            datasource.setIdleTimeout(Long.valueOf(idleTimeout));
        }
        String connectionTimeout = env.getProperty(AppApiConstant.SPRING_DATASOURCE_CONNECTION_TIMEOUT);
        if (connectionTimeout != null) {
            datasource.setConnectionTimeout(Long.valueOf(connectionTimeout));
        }
        String maxLifetile = env.getProperty(AppApiConstant.SPRING_DATASOURCE_MAX_LIFETIME);
        if (connectionTimeout != null) {
            datasource.setMaxLifetime(Long.valueOf(maxLifetile));
        }
        return datasource;
    }
    
    @Profile("dev")
    @Bean(name="dataSourceDb2")
    @ConfigurationProperties(prefix = "spring.datasource-db2")
    public DataSource dataSourceDb2ForDev() {
        HikariDataSource datasource = new HikariDataSource();
        datasource.setDriverClassName(env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB2_DRIVER_CLASS_NAME));
        datasource.setJdbcUrl(env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB2_URL));
        datasource.setUsername(env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB2_USERNAME));
        datasource.setPassword(env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB2_PASSWORD));
        datasource.setPoolName(env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB2_POOL_NAME));
        datasource.setAutoCommit(Boolean.valueOf(env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB2_AUTO_COMMIT)));
        boolean isRegisterMbeans = Boolean.valueOf(env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB2_REGISTER_MBEANS));
        datasource.setRegisterMbeans(isRegisterMbeans);
//        datasource.setMetricRegistry(metricRegistry);

        String minimumIdle = env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB2_MINIMUM_IDLE);
        if (minimumIdle != null) {
            datasource.setMinimumIdle(Integer.valueOf(minimumIdle));
        }
        String idleTimeout = env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB2_IDLE_TIMEOUT);
        if (idleTimeout != null) {
            datasource.setIdleTimeout(Long.valueOf(idleTimeout));
        }
        String connectionTimeout = env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB2_CONNECTION_TIMEOUT);
        if (connectionTimeout != null) {
            datasource.setConnectionTimeout(Long.valueOf(connectionTimeout));
        }
        String maxLifetile = env.getProperty(AppApiConstant.SPRING_DATASOURCE_DB2_MAX_LIFETIME);
        if (connectionTimeout != null) {
            datasource.setMaxLifetime(Long.valueOf(maxLifetile));
        }
        return datasource;
    }
    @Bean
    public MBeanExporter exporter() {
        final MBeanExporter exporter = new MBeanExporter();
        exporter.setAutodetect(true);
        exporter.setExcludedBeans("dataSource","dataSourceDb2","dataSourceLog");
        return exporter;
    }
}