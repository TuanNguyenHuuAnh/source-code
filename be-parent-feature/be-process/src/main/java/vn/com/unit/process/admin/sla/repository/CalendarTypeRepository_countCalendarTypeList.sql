SELECT 
	count(*)
FROM 
(
	SELECT 
	    *
	FROM
	    SLA_CALENDAR_TYPE
	WHERE
		DELETED_ID = 0
		/*IF search.companyId != null && search.companyId != 0*/
		AND company_id = /*search.companyId*/1
		/*END*/
		/*IF search.companyId == null*/
		AND company_id is null
		/*END*/
		/*IF search.companyId == 0 && !search.companyAdmin*/
		AND (company_id  IN /*search.companyIdList*/()
		OR company_id IS NULL)
		/*END*/
		/*BEGIN*/
		AND(
			/*IF search.code != null && search.code != ''*/
			OR UPPER(code) LIKE CONCAT( '%', CONCAT(UPPER(/*search.code*/) , '%') )
			/*END*/
			
			/*IF search.name != null && search.name != ''*/
			OR UPPER(name) LIKE CONCAT( '%', CONCAT(UPPER(/*search.name*/) , '%') )
			/*END*/
			
		    /*IF search.description != null && search.description != ''*/
	        OR UPPER(description) LIKE CONCAT( '%', CONCAT(UPPER(/*search.description*/) , '%') )
	        /*END*/
        )
        /*END*/
)  TBL