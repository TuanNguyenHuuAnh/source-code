SELECT DISTINCT id AS id,
		job_name AS name,
       	job_name AS text
FROM
    qrtz_m_job 
WHERE
	DELETED_ID = 0
	and validflag = 1
	/*IF jobId != null && jobId != ""*/
    and id = /*jobId*/'' 
    /*END*/
    /*IF id != null*/
    and company_id = /*id*/0 
    /*END*/