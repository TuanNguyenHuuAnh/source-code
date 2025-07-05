SELECT
      COMPANY.ID                                  AS COMPANY_ID
      ,COMPANY.NAME                               AS NAME
      ,COMPANY.SYSTEM_NAME                        AS SYSTEM_NAME
      ,COMPANY.SYSTEM_CODE                        AS SYSTEM_CODE
      ,COMPANY.DESCRIPTION                        AS DESCRIPTION
      ,COMPANY.PACKAGE_LOGIN_BACKGROUND           AS PACKAGE_LOGIN_BACKGROUND
      ,COMPANY.PACKAGE_SHORTCUT_ICON              AS PACKAGE_SHORTCUT_ICON
      ,COMPANY.PACKAGE_LOGO_LARGE                 AS PACKAGE_LOGO_LARGE
      ,COMPANY.PACKAGE_LOGO_MINI                  AS PACKAGE_LOGO_MINI
      ,COMPANY.LOGIN_BACKGROUND                   AS LOGIN_LOGO
      ,COMPANY.LOGIN_BACKGROUND_REPO_ID           AS LOGIN_LOGO_REPO_ID
      ,COMPANY.SHORTCUT_ICON                      AS SHORTCUT_ICON
      ,COMPANY.SHORTCUT_ICON_REPO_ID              AS SHORTCUT_ICON_REPO_ID
      ,COMPANY.LOGO_LARGE                         AS LOGO_LARGE
      ,COMPANY.LOGO_LARGE_REPO_ID                 AS LOGO_LARGE_REPO_ID
      ,COMPANY.LOGO_MINI                          AS LOGO_MINI
      ,COMPANY.LOGO_MINI_REPO_ID                  AS LOGO_MINI_REPO_ID
      ,COMPANY.STYLE                              AS STYLE
      ,COMPANY.LANGUAGE                           AS LANGUAGE
      ,COMPANY.EFFECTED_DATE                      AS EFFECTED_DATE
      ,COMPANY.EXPIRED_DATE                       AS EXPIRED_DATE
      ,COMPANY.LIMIT_NUMBER_USERS                 AS LIMIT_NUMBER_USERS
      ,COMPANY.LIMIT_NUMBER_TRANSACTION           AS LIMIT_NUMBER_TRANSACTION
      ,COMPANY.ACTIVED                        AS ACTIVED
      ,COMPANY.CREATED_DATE                       AS CREATED_DATE
      ,COMPANY.CREATED_ID                         AS CREATED_ID
      ,COMPANY.UPDATED_DATE                       AS UPDATED_DATE      
      ,COMPANY.UPDATED_ID                         AS UPDATED_ID
      ,COMPANY.DELETED_DATE                       AS DELETED_DATE
      ,COMPANY.DELETED_ID                         AS DELETED_ID
      
FROM JCA_COMPANY COMPANY
WHERE COMPANY.DELETED_ID = 0

/*BEGIN*/
	AND (
		/*IF companySearchDto.name != null && companySearchDto.name != ''*/
			UPPER(COMPANY.NAME) LIKE concat(concat('%',  UPPER(/*companySearchDto.name*/'')), '%')
		/*END*/
		/*IF companySearchDto.description != null && companySearchDto.description != ''*/
			OR UPPER(COMPANY.DESCRIPTION) LIKE concat(concat('%',  UPPER(/*companySearchDto.description*/'')), '%')
		/*END*/
		/*IF companySearchDto.systemCode != null && companySearchDto.systemCode != ''*/
			OR UPPER(COMPANY.SYSTEM_CODE) LIKE concat(concat('%',  UPPER(/*companySearchDto.systemCode*/'')), '%')
		/*END*/
		/*IF companySearchDto.systemName != null && companySearchDto.systemName != ''*/
			OR UPPER(COMPANY.SYSTEM_NAME) LIKE concat(concat('%',  UPPER(/*companySearchDto.systemName*/'')), '%')
		/*END*/
	)
/*END*/

/*IF orders != null*/
ORDER BY /*$orders*/username
-- ELSE ORDER BY COMPANY.UPDATED_DATE DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/
;