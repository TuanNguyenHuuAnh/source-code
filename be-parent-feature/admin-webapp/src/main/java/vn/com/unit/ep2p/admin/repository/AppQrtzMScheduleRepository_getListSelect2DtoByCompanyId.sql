SELECT ID AS id,
		sched_name AS name,
       	sched_name AS text
FROM
    qrtz_m_schedule 
WHERE 
	validflag = 1 
	/*IF term != null && term != ''*/
	and UPPER(sched_name) LIKE CONCAT(CONCAT('%',UPPER(/*term*/'')),'%')
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