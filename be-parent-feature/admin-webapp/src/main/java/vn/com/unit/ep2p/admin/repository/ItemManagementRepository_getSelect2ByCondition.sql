SELECT
	IT.ID  AS ID,
    IT.FUNCTION_NAME AS name,
    IT.FUNCTION_NAME AS text
FROM JCA_ITEM IT
WHERE IT.DELETED_ID = 0
	AND IT.FUNCTION_TYPE IN /*functionTypes*/()
	AND IT.ACTIVED = 1
/*IF keySearch != null && keySearch != ''*/
	AND UPPER(IT.FUNCTION_NAME) like UPPER(CONCAT(CONCAT('%',  /*keySearch*/''), '%'))
/*END*/
/*IF companyId != null && companyId!=''*/
	AND (IT.COMPANY_ID = /*companyId*/1 OR IT.COMPANY_ID IS NULL)
/*END*/
ORDER BY IT.FUNCTION_NAME
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/