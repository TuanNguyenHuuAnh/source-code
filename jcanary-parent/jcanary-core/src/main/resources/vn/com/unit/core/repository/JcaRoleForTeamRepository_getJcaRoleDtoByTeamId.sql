SELECT 
  	role.ID     			AS ID
  	,role.CODE     			AS CODE
  	,role.NAME     			AS NAME
  	,role.DESCRIPTION     	AS DESCRIPTION
  	,role.COMPANY_ID     	AS COMPANY_ID
  	,role.ACTIVE     		AS ACTIVE
FROM
  	JCA_ROLE role
LEFT JOIN
    JCA_ROLE_FOR_TEAM role_team
ON
    role_team.ROLE_ID = role.ID
    AND role_team.DELETED_ID = 0
    /*IF companyId != null */
	AND role_team.COMPANY_ID = /*companyId*/
	/*END*/	
    
WHERE
  	role.DELETED_ID = 0
  	AND role.ACTIVE = 1
    AND role_team.TEAM_ID = /*teamId*/
    
    /*IF companyId != null */
	AND role.COMPANY_ID = /*companyId*/
	/*END*/	
