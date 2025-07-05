SELECT job_code AS id,
		job_name AS name,
       	job_name AS text
FROM
    qrtz_m_job 
WHERE 
	validflag = 1 
	/*IF companyIdList != null*/
	AND company_id  IN /*companyIdList*/()
	/*END*/
	and (UPPER(id) LIKE CONCAT(CONCAT('%',UPPER(/*term*/'')),'%')
         OR UPPER(job_name) LIKE CONCAT(CONCAT('%',UPPER(/*term*/'')),'%'))
ORDER BY id ASC
OFFSET 0 ROWS FETCH NEXT 20 ROWS ONLY