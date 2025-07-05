SELECT
	COUNT(1)
FROM 
	JCA_ITEM item
WHERE 
	item.DELETED_ID = 0
	AND item.FUNCTION_CODE = /*functionCode*/