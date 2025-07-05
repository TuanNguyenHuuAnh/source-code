SELECT 
  count(1)
FROM JCA_ROLE role
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
	AND role.ACTIVE = /*jcaRoleSearchDto.active*/
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
 ;