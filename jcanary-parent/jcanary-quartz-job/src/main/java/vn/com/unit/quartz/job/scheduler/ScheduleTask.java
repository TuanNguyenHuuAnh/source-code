/*******************************************************************************
 * Class        ：ScheduleTask
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.scheduler;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * ScheduleTask
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public interface ScheduleTask {

	/**
     * <p>
     * JobStatus
     * </p>
     * .
     *
     * @author khadm
     * @version 01-00
     * @since 01-00
     */
	enum JobStatus {

		/** The waiting. */
		WAITING(1L, "WAITING"), /** The paused. */
 PAUSED(2L, "PAUSED"), /** The completed. */
 COMPLETED(3L, "COMPLETED"), /** The error. */
 ERROR(4L, "ERROR"), /** The stop. */
 STOP(5L,
				"STOP"), 
 /** The running. */
 RUNNING(6L, "RUNNING");

		/** The long value. */
		private Long longValue;

		/** The name. */
		private String name;

		/** The Constant mappings. */
		private static final Map<Long, JobStatus> mappings = new HashMap<>(5);

		static {
			for (JobStatus jobStatus : values()) {
				mappings.put(jobStatus.getLongValue(), jobStatus);
			}
		}

		/**
         * <p>
         * Resolve.
         * </p>
         *
         * @author khadm
         * @param value
         *            type {@link Long}
         * @return {@link JobStatus}
         */
		public static JobStatus resolve(Long value) {
			return (value != null ? mappings.get(value) : null);
		}

		/**
         * <p>
         * Instantiates a new job status.
         * </p>
         *
         * @author khadm
         * @param longValue
         *            type {@link long}
         * @param name
         *            type {@link String}
         */
		private JobStatus(long longValue, String name) {
			this.longValue = longValue;
			this.name = name;
		}

		/**
         * <p>
         * Get long value.
         * </p>
         *
         * @author khadm
         * @return {@link Long}
         */
		public Long getLongValue() {
			return longValue;
		}

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.name;
		}
		
		/**
         * <p>
         * Get string value.
         * </p>
         *
         * @author khadm
         * @return {@link String}
         */
		public String getStringValue(){
			return String.valueOf(longValue);
		}

	}

	/**
     * <p>
     * Do task.
     * </p>
     *
     * @author khadm
     * @throws Exception
     *             the exception
     */
	public void doTask() throws Exception;
}
