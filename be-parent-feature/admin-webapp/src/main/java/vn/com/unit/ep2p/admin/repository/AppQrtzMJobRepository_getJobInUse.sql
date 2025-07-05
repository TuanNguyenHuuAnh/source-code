select job.JOB_ID
from (select JOB_ID
		from qrtz_m_job_log
		union 
		select JOB_ID
		from qrtz_m_job_schedule) job
where job.JOB_ID = /*jobId*/''