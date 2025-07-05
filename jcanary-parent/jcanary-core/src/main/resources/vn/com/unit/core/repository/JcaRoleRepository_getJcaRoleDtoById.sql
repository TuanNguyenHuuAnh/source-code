SELECT 
  role.ID										AS ROLE_ID
  ,role.NAME									AS NAME	
  ,role.CODE									AS CODE
  ,role.ACTIVE									AS ACTIVE
  ,role.DESCRIPTION								AS DESCRIPTION
  ,role.COMPANY_ID								AS COMPANY_ID
  ,role.CREATED_ID								AS CREATED_ID
  ,role.CREATED_DATE							AS CREATE_DATE
  ,role.UPDATED_ID								AS UPDATED_ID
  ,role.UPDATED_DATE							AS UPDATED_DATE
  ,role.DELETED_ID								AS DELETED_ID
  ,role.DELETED_DATE							AS DELETED_DATE
FROM JCA_ROLE role
WHERE
  role.DELETED_ID = 0
  AND
  	role.ID = /*id*/
  