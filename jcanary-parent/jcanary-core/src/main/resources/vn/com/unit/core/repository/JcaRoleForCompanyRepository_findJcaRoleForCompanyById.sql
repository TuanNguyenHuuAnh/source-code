SELECT 
  	,rolecompany.COMPANY_ID     	AS COMPANY_ID
 	,rolecompany.ORG_ID         	AS ORG_ID
  	,rolecompany.ROLE_ID        	AS ROLE_ID
 	,rolecompany.IS_ADMIN       	AS IS_ADMIN
	,rolecompany.CREATED_ID		 	AS CREATED_ID
	,rolecompany.CREATED_DATE		AS CREATED_DATE
FROM JCA_ROLE_FOR_COMPANY rolecompany

WHERE  
	rolecompany.COMPANY_ID = /*companyId*/
	AND rolecompany.ORG_ID  = /*orgId*/
	AND rolecompany.ROLE_ID = /*roleId*/