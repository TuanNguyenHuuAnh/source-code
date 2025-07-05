SELECT 
    FUNCTION_CODE                              					AS  ID
    , FUNCTION_NAME                            					AS  NAME
    , CONCAT(FUNCTION_CODE, ' - ', FUNCTION_NAME  )				AS  TEXT
FROM JCA_ITEM
WHERE 
	ACTIVED = 1
	AND DELETED_DATE IS NULL
	AND COMPANY_ID = /*companyId*/2