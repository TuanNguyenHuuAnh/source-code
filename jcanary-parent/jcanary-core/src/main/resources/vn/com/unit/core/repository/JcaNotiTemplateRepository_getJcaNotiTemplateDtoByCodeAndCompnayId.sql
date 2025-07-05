SELECT
	noti_template.ID				AS ID
	, noti_template.CODE			AS CODE
	, noti_template.NAME			AS NAME
	, noti_template.DESCRIPTION		AS DESCRIPTION
	, noti_template.COMPANY_ID		AS COMPANY_ID
	, com.NAME						AS COMPANY_NAME
FROM 
	JCA_NOTI_TEMPLATE noti_template
LEFT JOIN 
	JCA_COMPANY com 
	ON noti_template.COMPANY_ID = com.ID AND com.DELETED_ID = 0
WHERE 
	noti_template.DELETED_ID = 0
	AND noti_template.CODE =/*code*/
	AND noti_template.COMPANY_ID = /*companyId*/