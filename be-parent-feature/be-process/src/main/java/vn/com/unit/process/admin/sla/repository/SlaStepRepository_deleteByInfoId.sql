UPDATE SLA_STEP
SET DELETED_BY = /*deletedBy*/,
	DELETED_DATE = /*deletedDate*/
WHERE 
	/*IF id != null*/
		SLA_INFO_ID = /*id*/
	/*END*/