SELECT
    *
FROM
    JCA_ORGANIZATION
WHERE   
	deleted_id = 0
    /*IF orgCode != null && orgCode != ''*/
    AND UPPER(code) = UPPER(/*orgCode*/)
    /*END*/ 
    /*IF companyId!=null && companyId!=''*/
	AND COMPANY_ID = /*companyId*/
	/*END*/
ORDER BY 
    id