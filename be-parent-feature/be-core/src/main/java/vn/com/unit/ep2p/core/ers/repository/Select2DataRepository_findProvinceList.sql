SELECT DISTINCT 
    PROVINCE                                    AS  ID
    , NAME                                      AS  NAME
    , CONCAT(PROVINCE, ' - ', NAME)             AS  TEXT
FROM dbo.ERS_ZIPCODE WITH (NOLOCK)
WHERE DISTRICT = '000' AND WARD = '00000' 
ORDER BY NAME