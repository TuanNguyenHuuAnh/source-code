SELECT
		 form.ID            	AS   FORM_ID
	,form.COMPANY_ID    	AS   COMPANY_ID
	,form.CATEGORY_ID   	AS   CATEGORY_ID
	,form.BUSINESS_ID 	AS   BUSINESS_ID
	,form.NAME          	AS   FORM_NAME
	,form.DESCRIPTION   	AS   DESCRIPTION
	,form.OZ_FILE_PATH     	AS   OZ_FILE_PATH
	,form.ICON_FILE_PATH         	AS   ICON_FILE_PATH
	,form.ICON_REPO_ID 	AS   ICON_REPO_ID
	,form.DISPLAY_ORDER 	AS   DISPLAY_ORDER
	,form.DEVICE_TYPE   	AS   DEVICE_TYPE
	,form.ACTIVED   	AS   ACTIVED
	,form.BUSINESS_ID	AS	BUSINESS_ID
	,form.FORM_TYPE			AS  FORM_TYPE
	,form.OZ_APPEND_FILE_PATH AS	OZ_APPEND_FILE_PATH
	,form.CREATED_DATE      AS CREATED_DATE
FROM EFO_FORM form
WHERE form.DELETED_ID = 0
/*IF companyId != null*/
	AND form.COMPANY_ID = /*companyId*/1
/*END*/
/*IF fileNameList != null && fileNameList.size() > 0*/
	AND UPPER(form.OZ_FILE_PATH) IN /*fileNameList*/()
/*END*/