WITH TBL_TMP AS (
	SELECT
		status.STATUS_CODE																					AS	id
		, statusLang.STATUS_NAME																			AS	NAME
		, CONCAT(status.STATUS_CODE, ' - ', statusLang.STATUS_NAME)											AS	TEXT
	FROM JPM_STATUS_LANG_DEPLOY statusLang
	INNER JOIN JPM_STATUS_DEPLOY status
		ON status.ID = statusLang.STATUS_DEPLOY_ID
	INNER JOIN JPM_PROCESS_DEPLOY process
		ON process.ID = status.PROCESS_DEPLOY_ID
	INNER JOIN JPM_BUSINESS bus
		ON bus.ID = process.BUSINESS_ID
		AND bus.COMPANY_ID = process.COMPANY_ID
		AND bus.BUSINESS_CODE = /*businessCode*/'BUSINESS_CMS'
	WHERE
		status.DELETED_DATE IS NULL
		AND statusLang.LANG_CODE = UPPER(/*languageCode*/'VI')
		AND bus.COMPANY_ID = /*companyId*/2
		
	UNION ALL 

	SELECT 
		status.STATUS_CODE																					AS	id
		, statusLang.STATUS_NAME																			AS	NAME
		, CONCAT(status.STATUS_CODE, ' - ', statusLang.STATUS_NAME)											AS	TEXT
	FROM JPM_STATUS_COMMON status
	INNER JOIN JPM_STATUS_COMMON_LANG statusLang
		ON statusLang.STATUS_COMMON_ID = status.ID
	WHERE 
		status.STATUS_CODE = '000'
		AND statusLang.LANG_CODE = UPPER(/*languageCode*/'VI')
)
SELECT DISTINCT * FROM TBL_TMP
ORDER BY id ASC, NAME DESC