SELECT count(*)
FROM qrtz_m_job job left join qrtz_m_job_store store on job.job_group = store.group_code
	left join QRTZ_M_JOB_TYPE const on const.type = 'JOB_TYPE' and job.job_type = const.ID
where job.DELETED_ID = 0
AND job.validflag = 1
/*IF jobSearch.companyId != null && jobSearch.companyId != 0*/
AND job.company_id = /*jobSearch.companyId*/1
/*END*/
/*IF jobSearch.companyId == null*/
AND job.company_id IS NULL
/*END*/
/*IF !jobSearch.companyAdmin && jobSearch.companyId == 0*/
AND (job.company_id  IN /*jobSearch.companyIdList*/()
	OR job.company_id IS NULL)
/*END*/
/*BEGIN*/
and(
		/*IF jobSearch.jobCode != null && jobSearch.jobCode != ''*/
		or UPPER(job.job_code) like CONCAT(CONCAT('%', UPPER(/*jobSearch.jobCode*/)), '%')
		/*END*/
		
		/*IF jobSearch.jobName != null && jobSearch.jobName != ''*/
		or UPPER(job.job_name) like CONCAT(CONCAT('%', UPPER(/*jobSearch.jobName*/)), '%')
		/*END*/
		
		/*IF jobSearch.jobType != null && jobSearch.jobType != ''*/
		or UPPER(const.official_name) like CONCAT(CONCAT('%', UPPER(/*jobSearch.jobType*/)), '%')
		/*END*/
		
		/*IF jobSearch.jobClassName != null && jobSearch.jobClassName != ''*/
		or UPPER(job.job_class_name) like CONCAT(CONCAT('%', UPPER(/*jobSearch.jobClassName*/)), '%')
		/*END*/
		
		/*IF jobSearch.storeName != null && jobSearch.storeName != ''*/
		or UPPER(store.store_name) like CONCAT(CONCAT('%', UPPER(/*jobSearch.storeName*/)), '%')
		/*END*/
)
/*END*/