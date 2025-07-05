/********************************************************************************
* Class        : DtsConstant
* Created date : 2020/11/08
* Lasted date  : 2020/11/08
* Author       : TaiTT
* Change log   : 2020/11/08 : 01-00 TaiTT create a new
******************************************************************************/
package vn.com.unit.dts.constant;



/**
* <p> DtsConstant </p>
* 
* @version : 01-00
* @since 01-00
* @author : TaiTT
 */
public class DtsConstant {


	// CONFIG DATASOURCE
	public static final String SPRING_DATASOURCE_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";
	public static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
	public static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
	public static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
	public static final String SPRING_DATASOURCE_JNDI_NAME = "spring.datasource.jndi-name";
	public static final String SPRING_DATASOURCE_DB_2_NAME = "spring.datasource-db2.jndi-name";
	public static final String SPRING_DATASOURCE_LOG_NAME = "spring.datasource-dblog.jndi-name";

	// ENCODING
	public static final String UTF_8 = "UTF-8";

	// JCA QUARTZ CONFIG
	public static final String JCA_QUARTZ_CONFIG_OVERWRITE_EXISTING_JOBS = "jca-quartz.config.overwrite-existing-jobs";
	public static final String JCA_QUARTZ_CONFIG_SCHEDULER_NAME = "jca-quartz.config.scheduler-name";
	public static final String JCA_QUARTZ_CONFIG_WAIT_FOR_JOBS_TO_COMPLETE_ON_SHUTDOWN = "jca-quartz.config.wait-for-jobs-to-complete-on-shutdown";
	public static final String JCA_QUARTZ_CONFIG_AUTO_STARTUP = "jca-quartz.config.auto-startup";

	// API - EXTERNAL 
	public static final int API_EXTERNAL_TIMEOUT = 120000;
	/** Call api with service REST Full */ 
	public static final String API_PROTOCOL_REST_FULL = "REST_FULL";
	/** Call api with service SOAP */ 
	public static final String API_PROTOCOL_SOAP = "SOAP";

	// CHARACTER SPECIAL
	/** QUESTION_MARK */ 
	public static final String QUESTION_MARK = "?";
	public static final String EMPTY = "";
	public static final String UNDERLINED = "_";
	public static final String HYPHEN = "-";
	public static final String ASTERISK = "*";
	public static final String DOT = ".";
	public static final String COMMA = ",";
	public static final String SEMI_COLON = ";";
	/** HASHTAG */ 
	public static final String HASHTAG = "#";

	// EXTERNAL CONFIG DATA SOURCE HIKARI
	public static final String SPRING_DATASOURCE_POOL_NAME = "spring.datasource.hikari.poolName";
	public static final String SPRING_DATASOURCE_AUTO_COMMIT = "spring.datasource.hikari.auto-commit";
	public static final String SPRING_DATASOURCE_REGISTER_MBEANS = "spring.datasource.hikari.register-mbeans";
	public static final String SPRING_DATASOURCE_MINIMUM_IDLE = "spring.datasource.hikari.minimum-idle";
	public static final String SPRING_DATASOURCE_IDLE_TIMEOUT = "spring.datasource.hikari.idle-timeout";
	public static final String SPRING_DATASOURCE_CONNECTION_TIMEOUT = "spring.datasource.hikari.connection-timeout";
	public static final String SPRING_DATASOURCE_MAX_LIFETIME = "spring.datasource.hikari.max-lifetime";
	public static final String SPRING_DATASOURCE_DB_2_POOL_NAME = "spring.datasource-db2.hikari.poolName";
	public static final String SPRING_DATASOURCE_DB_2_REGISTER_MBEANS = "spring.datasource-db2.hikari.register-mbeans";

	// FORMAT DATE
	public static final String DDMMYYYY_TIME_HYPHEN = "dd-MM-yyyy hh:mm:ss";
	public static final String DDMMYYYY_SLASH = "dd/MM/yyyy";
	public static final String YYYYMMDD_TIME = "yyyyMMddHHmmss";
	public static final String YYYYMMDD = "yyyyMMdd";

	// JWT
	public static final int JWT_LENGTH = 8;
	public static final String JWT_DEVICE_ID = "DEVICE-ID";
	public static final String JWT_TOKEN = "TOKEN";
	public static final String JWT_AUTHORIZATION = "Authorization";
	public static final String JWT_BEARER = "Bearer ";
	public static final String JWT_CONTENT_LANGUAGE = "Content-language";
	public static final String JWT_LANGUAGE_DEFAULT = "en";

	// REPONSE CODE - MESSAGE
	public static final String ERROR = "error";
	public static final String SUCCESS = "success";
	public static final String INFO = "info";
	public static final String WARNING = "warning";
	public static final int RESULT_CODE_SYSTEM_ERROR = -99;
	public static final int RESULT_CODE_SYSTEM_SUCCESS = 0;
	public static final int SUCCESS_CODE = 200;

	// REQUEST METHOD
	public static final String REQUEST_POST = "POST";
	public static final String REQUEST_PUT = "PUT";
	public static final String REQUEST_DELETE = "DELETE";

	// SEQUENCE
	public static final String SEQ = "SEQ_";
/**
* @author: TriNT
* @since: 04/10/2021 11:36 SA
* @description:
* @update:
*
* */
	// CONFIG DATASOURCE db2
	public static final String SPRING_DATASOURCE_DB2_DRIVER_CLASS_NAME = "spring.datasource-db2.driver-class-name";
	public static final String SPRING_DATASOURCE_DB2_URL = "spring.datasource-db2.url";
	public static final String SPRING_DATASOURCE_DB2_USERNAME = "spring.datasource-db2.username";
	public static final String SPRING_DATASOURCE_DB2_PASSWORD = "spring.datasource-db2.password";
	public static final String SPRING_DATASOURCE_DB2_JNDI_NAME = "spring.datasource-db2.jndi-name";

	// EXTERNAL CONFIG DATA SOURCE HIKARI
	public static final String SPRING_DATASOURCE_DB2_POOL_NAME = "spring.datasource-db2.hikari.poolName";
	public static final String SPRING_DATASOURCE_DB2_AUTO_COMMIT = "spring.datasource-db2.hikari.auto-commit";
	public static final String SPRING_DATASOURCE_DB2_REGISTER_MBEANS = "spring.datasource-db2.hikari.register-mbeans";
	public static final String SPRING_DATASOURCE_DB2_MINIMUM_IDLE = "spring.datasource-db2.hikari.minimum-idle";
	public static final String SPRING_DATASOURCE_DB2_IDLE_TIMEOUT = "spring.datasource-db2.hikari.idle-timeout";
	public static final String SPRING_DATASOURCE_DB2_CONNECTION_TIMEOUT = "spring.datasource-db2.hikari.connection-timeout";
	public static final String SPRING_DATASOURCE_DB2_MAX_LIFETIME = "spring.datasource-db2.hikari.max-lifetime";
}