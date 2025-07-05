package vn.com.unit.ep2p.configurer;

import java.io.IOException;

import javax.sql.DataSource;

import org.activiti.dmn.api.DmnRepositoryService;
import org.activiti.dmn.api.DmnRuleService;
import org.activiti.dmn.engine.DmnEngine;
import org.activiti.dmn.engine.impl.cfg.StandaloneDmnEngineConfiguration;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ProcessEngineConfig {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String BPMN_PATH = "processes/";

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public StandaloneDmnEngineConfiguration dmnEngineConfiguration() {
		StandaloneDmnEngineConfiguration config = new StandaloneDmnEngineConfiguration();

		config.setDataSource(dataSource);
		return config;
	}

	@Bean
	public DmnEngine dmnEngine() {
		return dmnEngineConfiguration().buildDmnEngine();
	}

	@Bean
	public DmnRepositoryService dmnRepositoryService(DmnEngine dmnEngine) {
		return dmnEngine.getDmnRepositoryService();
	}

	@Bean
	public DmnRuleService dmnRuleService(DmnEngine dmnEngine) {
		return dmnEngine.getDmnRuleService();
	}

	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration() {
		SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
		try {
			config.setDeploymentResources(getBpmnFiles());
		} catch (IOException e) {
			logger.error("Exception ", e);
		}
		config.setDataSource(dataSource);
		config.setTransactionManager(transactionManager());
		config.setDatabaseSchemaUpdate("true");
		config.setHistory("audit");
		// config.setJobExecutorActivate(true);
		// DMN
		config.setDmnEngineInitialized(true);
		config.setDmnEngineRepositoryService(dmnRepositoryService(dmnEngine()));
		config.setDmnEngineRuleService(dmnRuleService(dmnEngine()));

		return config;
	}

	private Resource[] getBpmnFiles() throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		return resourcePatternResolver.getResources("classpath*:" + BPMN_PATH + "**/*.bpmn");
	}

	@Bean
	public RepositoryService repositoryService(ProcessEngine processEngine) {
		return processEngine.getRepositoryService();
	}

	@Bean
	public IdentityService identityService(ProcessEngine processEngine) {
		return processEngine.getIdentityService();
	}

	@Bean
	public FormService formService(ProcessEngine processEngine) {
		return processEngine.getFormService();
	}

	@Bean
	public RuntimeService runtimeService(ProcessEngine processEngine) {
		return processEngine.getRuntimeService();
	}

	@Bean
	public TaskService taskService(ProcessEngine processEngine) {
		return processEngine.getTaskService();
	}

	@Bean
	public ManagementService managementService(ProcessEngine processEngine) {
		return processEngine.getManagementService();
	}

	@Bean
	public HistoryService historyService(ProcessEngine processEngine) {
		return processEngine.getHistoryService();
	}
}