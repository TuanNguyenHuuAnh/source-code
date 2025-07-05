SELECT 
  	,rolecompany.COMPANY_ID     	AS COMPANY_ID
 	,rolecompany.ORG_ID         	AS ORG_ID
  	,rolecompany.ROLE_ID        	AS ROLE_ID
 	,rolecompany.IS_ADMIN       	AS IS_ADMIN
	,rolecompany.CREATED_ID		 	AS CREATED_ID
	,rolecompany.CREATED_DATE		AS CREATED_DATE
FROM JCA_ROLE_FOR_COMPANY rolecompany
/*BEGIN*/
WHERE 
	/*IF jcaRoleForCompanySearchDto.companyId != null */
	AND rolecompany.COMPANY_ID = /*jcaRoleForCompanySearchDto.companyId*/
	/*END*/	
		/*IF jcaRoleForCompanySearchDto.roleId != null */
	AND rolecompany.ROLE_ID = /*jcaRoleForCompanySearchDto.roleId*/
	/*END*/	
/*END*/
 /*IF orders != null*/
ORDER BY /*$orders*/username
-- ELSE ORDER BY rolecompany.UPDATED_DATE DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/