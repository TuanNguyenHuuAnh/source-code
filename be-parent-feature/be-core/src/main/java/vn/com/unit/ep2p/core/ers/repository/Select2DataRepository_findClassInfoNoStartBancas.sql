SELECT DISTINCT 
    CLASS_CODE                              AS  ID
    , CLASS_CODE                                   AS  NAME
    , CLASS_CODE             						AS  TEXT 
FROM ERS_CLASS_INFO 
WHERE DELETED_FLAG = 0 AND CHANNEL = 'Bancas' AND START_DATE >= GETDATE()