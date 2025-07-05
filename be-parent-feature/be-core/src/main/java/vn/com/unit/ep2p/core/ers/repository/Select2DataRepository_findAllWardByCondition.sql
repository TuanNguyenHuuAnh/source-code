SELECT 
    WARD                                     AS ID
    , NAME                                   AS NAME
    , CONCAT(WARD, ' - ', NAME)              AS  TEXT
FROM ERS_ZIPCODE WITH (NOLOCK)
WHERE 
    PROVINCE =  /*province*/''
    AND DISTRICT = /*district*/''
    AND WARD <> '00000'
ORDER BY Name ASC