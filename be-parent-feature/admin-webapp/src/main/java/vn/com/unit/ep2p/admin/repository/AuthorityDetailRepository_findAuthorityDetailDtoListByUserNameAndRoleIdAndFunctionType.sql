SELECT DISTINCT
	detail.USERNAME      AS USERNAME
	, detail.FULLNAME      AS FULLNAME
	, detail.EMAIL         AS EMAIL
	, detail.FUNCTION_CODE AS FUNCTION_CODE
	, detail.FUNCTION_NAME AS FUNCTION_NAME
	, detail.ACCESS_RIGHT  AS ACCESS_RIGHT
	, detail.COMPANY_ID	   AS COMPANY_ID
FROM 
	VW_AUTHORITY_DETAIL detail
WHERE
	detail.ACCESS_RIGHT = 'ACCESS'
	AND detail.COMPANY_ID = /*companyId*/1
	AND detail.USERNAME = /*username*/''
	/*IF roleId != null*/
	AND detail.ROLE_ID = /*roleId*/
	/*END*/
	AND detail.FUNCTION_TYPE = /*functionType*/''
ORDER BY 
	USERNAME
	, FUNCTION_NAME