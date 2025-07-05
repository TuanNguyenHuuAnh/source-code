/*******************************************************************************
 * Class        ：AutowiringSpringBeanJobFactory
 * Created date ：2021/01/22
 * Lasted date  ：2021/01/22
 * Author       ：TrieuVD
 * Change log   ：2021/01/22：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.ep2p.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * AutowiringSpringBeanJobFactory
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

    /** The bean factory. */
    private transient AutowireCapableBeanFactory beanFactory;

    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(final ApplicationContext context) {
        beanFactory = context.getAutowireCapableBeanFactory();
    }

    /* (non-Javadoc)
     * @see org.springframework.scheduling.quartz.SpringBeanJobFactory#createJobInstance(org.quartz.spi.TriggerFiredBundle)
     */
    @Override
    public Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        final Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job); // the magic is done here
        return job;
    }

}