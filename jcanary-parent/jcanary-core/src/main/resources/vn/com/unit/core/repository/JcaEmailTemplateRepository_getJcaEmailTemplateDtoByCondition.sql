SELECT 
	email.ID							AS ID
	, email.NAME						AS NAME
	, email.CODE						AS CODE
	, email.TEMPLATE_CONTENT			AS TEMPLATE_CONTENT
	, email.TEMPLATE_SUBJECT			AS TEMPLATE_SUBJECT
	, email.COMPANY_ID					AS COMPANY_ID
	, com.NAME							AS COMPANY_NAME
	, email.CREATED_DATE				AS CREATED_DATE
	, email.CREATED_ID					AS CREATED_ID
	, acc.USERNAME						AS CREATED_BY
    , email.UPDATED_DATE                AS UPDATED_DATE
    , email.UPDATED_ID                  AS UPDATED_ID
    , accUpdate.USERNAME                AS UPDATED_BY
FROM JCA_EMAIL_TEMPLATE email WITH (NOLOCK)
LEFT JOIN JCA_COMPANY com WITH (NOLOCK)
    ON com.ID = email.COMPANY_ID
LEFT JOIN JCA_ACCOUNT acc WITH (NOLOCK)
    ON acc.ID = email.CREATED_ID
LEFT JOIN JCA_ACCOUNT accUpdate WITH (NOLOCK)
    ON accUpdate.ID = email.UPDATED_ID
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
	
/*IF orders != null*/
ORDER BY /*$orders*/
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/	
	
	