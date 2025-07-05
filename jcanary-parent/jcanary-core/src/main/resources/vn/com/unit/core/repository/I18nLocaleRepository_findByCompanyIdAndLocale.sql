SELECT 
	* 
FROM  
	I18N_LOCALE i18n_loc
WHERE
	i18n_loc.DELETED_ID = 0
	
	/*IF companyId >= 0*/
	AND i18n_loc.COMPANY_ID = /*companyId*/
	/*END*/
		
	/*IF locale != null && locale != ''*/
	AND i18n_loc.LOCALE = /*locale*/ 
	/*END*/	