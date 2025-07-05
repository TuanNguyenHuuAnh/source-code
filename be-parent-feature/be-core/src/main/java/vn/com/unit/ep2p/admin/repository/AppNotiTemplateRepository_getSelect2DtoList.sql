SELECT 
	ID 		AS ID
	,NAME 	AS NAME
	,NAME 	AS TEXT
FROM
    JCA_NOTI_TEMPLATE 
WHERE 
	DELETED_ID = 0
	/*IF keySearch!= null && keySearch != ''*/
		AND UPPER(NAME) like CONCAT('%',CONCAT(/*keySearch*/'','%'))
	/*END*/
	/*IF companyId != null*/
		AND COMPANY_ID = /*companyId*/
	/*END*/ 
ORDER BY 
	CREATED_DATE DESC 
/*IF isPaging*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/