SELECT ID AS id,
		job_name AS name,
       	job_name AS text
FROM
    qrtz_m_job 
WHERE 
	validflag = 1
	/*IF term != null && term != ''*/
	and UPPER(job_name) LIKE CONCAT(CONCAT('%',UPPER(/*term*/'')),'%')
    /*END*/
	/*IF companyId == null*/
	AND company_id IS NULL
	/*END*/
	/*IF companyId != null && companyId != 0*/
	AND company_id  = /*companyId*/1
	/*END*/
ORDER BY id ASC
/*IF isPaging*/
OFFSET 0 ROWS FETCH NEXT 20 ROWS ONLY
/*END*/