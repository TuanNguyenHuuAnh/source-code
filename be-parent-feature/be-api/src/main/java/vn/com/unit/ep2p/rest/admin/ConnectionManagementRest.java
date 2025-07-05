/*******************************************************************************
 * Class        ：ConnectionManagementRest
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：vinhlt
 * Change log   ：2021/01/19：01-00 vinhlt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.rest.admin;

import java.lang.management.ManagementFactory;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zaxxer.hikari.HikariPoolMXBean;

import io.swagger.annotations.Api;
import vn.com.unit.dts.web.rest.common.DtsAppLog;
import vn.com.unit.ep2p.core.constant.AppApiConstant;

/**
 * ConnectionManagementRest
 * 
 * @version 01-00
 * @since 01-00
 * @author vinhlt
 */

@RestController
@RequestMapping(AppApiConstant.API + AppApiConstant.API_CONNECTION_MANAGEMENT)
@Api(tags = { "Connection management Rest" })
public class ConnectionManagementRest {

    @Autowired
    private Environment env;

    MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

    @PostMapping(AppApiConstant.POOL_STATUS)
    public Object getStatusPool(HttpServletRequest request) throws MalformedObjectNameException {
        String kPrivate = request.getHeader("KEY");
        if (kPrivate == null || !"coreApi".equals(kPrivate)) {
            return ResponseEntity.notFound().build();
        }
        String poolStringName = env.getProperty(AppApiConstant.SPRING_DATASOURCE_POOL_NAME);
        long start = System.currentTimeMillis();
        ObjectName poolName = new ObjectName(String.format("com.zaxxer.hikari:type=Pool (%s)", poolStringName));
        HikariPoolMXBean poolProxy = JMX.newMXBeanProxy(mBeanServer, poolName, HikariPoolMXBean.class);
        long took = System.currentTimeMillis() - start;
        return new DtsAppLog.Response(took, null, poolProxy.getTotalConnections(), poolProxy.getActiveConnections(),
                poolProxy.getIdleConnections(), poolProxy.getThreadsAwaitingConnection(), null);

    }
}
