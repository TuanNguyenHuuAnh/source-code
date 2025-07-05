SELECT
	org.ID 							as ORG_ID
	,org.CODE						as CODE
	,org.NAME						as NAME
	,org.NAME_ABV					as NAME_ABV
	,org.DISPLAY_ORDER				as DISPLAY_ORDER
	,org.ORG_TYPE					as ORG_TYPE
	,org_path.ANCESTOR_ID			as PARENT_ORG_ID
	,org.EMAIL						as EMAIL
	,org.DESCRIPTION				as DESCRIPTION
	,org.PHONE						as PHONE
	,org.COMPANY_ID					as COMPANY_ID
	,org.ADDRESS					as ADDRESS
	,org.ACTIVED					as ACTIVED
FROM 
	JCA_ORGANIZATION org
LEFT JOIN
	JCA_ORGANIZATION_PATH org_path
ON 
	org.ID  = org_path.DESCENDANT_ID
	AND org_path.DEPTH = 1
WHERE  
	org.DELETED_ID = 0
	AND org.ID = /*orgId*/
