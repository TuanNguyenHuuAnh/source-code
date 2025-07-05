SELECT 
  temp_lang.ID                  	AS ID
  ,temp_lang.EMAIL_TEMPLATE_ID  	AS EMAIL_TEMPLATE_ID
  ,temp_lang.TITLE            		AS TITLE
  ,temp_lang.LANG_CODE          	AS LANG_CODE
  ,temp_lang.NOTIFICATION          	AS NOTIFICATION
FROM
  JCA_EMAIL_TEMPLATE_LANG temp_lang
WHERE
  temp_lang.DELETED_ID = 0
  AND temp_lang.EMAIL_TEMPLATE_ID = /*emailTemplateId*/
