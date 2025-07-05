SELECT
	DOC.ID 			AS document_id
	,DOC.DOC_NAME	AS document_name
	,DOC.CREATED_BY	AS created_by
	,DOC.CREATED_DATE AS created_date
	,DOC.DOC_TITLE	AS document_summary
	,DOC.ECM_REPOSITORY_ID AS ozd_file_name_repo_id
	,DOC.FORM_FILE_NAME 	AS ozd_file_name
	,lang.STATUS_NAME		AS status
  	,jpm_status.UPDATED_DATE	AS statusDate
FROM
	EFO_OZ_DOC DOC
INNER JOIN
	jca_company CP
ON  CP.ID = DOC.COMPANY_ID
INNER JOIN
	EFO_CATEGORY CA
ON	CA.COMPANY_ID = CP.ID
LEFT JOIN
	JPM_Status_DEPLOY jpm_status
ON
	jpm_status.PROCESS_ID = DOC.PROCESS_INSTANCE_ID AND jpm_status.STATUS_CODE = DOC.PROCESS_STATUS_CODE
LEFT JOIN JPM_STATUS_LANG_DEPLOY lang
ON 
    lang.STATUS_ID = jpm_status.ID AND UPPER(lang.LANG_CODE) = UPPER(/*langCode*/'')
WHERE
	DOC.DELETED_ID = 0
	AND DOC.COMPANY_ID IN /*companyList*/()
	
	/*IF keySearch != null && keySearch != ''*/
	AND UPPER(DOC.DOC_NAME) LIKE concat(concat('%',  UPPER(/*keySearch*/)), '%') 
	/*END*/

	/*IF companyId != null*/
	AND DOC.COMPANY_ID = /*companyId*/
	/*END*/
	
	/*IF categoryId != null*/
	AND CA.ID = /*categoryId*/
	/*END*/
ORDER BY
	DOC.COMPANY_ID,CA.ID,DOC.ID
/*IF pageSize!=null*/
		OFFSET /*page*/ ROWS FETCH NEXT  /*pageSize*/ ROWS ONLY
/*END*/