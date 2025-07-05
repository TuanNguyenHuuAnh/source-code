SELECT 
    DISTRICT                                        AS ID
    , NAME                                          AS NAME
    , CONCAT(DISTRICT, ' - ', NAME)                 AS  TEXT
FROM ERS_ZIPCODE WITH (NOLOCK)
WHERE 
    PROVINCE = /*province*/'' 
    AND DISTRICT <> 0 
    AND WARD = '00000'
ORDER BY Name ASC