SELECT
	ITEM.*
FROM jca_company ITEM
WHERE ITEM.DELETED_ID = 0
/*BEGIN*/
	AND (
		/*IF search.name != null && search.name != ''*/
			UPPER(ITEM.NAME) LIKE concat(concat('%',  UPPER(/*search.name*/'')), '%')
		/*END*/
		/*IF search.description != null && search.description != ''*/
			OR UPPER(ITEM.DESCRIPTION) LIKE concat(concat('%',  UPPER(/*search.description*/'')), '%')
		/*END*/
		/*IF search.systemCode != null && search.systemCode != ''*/
			OR UPPER(ITEM.SYSTEM_CODE) LIKE concat(concat('%',  UPPER(/*search.systemCode*/'')), '%')
		/*END*/
		/*IF search.systemName != null && search.systemName != ''*/
			OR UPPER(ITEM.SYSTEM_NAME) LIKE concat(concat('%',  UPPER(/*search.systemName*/'')), '%')
		/*END*/
	)
/*END*/
ORDER BY ITEM.ID
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY