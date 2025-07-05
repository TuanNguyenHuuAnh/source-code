SELECT 
      SETTING.SETTING_KEY                      AS SETTING_KEY
      ,SETTING.SETTING_VALUE                    AS SETTING_VALUE
      ,SETTING.COMPANY_ID                       AS COMPANY_ID
      ,SETTING.GROUP_CODE                         AS GROUP_CODE
      ,SETTING.CREATED_DATE                     AS CREATED_DATE
      ,SETTING.CREATED_ID                       AS CREATED_ID
      ,SETTING.UPDATED_DATE                     AS UPDATED_DATE      
      ,SETTING.UPDATED_ID                       AS UPDATED_ID

FROM JCA_SYSTEM_SETTING SETTING
WHERE SETTING.SETTING_KEY = /*settingKey*/ 
and SETTING.COMPANY_ID=/*companyId*/