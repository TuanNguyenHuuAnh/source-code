SELECT PATH_CLASS AS id,
		NAME_CLASS AS name,
       	NAME_CLASS AS text
FROM
    QRTZ_M_JOB_CLASS 
WHERE 
	DELETED_ID = 0 
	/*IF id != null*/
	and JOB_TYPE_ID = /*id*/0 
	/*END*/ 
	/*IF term != null && term != ''*/ 
	and UPPER(NAME_CLASS) LIKE CONCAT(CONCAT('%',UPPER(/*term*/'')),'%') 
	/*END*/ 
ORDER BY id ASC
OFFSET 0 ROWS FETCH NEXT 20 ROWS ONLY