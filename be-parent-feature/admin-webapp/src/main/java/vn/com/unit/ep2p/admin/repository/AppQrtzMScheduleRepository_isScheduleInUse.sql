select schedule.sched_id
from (select sched_id, company_id
		from qrtz_m_job_log
		union 
		select sched_id, company_id
		from qrtz_m_job_schedule) schedule
where schedule.sched_id = /*schedId*/''
/*IF companyId != null*/
AND schedule.company_id = /*companyId*/
/*END*/
/*IF companyId == null*/
AND schedule.company_id IS NULL
/*END*/
