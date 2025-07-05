UPDATE
	EFO_OZ_DOC_FILTER_TRACKING tracking
SET
	tracking.DEL_FLAG = /*isDelete*/
WHERE 
	tracking.ID = /*id*/