/*******************************************************************************
 * Class        ：AopLogging
 * Created date ：2020/11/08
 * Lasted date  ：2020/11/08
 * Author       ：KhoaNA
 * Change log   ：2020/11/08：01-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.config;


import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.UUID;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
//import javax.sql.ConnectionPoolDataSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariPoolMXBean;

//import oracle.jdbc.OracleDriver;
//import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import vn.com.unit.dts.exception.ErrorHandler;
import vn.com.unit.dts.utils.DtsRequestUtil;
import vn.com.unit.dts.web.rest.common.DtsAppLog;
import vn.com.unit.ep2p.core.constant.AppApiConstant;

/**
 * AopLogging
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
@Aspect
@Configuration
public class AopLogging {

    @Autowired
    private HttpServletRequest httpRequest;
    
    @Autowired
    private Environment env;

    @Autowired
    private ErrorHandler errorHandler;

    @Autowired
    private ObjectMapper objectMapper;
    
    MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    
    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
        " || within(@org.springframework.stereotype.Service *)" +
        " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(vn.com.unit.ep2p.**.rest..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }
    
    /**
     * Tracking error all modules
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(vn.com.unit.ep2p..*)" +
            " || within(vn.com.unit.core.service..*)" +
            " || within(vn.com.unit.core.repository..*)" +
            " || within(vn.com.unit.workflow.service..*)" +
            " || within(vn.com.unit.workflow.repository..*)" +
            " || within(vn.com.unit.sla.service..*)" +
            " || within(vn.com.unit.sla.repository..*)" +
            " || within(vn.com.unit.storage.service..*)" +
            " || within(vn.com.unit.storage.repository..*)" +
            " || within(vn.com.unit.common.service..*)" +
            " || within(vn.com.unit.common.repository..*)" +
            " || within(vn.com.unit.dts.service..*)" +
            " || within(vn.com.unit.dts.repository..*)" + 
            " || within(vn.com.unit.ep2p.core.ers..*)" +
            " || within(vn.com.unit.ep2p.core.ers.service..*)" + 
            " || within(vn.com.unit.ep2p.core.ers.repository..*)" + 
            " || within(vn.com.unit.ies..*)" +
            " || within(vn.com.unit..*)" +
            " || within(vn.com.unit.cms.api..*)" +
            " || within(vn.com.unit.cms.api.controller..*)")
    public void applicationPackagePointcutTracking() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }
    
    /**
     * Retrieves the {@link Logger} associated to the given {@link JoinPoint}.
     *
     * @param joinPoint join point we want the logger for.
     * @return {@link Logger} associated to the given {@link JoinPoint}.
     */
    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }
    
    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice.
     * @param e exception.
     */
    @SuppressWarnings("deprecation")
    @AfterThrowing(pointcut = "applicationPackagePointcutTracking() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles("dev")) {
            logger(joinPoint)
                .error(
                    "Exception in {}() with cause = \'{}\' and exception = \'{}\'",
                    joinPoint.getSignature().getName(),
                    e.getCause() != null ? e.getCause() : "NULL",
                    e.getMessage(),
                    e
                );
        } else {
            logger(joinPoint)
                .error(
                    "Exception in {}() with cause = {}",
                    joinPoint.getSignature().getName(),
                    getStackTrace(e)
                );
        }
    }
    
    private String[] getStackTrace(Throwable ex) {
        String[] result = new String[2];
        result[0] = ex.getMessage();
        result[1] = Arrays.asList(ex.getStackTrace()).get(0).toString();
        return result;
    }

    @SuppressWarnings("deprecation")
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Logger log = logger(proceedingJoinPoint);
        boolean isRegisterMbeans = Boolean.valueOf(env.getProperty(AppApiConstant.SPRING_DATASOURCE_REGISTER_MBEANS));
        // hikari pool
        Integer totalConnections = null;
        Integer activeConnections = null;
        Integer idleConnections = null;
        Integer idleThreadsAwaitingConnection = null;
        if (isRegisterMbeans) {
			String poolStringName = env.getProperty(AppApiConstant.SPRING_DATASOURCE_POOL_NAME);
			ObjectName poolName = null;
			poolName = new ObjectName(String.format("com.zaxxer.hikari:type=Pool (%s)", poolStringName));
            HikariPoolMXBean poolProxy = JMX.newMXBeanProxy(mBeanServer, poolName, HikariPoolMXBean.class);
            totalConnections = poolProxy.getTotalConnections();
            activeConnections = poolProxy.getActiveConnections();
            idleConnections = poolProxy.getTotalConnections();
            idleThreadsAwaitingConnection = poolProxy.getIdleConnections();
        }
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}() with argument[s] = {}", proceedingJoinPoint.getSignature().getName(),
                    Arrays.toString(proceedingJoinPoint.getArgs()));
        }
        
        MDC.put("traceID", UUID.randomUUID().toString());
        String method = this.httpRequest.getMethod();
        String fullRequestUrl = DtsRequestUtil.getFullRequestUrl(this.httpRequest);
        String header = DtsRequestUtil.getInfoMandatoryHeader(this.httpRequest);
        String body = DtsRequestUtil.getBody(this.httpRequest, proceedingJoinPoint, this.objectMapper);
        boolean password = body.contains("password");
        if (password) {
            //LoginRequest loginRequest = this.objectMapper.readValue(body, LoginRequest.class);
            //loginRequest.setPassword("");
            //body = this.objectMapper.writeValueAsString(loginRequest);
        }
        boolean isSaveLog = false;
        isSaveLog = !fullRequestUrl.contains("pool-status");
        if (isSaveLog) {
            DtsAppLog.Request request = new DtsAppLog.Request(totalConnections, activeConnections, idleConnections,
                    idleThreadsAwaitingConnection, method, fullRequestUrl, header, body);
            new DtsAppLog(request, null).writeWithLoggerRequest("#REQUEST#");
        }
        MDC.put("header", header);
        Object result;
        long start = System.currentTimeMillis();
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            result = this.errorHandler.handlerException(exception, start);
        }
        long took = System.currentTimeMillis() - start;
        String resBody = "";

        if (result instanceof ResponseEntity) {
            Object bodyObject = ((ResponseEntity<?>) result).getBody();
            
            Assert.notNull(bodyObject, "ResponseEntity getBody is not null");
            resBody = bodyObject.toString();
        } else {
            try {
                resBody = this.objectMapper.writeValueAsString(result);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (isSaveLog) {
            DtsAppLog.Response response = new DtsAppLog.Response(took, null, totalConnections, activeConnections, idleConnections,
                    idleThreadsAwaitingConnection, resBody);
            new DtsAppLog(null, response).writeWithLoggerResponse("#RESPONSE#");
        }
        return result;
    }
}
