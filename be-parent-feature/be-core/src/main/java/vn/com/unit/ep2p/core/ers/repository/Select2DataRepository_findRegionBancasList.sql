SELECT
    CODE                                            AS  ID
    , NAME                                          AS  NAME
    , NAME                                          AS  TEXT
FROM JCA_CONSTANT 
WHERE 
    GROUP_CODE = 'JCA_BANCAS_REGION' 
    AND KIND = 'AREA'
    AND LANG_CODE = 'VI'