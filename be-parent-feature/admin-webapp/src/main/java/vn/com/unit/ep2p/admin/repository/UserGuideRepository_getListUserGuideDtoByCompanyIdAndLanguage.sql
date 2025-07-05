SELECT userG.*
FROM
	JCA_USER_GUIDE userG
WHERE
	userG.DELETED_ID = 0
	/*IF companyId != null */
	AND	userG.COMPANY_ID = /*companyId*/
	/*END*/
	/*IF codes != null */
	AND UPPER(userG.LANG_CODE) IN /*codes*/()
	/*END*/
	/*IF appCode != null && appCode!= ''*/
	AND UPPER(userG.APP_CODE) = /*appCode*/
	/*END*/
	