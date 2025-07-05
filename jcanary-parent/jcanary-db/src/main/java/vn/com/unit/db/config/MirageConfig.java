package vn.com.unit.db.config;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import jp.sf.amateras.mirage.bean.BeanDescFactory;
import jp.sf.amateras.mirage.bean.FieldPropertyExtractor;
import jp.sf.amateras.mirage.integration.spring.SpringConnectionProvider;
import jp.sf.amateras.mirage.naming.RailsLikeNameConverter;
import jp.xet.springframework.data.mirage.repository.config.EnableMirageRepositories;
import jp.xet.springframework.data.mirage.repository.support.MiragePersistenceExceptionTranslator;
import vn.com.unit.db.service.impl.SqlManagerServiceImpl;

@Configuration
@EnableMirageRepositories(basePackages = "vn.com.unit.**.repository", sqlManagerRef = "sqlManagerServicePr")
public class MirageConfig {

    @Autowired
    @Qualifier("transactionManagerSql")
    private DataSourceTransactionManager transactionManager;

    private BeanDescFactory beanDescFactory;

    @Bean
    public BeanDescFactory beanDescFactory() {
        if (beanDescFactory == null) {
            beanDescFactory = new BeanDescFactory();
            beanDescFactory.setPropertyExtractor(new FieldPropertyExtractor());
        }
        return beanDescFactory;
    }

    @Bean("sqlManagerServicePr")
    @Primary
    public SqlManagerServiceImpl sqlManagerService() {
        // bridge java.util.logging used by mirage
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        SqlManagerServiceImpl sqlManagerServiceImpl = new SqlManagerServiceImpl();
        sqlManagerServiceImpl.setConnectionProvider(connectionProvider());
        sqlManagerServiceImpl.setDialect(new SQLServerDialect());
        sqlManagerServiceImpl.setBeanDescFactory(beanDescFactory());
        sqlManagerServiceImpl.setNameConverter(new RailsLikeNameConverter());
        return sqlManagerServiceImpl;
    }

    @Bean
    public MiragePersistenceExceptionTranslator persistenceExceptionTranslator() {
        return new MiragePersistenceExceptionTranslator();
    }

    @Bean(name="connectionProvider")
    @Primary
    public SpringConnectionProvider connectionProvider(){
        SpringConnectionProvider springConnectionProvider = new SpringConnectionProvider();
        springConnectionProvider.setTransactionManager(transactionManager);
        return springConnectionProvider;
    }
}
