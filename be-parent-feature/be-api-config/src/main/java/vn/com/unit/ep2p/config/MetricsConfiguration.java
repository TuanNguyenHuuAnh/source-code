package vn.com.unit.ep2p.config;
///*******************************************************************************
// * Class        ：MetricsConfiguration
// * Created date ：2021/01/19
// * Lasted date  ：2021/01/19
// * Author       ：vinhlt
// * Change log   ：2021/01/19：01-00 vinhlt create a new
// ******************************************************************************/
//package vn.com.unit.mbal.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.codahale.metrics.MetricRegistry;
//
///**
// * MetricsConfiguration
// * 
// * @version 01-00
// * @since 01-00
// * @author vinhlt
// */
//@Configuration
//public class MetricsConfiguration {
//    @Bean
//    public MetricRegistry metricRegistry() {
//      final MetricRegistry metricRegistry = new MetricRegistry();
//
//      metricRegistry.register("jvm.memory",new Memor);
//      metricRegistry.register("jvm.thread-states",new ThreadStatesGaugeSet());
//      metricRegistry.register("jvm.garbage-collector",new GarbageCollectorMetricSet());
//
//      return metricRegistry;
//    }
//
//    /*
//     * Reading all metrics that appear on the /metrics endpoint to expose them to metrics writer beans.
//     */
//    @Bean
//    public MetricsEndpointMetricReader metricsEndpointMetricReader(final MetricsEndpoint metricsEndpoint) {
//      return new MetricsEndpointMetricReader(metricsEndpoint);
//    }
//
//}
