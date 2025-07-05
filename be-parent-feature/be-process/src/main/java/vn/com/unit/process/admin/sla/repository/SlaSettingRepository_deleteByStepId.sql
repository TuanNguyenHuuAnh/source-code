UPDATE SLA_SETTING
SET DELETED_BY = /*deletedBy*/,
	DELETED_DATE = /*deletedDate*/
WHERE
	/*IF id != null*/
		ID = /*id*/1
	/*END*/