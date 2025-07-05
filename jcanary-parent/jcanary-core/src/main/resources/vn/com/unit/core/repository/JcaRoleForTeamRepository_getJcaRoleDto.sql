SELECT 
  	role.ID     			AS ROLE_ID
  	,role.CODE     			AS CODE
  	,role.NAME     			AS NAME
  	,role.DESCRIPTION     	AS DESCRIPTION
  	,role.COMPANY_ID     	AS COMPANY_ID
  	,role.ACTIVE     		AS ACTIVE
FROM
  	JCA_ROLE role
WHERE
  	role.DELETED_ID = 0
  	AND role.ACTIVE = 1
  	/*IF jcaRoleForTeamSearchDto.companyId != null*/
	AND role.COMPANY_ID = /*jcaRoleForTeamSearchDto.companyId*/
	/*END*/