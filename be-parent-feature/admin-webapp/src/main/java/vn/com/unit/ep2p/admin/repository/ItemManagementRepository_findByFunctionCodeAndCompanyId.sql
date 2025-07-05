SELECT
	item.id as item_id
	, item.*
FROM
	JCA_ITEM item
WHERE
	item.DELETED_ID = 0
	AND UPPER (item.FUNCTION_CODE) = UPPER (/*functionCode*/)
	/*IF companyId != null*/
	AND (item.company_id is null or item.company_id = /*companyId*/1)
	/*END*/