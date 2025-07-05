DELETE
	JCA_ROLE_FOR_COMPANY
WHERE 
	rolecompany.COMPANY_ID = /*companyId*/
	AND rolecompany.ORG_ID  = /*orgId*/
	AND rolecompany.ROLE_ID = /*roleId*/
	