SELECT
   statusLang.STATUS_NAME AS STATUS_NAME,
   statusCommon.*
FROM
   JPM_STATUS_COMMON statusCommon
LEFT
JOIN JPM_STATUS_COMMON_LANG statusLang ON statusCommon.ID = statusLang.STATUS_COMMON_ID
AND
   statusLang.LANG_CODE = UPPER (/*lang*/'VI')
WHERE
   statusCommon.DELETED_ID = 0