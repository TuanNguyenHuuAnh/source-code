SELECT 
	role_com.COMPANY_ID					AS COMPANY_ID
	,role_com.ORG_ID					AS ORG_ID
	,role_com.IS_ADMIN					AS IS_ADMIN
FROM 
	JCA_ROLE_FOR_COMPANY role_com
LEFT JOIN
	JCA_ROLE role
ON
	role.ID = role_com.ROLE_ID
	AND role.DELETED_ID = 0
LEFT JOIN
	JCA_COMPANY com
ON
	com.ID = role_com.COMPANY_ID
	AND com.DELETED_ID = 0
WHERE 
	role.ID = /*roleId*/
	
	/*IF companyId != null */
	AND role_com.COMPANY_ID = /*companyId*/
	/*END*/