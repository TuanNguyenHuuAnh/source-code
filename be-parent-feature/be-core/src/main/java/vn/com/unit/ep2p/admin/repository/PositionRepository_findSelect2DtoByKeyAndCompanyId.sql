SELECT pos.ID           AS id,
	   pos.NAME         AS text,
	   pos.NAME         AS name
FROM
	jca_position_path       main
    LEFT JOIN jca_position            pos 
        ON main.descendant_id = pos.id
WHERE 
	pos.DELETED_ID = 0
	AND main.depth = 1
	AND main.ANCESTOR_ID > 1
	/*IF !companyAdmin*/
	AND (pos.COMPANY_ID = /*companyId*/1
	 OR pos.COMPANY_ID IS NULL )
	 /*END*/
	AND pos.ACTIVED  = 1
	/*IF keySearch !=null && keySearch !=''*/
	AND UPPER(pos.NAME) LIKE CONCAT(CONCAT('%', UPPER(/*keySearch*/'')), '%')
	/*END*/
ORDER BY 
	pos.NAME
/*IF isPaging*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/