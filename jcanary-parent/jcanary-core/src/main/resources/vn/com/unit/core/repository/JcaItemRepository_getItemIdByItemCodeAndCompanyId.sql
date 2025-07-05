SELECT
	item.ID
FROM 
	JCA_ITEM item
WHERE 
	item.DELETED_ID = 0
	AND UPPER(item.FUNCTION_CODE) = UPPER(/*item*/'')
	/*IF companyId != null*/
		AND COMPANY_ID = /*companyId*/1
	/*END*/