SELECT DISTINCT 
	rol.ID 			AS ID
	, rol.CODE 		AS NAME
	, rol.NAME 		AS TEXT
FROM JCA_ROLE rol 
LEFT JOIN VW_GET_USER_ROLE vie ON vie.ROLE_ID = rol.ID 
LEFT JOIN JCA_AUTHORITY author ON author.ROLE_ID = rol.ID  
LEFT JOIN JCA_ITEM item ON item.ID = author.ITEM_ID 
WHERE 
	rol.DELETED_ID = 0 
	AND vie.ASSIGN_TYPE = 'M' 
    AND item.FUNCTION_TYPE <> 3 
	AND vie.ACCOUNT_ID = /*accountId*/1802 
    /*IF companyId != null*/
	AND rol.COMPANY_ID = /*companyId*/220
	/*END*/ 
	/*IF key != null && key != ''*/
	AND UPPER(rol.NAME) LIKE CONCAT(CONCAT('%',UPPER(/*key*/'')),'%') 
	/*END*/ 
    /*IF isPaging == true*/
	OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
	/*END*/