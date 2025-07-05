/*******************************************************************************
 * Class        ：SimpleJob
 * Created date ：2021/01/19
 * Lasted date  ：2021/01/19
 * Author       ：TrieuVD
 * Change log   ：2021/01/19：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.quartz.job.service.impl;

import org.quartz.JobExecutionContext;

import vn.com.unit.quartz.job.JcaQuartzJob;

/**
 * SimpleJob
 * 
 * @version 01-00
 * @since 01-00
 * @author TrieuVD
 */
public class SimpleJob extends JcaQuartzJob{

    /* (non-Javadoc)
     * @see vn.com.unit.quartz.job.JcaQuartzJob#executeJcaQuartzJob(org.quartz.JobExecutionContext)
     */
    @Override
    protected void executeJcaQuartzJob(JobExecutionContext context) {
        System.out.println("SimpleJob");
        
    }

}