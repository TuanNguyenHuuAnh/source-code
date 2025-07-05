select 
  template.ID 				AS ID
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
  	,templateLang.MOBILE_NOTIFICATION
	,templateLang.LANG_CODE
  
	,template.id 					as template_id
	,co.name 						as company_name
	
from JCA_EMAIL_TEMPLATE template 
LEFT JOIN jca_company co
ON
	template.company_id = co.id and co.DELETED_ID = 0
LEFT JOIN JCA_EMAIL_TEMPLATE_LANG templateLang
ON 
	templateLang.TEMPLATE_ID = template.ID and UPPER(templateLang.LANG_CODE) = UPPER(/*langCode*/)
WHERE
    template.actived = 1
    /*IF searchDto.companyId != null && searchDto.companyId != 0*/
	AND template.company_id = /*searchDto.companyId*/1
	/*END*/
	/*IF searchDto.companyId == null*/
	AND template.company_id is null
	/*END*/
	/*IF searchDto.companyId == 0 && !searchDto.companyAdmin*/
	AND (template.company_id  IN /*searchDto.companyIdList*/()
	OR template.company_id IS NULL)
	/*END*/
	/*BEGIN*/
	AND (
	/*IF searchDto.templateName != null && searchDto.templateName != ''*/
	OR UPPER(template.template_name) LIKE concat(concat('%',  UPPER(/*searchDto.templateName*/)), '%')
	/*END*/
	/*IF searchDto.createdBy != null && searchDto.createdBy != ''*/
	OR UPPER(template.CREATED_BY) LIKE concat(concat('%',  UPPER(/*searchDto.createdBy*/)), '%')
	/*END*/
	/*IF searchDto.fileName != null && searchDto.fileName != ''*/
	OR UPPER(template.file_name) LIKE concat(concat('%',  UPPER(/*searchDto.fileName*/)), '%')
	/*END*/
	)
	/*END*/
	/*IF searchDto.fromDate != null */
	AND CONVERT(date, /*searchDto.fromDate*/) <= CONVERT(date, template.CREATED_DATE)
	/*END*/
	/*IF searchDto.toDate != null */
	AND CONVERT(date, /*searchDto.toDate*/) >= CONVERT(date, template.CREATED_DATE)
	/*END*/	
ORDER BY template.company_id, template.CREATED_DATE DESC, template.TEMPLATE_NAME

OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY