SELECT 
    CODE                              AS  ID
    , NAME                            AS  NAME
    , CONCAT(CODE, ' - ', NAME  )     AS  TEXT
FROM JCA_CONSTANT 
WHERE 
    GROUP_CODE = 'JCA_ACCOUNT'
    AND KIND = 'QUESTION'
    AND LANG_CODE = /*lang*/'VI'