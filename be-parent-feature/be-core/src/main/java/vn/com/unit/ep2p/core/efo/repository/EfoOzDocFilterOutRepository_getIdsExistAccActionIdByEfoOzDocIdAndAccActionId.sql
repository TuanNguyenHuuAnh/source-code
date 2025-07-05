SELECT
 taskOut.ID
FROM
	 EFO_OZ_DOC_FILTER_OUT taskOut
WHERE
	taskOut.DELETED_ID = 0
	AND
		EFO_OZ_DOC_ID = /*efoOzDocId*/1
  	AND
	   taskOut.OWNER_ID = /*accActionId*/1