SELECT
    *    
FROM
    JCA_SYSTEM_SETTING ss
WHERE
    1 = 1
    /*IF key != null && key != ''*/
	AND ss.setting_key = /*key*/
	/*END*/