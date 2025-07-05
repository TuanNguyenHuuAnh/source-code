SELECT 
	jtype.ID AS id,
	jtype.CODE AS name,
    jtype.OFFICIAL_NAME AS text 
FROM
    QRTZ_M_JOB_TYPE jtype
WHERE
    jtype.DELETED_ID = 0
	AND jtype.type = 'JOB_TYPE'
ORDER BY 
    jtype.ID ASC;