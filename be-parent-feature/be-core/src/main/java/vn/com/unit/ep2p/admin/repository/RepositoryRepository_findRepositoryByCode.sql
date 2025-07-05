SELECT
	*
FROM
	jca_repository repository			
WHERE
	repository.DELETED_ID IS NULL	
	AND repository.code = /*code*/
	/*IF id != null */
	AND repository.id <> /*id*/
	/*END*/