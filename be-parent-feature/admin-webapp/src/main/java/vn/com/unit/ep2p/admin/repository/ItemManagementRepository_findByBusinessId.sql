SELECT
	/*IF functionCodeAsId == true*/
	IT.FUNCTION_CODE id,
	/*END*/
	/*IF functionCodeAsId == false*/
	IT.ID id,
	/*END*/
    IT.FUNCTION_NAME AS name,
    IT.FUNCTION_NAME AS text
FROM JCA_ITEM IT
WHERE IT.DELETED_ID = 0
	AND IT.FUNCTION_TYPE = 3 
/*IF keySearch != null && keySearch != ''*/
	AND IT.FUNCTION_NAME like CONCAT(CONCAT('%',  /*keySearch*/''), '%')
/*END*/
/*IF companyId != null*/
	AND IT.COMPANY_ID = /*companyId*/1
/*END*/

ORDER BY IT.DISPLAY_ORDER, IT.FUNCTION_NAME
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/