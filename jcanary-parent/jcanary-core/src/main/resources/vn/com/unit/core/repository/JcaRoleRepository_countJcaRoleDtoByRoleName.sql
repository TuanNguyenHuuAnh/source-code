SELECT 
	COUNT(1)
FROM 
	JCA_ROLE role
WHERE
  	role.DELETED_ID = 0
  	AND role.NAME = /*roleName*/
  
 	/*IF roleId != null */
	AND role.ID  != /*roleId*/
	/*END*/	
  