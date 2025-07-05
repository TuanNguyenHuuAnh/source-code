SELECT *
FROM
	/*$tableName*/

WHERE 
	SESSION_KEY = /*importId*/
	ORDER BY ID
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY ;