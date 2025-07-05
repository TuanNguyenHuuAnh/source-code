SELECT
    *    
FROM
    JCA_SYSTEM_SETTING ss
WHERE
	ss.DELETED_ID = 0
    /*IF companyId != null*/
	AND ss.company_id = /*companyId*/1
	/*END*/