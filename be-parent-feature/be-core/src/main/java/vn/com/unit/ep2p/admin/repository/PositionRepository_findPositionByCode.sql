SELECT
	*
FROM
	jca_position position			
WHERE
	position.DELETED_ID = 0	
	AND position.code = /*code*/
	/*IF id != null */
	AND position.id <> /*id*/
	/*END*/