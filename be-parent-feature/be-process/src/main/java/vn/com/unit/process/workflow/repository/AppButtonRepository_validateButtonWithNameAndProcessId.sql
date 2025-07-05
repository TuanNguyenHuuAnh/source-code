SELECT  CASE WHEN COUNT(*) > 0 THEN 0 ELSE 1 END
FROM    JPM_BUTTON
WHERE   PROCESS_ID = /*processId*/
AND     BUTTON_TEXT = /*buttonName*/
AND     (/*buttonId*/ IS NULL OR ID != /*buttonId*/)
AND 	DELETED_ID = 0