SELECT
	item.ID AS ITEM_ID,
	item.*
FROM
	JCA_ITEM item
WHERE
	DELETED_ID = 0 
	AND item.ID = /*id*/
	