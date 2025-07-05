-- jcanary-quartz-job
-- QrtzMScheduleRepository_isScheduleInUse.sql

select schedule.sched_code
from (select sched_code, company_id
		from qrtz_m_job_log
		union 
		select sched_code, company_id
		from qrtz_m_job_schedule) schedule
where schedule.sched_code = /*schedCode*/''
/*IF companyId != null*/
AND schedule.company_id = /*companyId*/
/*END*/
/*IF companyId == null*/
AND schedule.company_id IS NULL
/*END*/
