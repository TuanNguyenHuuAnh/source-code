SELECT 
    CODE                                                        AS  ID
    , NAME                                                      AS  NAME
    , CONCAT(CODE, ' - ', NAME)                                 AS  TEXT
FROM JCA_CONSTANT WITH (NOLOCK)
WHERE
    GROUP_CODE = 'JCA_BANK_BRANCH'
    AND KIND = 'BANK_BRANCH'
    AND LANG_CODE = /*lang*/'EN'
    AND CODE LIKE CONCAT(/*province*/'00000','%')