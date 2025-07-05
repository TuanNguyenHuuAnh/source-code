SELECT a.ACT_CODE AS ACT_CODE
FROM SAM_ACTIVITY a
WHERE 1 = 1
/*IF createBy != null && createBy != ''*/
	AND a.CREATED_BY = /*createBy*/'ntr.bang'
/*END*/
ORDER BY a.ID DESC
OFFSET 0 ROWS FETCH NEXT  /*number*/'5' ROWS ONLY