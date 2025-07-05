select job.job_code
from (select job_code
		from qrtz_m_job_log
		union 
		select job_code
		from qrtz_m_job_schedule) job
where job_code = /*jobCode*/''