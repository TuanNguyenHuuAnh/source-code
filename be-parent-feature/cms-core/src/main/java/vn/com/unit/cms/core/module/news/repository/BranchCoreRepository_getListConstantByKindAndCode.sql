SELECT CODE AS ID, CODE, NAME
FROM JCA_CONSTANT
WHERE KIND = /*kind*/'MENU_ICON'
/* IF code != null && code != ''*/
AND CODE = /*code*/'fa fa-bar-chart'
/*END*/
AND ISNULL(CAN_DELETE, 0) = 0
AND ACTIVED = 1
AND LANG_CODE = /*language*/'VI'
ORDER BY DISPLAY_ORDER ASC
