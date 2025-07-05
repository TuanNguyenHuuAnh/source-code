--
-- SystemSettingRepository_findKeyAndCompanyId.sql

SELECT
    *    
FROM
    JCA_SYSTEM_SETTING ss
WHERE
	-- ss.DELETED_ID = 0
	1 = 1
    /*IF companyId != null*/
		AND ss.company_id = /*companyId*/1
	--ELSE AND ss.company_id IS NULL
	/*END*/
	/*IF key != null && key != ''*/
		AND ss.setting_key = /*key*/
	/*END*/