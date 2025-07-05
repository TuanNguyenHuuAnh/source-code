SELECT 
	statusLang.LANG_ID	  AS LANG_ID
	, statusLang.LANG_CODE     	  AS LANG_CODE
	, statusLang.STATUS_NAME      AS STATUS_NAME
	, statusLang.STATUS_ID        AS STATUS_ID
FROM
	JPM_STATUS status
LEFT JOIN
	JPM_STATUS_LANG statusLang
ON
	status.ID = statusLang.STATUS_ID
WHERE
	status.DELETED_ID = 0
	AND statusLang.STATUS_ID = /*statusId*/