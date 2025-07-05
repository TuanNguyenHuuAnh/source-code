SELECT 
	tracking.*
FROM 
	EFO_OZ_DOC_FILTER_TRACKING tracking
WHERE
	tracking.ACCOUNT_ID = /*accountId*/
	AND tracking.EFO_OZ_DOC_ID = /*efoOzDocId*/
	AND tracking.STEP_CODE = /*stepCode*/
	AND tracking.TYPE_TRACKING = /*typeTracking*/