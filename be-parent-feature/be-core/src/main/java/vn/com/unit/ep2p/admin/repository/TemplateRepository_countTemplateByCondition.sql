select count(*)
from JCA_EMAIL_TEMPLATE template 
    LEFT JOIN jca_company co ON template.company_id = co.id and co.DELETED_ID = 0
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