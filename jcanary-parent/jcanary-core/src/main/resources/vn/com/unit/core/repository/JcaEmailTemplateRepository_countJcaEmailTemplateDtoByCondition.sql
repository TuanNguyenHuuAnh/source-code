SELECT 
	COUNT(1) 
FROM  
	JCA_EMAIL_TEMPLATE email
LEFT JOIN
	JCA_ACCOUNT acc
ON
	email.CREATED_ID = acc.ID
LEFT JOIN
	JCA_COMPANY com
ON
	com.ID = email.COMPANY_ID
WHERE
	email.DELETED_ID = 0
	
	/*BEGIN*/
	AND  (
			/*IF jcaEmailTemplateSearchDto.templateName != null && jcaEmailTemplateSearchDto.templateName != ''*/
			OR UPPER(RTRIM(LTRIM(email.NAME))) LIKE CONCAT( '%', CONCAT(UPPER(RTRIM(LTRIM(/*jcaEmailTemplateSearchDto.templateName*/))), '%' ))
			/*END*/
			
			/*IF jcaEmailTemplateSearchDto.code != null && jcaEmailTemplateSearchDto.code != ''*/
			OR UPPER(RTRIM(LTRIM(email.CODE))) LIKE CONCAT( '%', CONCAT(UPPER(RTRIM(LTRIM(/*jcaEmailTemplateSearchDto.code*/))), '%' ))
			/*END*/
			
			/*IF jcaEmailTemplateSearchDto.createdBy != null && jcaEmailTemplateSearchDto.createdBy != ''*/
			OR UPPER(RTRIM(LTRIM(acc.USERNAME))) LIKE CONCAT( '%', CONCAT(UPPER(RTRIM(LTRIM(/*jcaEmailTemplateSearchDto.createdBy*/))), '%' ))
			/*END*/
		)
	/*END*/
		
	/*IF jcaEmailTemplateSearchDto.companyId != null &&  jcaEmailTemplateSearchDto.companyId != ''*/
	AND email.COMPANY_ID = /*jcaEmailTemplateSearchDto.companyId*/
	/*END*/	