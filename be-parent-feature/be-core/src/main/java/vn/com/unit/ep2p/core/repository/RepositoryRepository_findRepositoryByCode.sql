SELECT
	*
FROM
	jca_m_repository repository			
WHERE
	repository.DELETED_BY IS NULL	
	AND repository.code = /*code*/
	/*IF id != null */
	AND repository.id <> /*id*/
	/*END*/