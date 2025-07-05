SELECT 	*
FROM QRTZ_M_JOB_SCHEDULE
WHERE DELETED_DATE IS NULL

/*IF orders != null*/
ORDER BY /*$orders*/ID
-- ELSE ORDER BY ID DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/
;
