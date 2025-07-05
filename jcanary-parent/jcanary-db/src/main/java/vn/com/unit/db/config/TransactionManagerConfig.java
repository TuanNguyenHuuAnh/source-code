package vn.com.unit.db.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import vn.com.unit.db.service.impl.SqlManagerServiceImpl;

@Configuration
@EnableTransactionManagement
public class TransactionManagerConfig {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("dataSourceDb2")
    private DataSource dataSourceDb2;

//    @Autowired
//    private SqlManagerServiceImplImpl sqlManagerServiceImpl;

    @Bean(name = "transactionManagerSql")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        
//        transactionManager.setEnforceReadOnly(true);
        // The "SET TRANSACTION READ ONLY" is understood by Oracle, MySQL and Postgres
        
        
//        if ("com.microsoft.sqlserver.jdbc.SQLServerDriver" != sqlManagerServiceImpl.getConnectionProvider())
        transactionManager.setEnforceReadOnly(false);
        // SQL SERVER => disable "SET TRANSACTION READ ONLY"
        
        
        return transactionManager;
    }

    @Bean(name = "transactionManagerDb2")
    public DataSourceTransactionManager transactionManagerDb2() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSourceDb2);
        transactionManager.setEnforceReadOnly(false);
        return transactionManager;
    }
}
