package vn.com.unit.db.config;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import jp.sf.amateras.mirage.bean.BeanDescFactory;
import jp.sf.amateras.mirage.bean.FieldPropertyExtractor;
import jp.sf.amateras.mirage.integration.spring.SpringConnectionProvider;
import jp.sf.amateras.mirage.naming.RailsLikeNameConverter;
import jp.xet.springframework.data.mirage.repository.config.EnableMirageRepositories;
import jp.xet.springframework.data.mirage.repository.support.MiragePersistenceExceptionTranslator;
import vn.com.unit.db.service.impl.SqlManagerDb2ServiceImpl;

@Configuration
@EnableMirageRepositories(basePackages = {"vn.com.unit.cms.core.module.db2","vn.com.unit.ep2p.admin.db2"}, sqlManagerRef = "sqlManageDb2Service")
public class MirageConfigDb2 {

    @Autowired
    @Qualifier("transactionManagerDb2")
    private DataSourceTransactionManager transactionManagerDb2;

    private BeanDescFactory beanDescFactory;

    @Bean("beanDescFactoryDb2")
    public BeanDescFactory beanDescFactory() {
        if (beanDescFactory == null) {
            beanDescFactory = new BeanDescFactory();
            beanDescFactory.setPropertyExtractor(new FieldPropertyExtractor());
        }
        return beanDescFactory;
    }

    @Bean("sqlManageDb2Service")
    public SqlManagerDb2ServiceImpl sqlManagerService() {
        // bridge java.util.logging used by mirage
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        SqlManagerDb2ServiceImpl sqlManagerServiceImpl = new SqlManagerDb2ServiceImpl();
        sqlManagerServiceImpl.setConnectionProvider(connectionProviderDb2());
        sqlManagerServiceImpl.setDialect(new Db2Dialect());
        sqlManagerServiceImpl.setBeanDescFactory(beanDescFactory());
        sqlManagerServiceImpl.setNameConverter(new RailsLikeNameConverter());
        return sqlManagerServiceImpl;
    }

    @Bean("persistenceExceptionTranslatorDb2")
    public MiragePersistenceExceptionTranslator persistenceExceptionTranslator() {
        return new MiragePersistenceExceptionTranslator();
    }

    @Bean(name="connectionProviderDb2")
    public SpringConnectionProvider connectionProviderDb2(){
        SpringConnectionProvider springConnectionProvider = new SpringConnectionProvider();
        springConnectionProvider.setTransactionManager(transactionManagerDb2);
        return springConnectionProvider;
    }
}
