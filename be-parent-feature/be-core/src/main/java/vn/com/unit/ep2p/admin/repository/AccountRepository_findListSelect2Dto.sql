SELECT ID as id
	   , USERNAME as name
	   , FULLNAME as text
FROM JCA_M_ACCOUNT
WHERE  
	DELETED_ID = 0 
	AND ENABLED = 1 
	/*IF key!= null && key != ''*/
	AND FN_CONVERT_TO_VN(UPPER(FULLNAME)) like CONCAT(CONCAT('%', FN_CONVERT_TO_VN(UPPER(/*key*/''))), '%')
	/*END*/
	/*IF companyId != null*/
	AND COMPANY_ID = /*companyId*/0
	/*END*/ 
	ORDER BY CREATED_DATE DESC 
	/*IF isPaging == true*/
	OFFSET 0 ROWS FETCH NEXT 60 ROWS ONLY
	/*END*/