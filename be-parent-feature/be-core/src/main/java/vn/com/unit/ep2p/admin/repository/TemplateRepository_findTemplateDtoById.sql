SELECT 
	template.ID 				
	,template.ID 				AS TEMPLATE_ID
	,template.TEMPLATE_NAME
	,template.FILE_FORMAT
	,template.FILE_SIZE
	,template.URL
	,template.FILE_NAME
	,template.ACTIVED
	,template.REPOSITORY_ID
	,template.COMPANY_ID
	,template.CREATED_DATE
	,template.CREATED_BY
	,template.DELETED_DATE
	,template.DELETED_BY
	,template.CODE
	,template.TEMPLATE_CONTENT
	
	,templateLang.SUBJECT
	,templateLang.ID				AS TEMPLATE_LANG_ID
	,templateLang.MOBILE_NOTIFICATION
	,templateLang.LANG_CODE

FROM 
	JCA_EMAIL_TEMPLATE template
LEFT JOIN JCA_EMAIL_TEMPLATE_LANG templateLang
ON 
	templateLang.TEMPLATE_ID = template.ID
WHERE 
	template.DELETED_ID = 0
	/* IF id != null*/
	AND template.ID = /*id*/0
	/*END*/ 