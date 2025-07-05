SELECT DISTINCT id AS id,
		sched_name AS name,
       	sched_name AS text
FROM
    qrtz_m_schedule 
WHERE
	DELETED_ID = 0
	AND validflag = 1 
	/*IF schedId != null && schedId != ""*/
    and id = /*schedId*/'' 
    /*END*/
    /*IF id != null*/
    and company_id = /*id*/0 
    /*END*/