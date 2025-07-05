SELECT
    *    
FROM
    jca_language
WHERE DELETED_ID = 0
	AND UPPER(CODE) = UPPER(/*lang*/'')
