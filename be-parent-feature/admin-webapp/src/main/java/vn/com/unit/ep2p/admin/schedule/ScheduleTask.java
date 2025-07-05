package vn.com.unit.ep2p.admin.schedule;

import java.util.HashMap;
import java.util.Map;

/**
 * ScheduleTask
 * 
 * @version 01-00
 * @since 01-00
 * @author CongDT
 */
public interface ScheduleTask {

	enum JobStatus {

		WAITING(1L, "WAITING"), PAUSED(2L, "PAUSED"), COMPLETED(3L, "COMPLETED"), ERROR(4L, "ERROR"), STOP(5L,
				"STOP"), RUNNING(6L, "RUNNING");

		private Long longValue;

		private String name;

		private static final Map<Long, JobStatus> mappings = new HashMap<>(5);

		static {
			for (JobStatus jobStatus : values()) {
				mappings.put(jobStatus.getLongValue(), jobStatus);
			}
		}

		public static JobStatus resolve(Long value) {
			return (value != null ? mappings.get(value) : null);
		}

		private JobStatus(long longValue, String name) {
			this.longValue = longValue;
			this.name = name;
		}

		public Long getLongValue() {
			return longValue;
		}

		@Override
		public String toString() {
			return this.name;
		}
		
		public String getStringValue(){
			return String.valueOf(longValue);
		}

	}

	public void doTask() throws Exception;
}
