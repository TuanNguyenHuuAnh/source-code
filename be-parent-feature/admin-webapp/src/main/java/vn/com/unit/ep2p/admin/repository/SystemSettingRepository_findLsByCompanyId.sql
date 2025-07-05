--
-- SystemSettingRepository_findLsByCompanyId.sql

SELECT
    *
FROM
    JCA_SYSTEM_SETTING 
WHERE
	1 = 1
    /*IF companyId != null*/
		AND company_id = /*companyId*/1
	/*END*/
