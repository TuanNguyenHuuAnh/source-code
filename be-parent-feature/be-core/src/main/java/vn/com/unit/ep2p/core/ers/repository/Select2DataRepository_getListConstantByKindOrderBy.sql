SELECT 
    CODE                                                        AS  ID
    , NAME                                                      AS  NAME
    , CONCAT(CODE, ' - ', NAME)                                 AS  TEXT
FROM JCA_CONSTANT WITH (NOLOCK)
WHERE
    GROUP_CODE = /*groupCode*/''
    AND KIND = /*kind*/
    
    /*IF code != null && code != ''*/
    	AND CODE = /*code*/
    /*END*/
AND ACTIVED = 1
AND LANG_CODE = /*lang*/'EN'
ORDER BY DISPLAY_ORDER ASC