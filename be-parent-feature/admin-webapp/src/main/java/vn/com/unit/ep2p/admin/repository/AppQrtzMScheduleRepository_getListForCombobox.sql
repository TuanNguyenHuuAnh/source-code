SELECT sched_code AS id,
		sched_name AS name,
       	sched_name AS text
FROM
    qrtz_m_schedule 
WHERE 
	validflag = 1 
	/*IF companyIdList != null*/
	AND company_id  IN /*companyIdList*/()
	/*END*/
	and (UPPER(sched_code) LIKE CONCAT(CONCAT('%',UPPER(/*term*/'')),'%')
         OR UPPER(sched_name) LIKE CONCAT(CONCAT('%',UPPER(/*term*/'')),'%'))
ORDER BY id ASC
OFFSET 0 ROWS FETCH NEXT 20 ROWS ONLY