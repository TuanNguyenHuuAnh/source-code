select *
from qrtz_m_job 
where 
	validflag = 1
	AND job_group = /*jobGroup*/'' 
	AND COMPANY_ID = /*companyId*/0 