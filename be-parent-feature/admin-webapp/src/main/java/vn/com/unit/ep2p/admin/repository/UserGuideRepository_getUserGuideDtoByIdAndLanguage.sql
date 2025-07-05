SELECT userG.*
FROM
	JCA_USER_GUIDE userG
WHERE
	DELETED_ID = 0
	/*IF id != null */
	AND	userG.id = /*id*/
	/*END*/