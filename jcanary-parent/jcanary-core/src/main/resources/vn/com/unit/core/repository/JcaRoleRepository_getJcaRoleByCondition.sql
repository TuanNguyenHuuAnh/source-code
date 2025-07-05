SELECT 
  role.ID										AS ROLE_ID
  ,role.NAME									AS NAME	
  ,role.CODE									AS CODE
  ,role.ACTIVED									AS ACTIVE
  ,role.DESCRIPTION								AS DESCRIPTION
  ,role.COMPANY_ID								AS COMPANY_ID
  ,role.CREATED_ID								AS CREATED_ID
  ,role.CREATED_DATE							AS CREATE_DATE
  ,role.UPDATED_ID								AS UPDATED_ID
  ,role.UPDATED_DATE							AS UPDATED_DATE
  ,role.DELETED_ID								AS DELETED_ID
  ,role.DELETED_DATE							AS DELETED_DATE
  ,com.NAME										AS COMPANY_NAME
FROM JCA_ROLE role 
LEFT JOIN JCA_COMPANY com
ON role.COMPANY_ID = com.ID
WHERE
  role.DELETED_ID = 0
 /*BEGIN*/
	AND 
		(
			/*IF jcaRoleSearchDto.name != null && jcaRoleSearchDto.name != ''*/
			OR UPPER(role.NAME) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaRoleSearchDto.name*/), '%' ))
			/*END*/
			
			/*IF jcaRoleSearchDto.code != null && jcaRoleSearchDto.code != ''*/
			OR UPPER(role.CODE) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaRoleSearchDto.code*/), '%' ))
			/*END*/
			
			/*IF jcaRoleSearchDto.description != null && jcaRoleSearchDto.description != ''*/
			OR UPPER(role.DESCRIPTION) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaRoleSearchDto.description*/), '%' ))
			/*END*/
			
		)
	/*END*/
		/*IF jcaRoleSearchDto.active != null */
	AND role.ACTIVED = /*jcaRoleSearchDto.active*/
	/*END*/	
	
	/*END*/
	/*IF jcaRoleSearchDto.companyId == null*/
	AND role.COMPANY_ID IS NULL
	/*END*/
		/*IF jcaRoleSearchDto.companyId != null && jcaRoleSearchDto.companyId != 0*/
	AND role.COMPANY_ID = /*jcaRoleSearchDto.companyId*/
	/*END*/
	/*IF jcaRoleSearchDto.companyId != null && jcaRoleSearchDto.companyId == 0*/
	AND role.COMPANY_ID IN /*jcaRoleSearchDto.companyIdList*/()
	/*END*/	
	
 /*IF orders != null*/
ORDER BY /*$orders*/username
-- ELSE ORDER BY role.UPDATED_DATE DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/