--
-- EfoFormRepository_getEfoFormDtoByCondition.sql
SELECT  
	 form.ID            		AS   FORM_ID
	,form.COMPANY_ID    		AS   COMPANY_ID
	,form.CATEGORY_ID   		AS   CATEGORY_ID
	,form.BUSINESS_ID 			AS   BUSINESS_ID
	,form.NAME          		AS   FORM_NAME
	,form.DESCRIPTION   		AS   DESCRIPTION
	,form.OZ_FILE_PATH     		AS   OZ_FILE_PATH
	,form.ICON_FILE_PATH    	AS   ICON_FILE_PATH
	,form.ICON_REPO_ID 			AS   ICON_REPO_ID
	,form.DISPLAY_ORDER 		AS   DISPLAY_ORDER
	,form.DEVICE_TYPE   		AS   DEVICE_TYPE
	,form.ACTIVED   			AS   ACTIVED
	,form.FORM_TYPE				AS  FORM_TYPE
	,form.OZ_APPEND_FILE_PATH 	AS	OZ_APPEND_FILE_PATH
	,form.CREATED_DATE      	AS CREATED_DATE
    ,ecl.NAME	            	AS CATEGORY_NAME
    ,business.Business_Name 	AS BUSINESS_NAME
    ,business.BUSINESS_CODE 	AS BUSINESS_CODE
    ,company.NAME 				AS COMPANY_NAME
    ,account.fullname      		AS USER_NAME
    ,form.UPDATED_DATE      	AS UPDATED_DATE
FROM
	EFO_FORM form
INNER JOIN EFO_FORM_AUTHORITY authorForm
ON
	authorForm.FORM_ID = form.ID
INNER JOIN
	/*IF dbType != '' && dbType != null*/
		/*IF dbType == 'ORACLE'*/
			TABLE(FN_AUTHORITY_ROLE_FOR_ACCOUNT(/*accountId*/500,'3','0')) authoAcc
		/*END*/
		/*IF dbType == 'SQLSERVER'*/
			(
				SELECT *
				FROM FN_AUTHORITY_ROLE_FOR_ACCOUNT(/*accountId*/500,'3','0')
			) authoAcc
		/*END*/
		/*IF dbType == 'MYSQL'*/
			(
				SELECT *
				FROM FN_AUTHORITY_ROLE_FOR_ACCOUNT(/*accountId*/500,'3','0')
			) authoAcc
		/*END*/
	-- ELSE TABLE(FN_AUTHORITY_ROLE_FOR_ACCOUNT(/*accountId*/500,'3','0')) authoAcc
	/*END*/
ON 
  authorForm.ROLE_ID = authoAcc.ROLE_ID
LEFT JOIN
	 EFO_CATEGORY ca 
ON 
	ca.COMPANY_ID = form.COMPANY_ID AND ca.DELETED_ID = 0 AND ca.ID = form.CATEGORY_ID
LEFT JOIN
	 EFO_CATEGORY_LANG ecl
ON 
	ecl.CATEGORY_ID = ca.ID AND ca.DELETED_ID = 0 AND UPPER(ecl.LANG_CODE) = UPPER(/*langCode*/'en')
LEFT JOIN 
	JPM_BUSINESS business
ON
	business.ID = form.BUSINESS_ID
LEFT JOIN 
	JCA_COMPANY company
ON
	company.ID = form.COMPANY_ID 
LEFT JOIN  
	JCA_ACCOUNT account 
ON 
	account.ID= form.created_id
WHERE 

	form.DELETED_ID = 0
/*BEGIN*/
	AND(
		/*IF efoFormSearchDto.formName != null && efoFormSearchDto.formName != ''*/
			OR	UPPER(form.NAME) LIKE CONCAT('%',CONCAT(UPPER(/*efoFormSearchDto.formName*/''),'%'))
			
		/*END*/
		/*IF efoFormSearchDto.description != null && efoFormSearchDto.description != '' */
			OR UPPER(form.DESCRIPTION)  LIKE CONCAT('%',CONCAT(UPPER(/*efoFormSearchDto.description*/''),'%'))
		/*END*/	
		/*IF efoFormSearchDto.fileName != null && efoFormSearchDto.fileName != '' */
			OR UPPER(form.OZ_FILE_PATH)  LIKE CONCAT('%',CONCAT(UPPER(/*efoFormSearchDto.fileName*/''),'%'))
		/*END*/	
	)
/*END*/
	
	/*IF efoFormSearchDto.companyId != null && efoFormSearchDto.companyId != 0*/
	AND form.COMPANY_ID = /*efoFormSearchDto.companyId*/1
	/*END*/
	/*IF efoFormSearchDto.companyIdList != null && efoFormSearchDto.companyId == null*/
	AND (form.COMPANY_ID IS NULL OR form.COMPANY_ID IN /*efoFormSearchDto.companyIdList*/() )
	/*END*/
	
	/*IF efoFormSearchDto.categoryId != null*/
	AND form.CATEGORY_ID  = /*efoFormSearchDto.categoryId*/
	/*END*/
	/*IF efoFormSearchDto.formId != null*/
	AND form.ID  = /*efoFormSearchDto.formId*/
	/*END*/
	/*IF efoFormSearchDto.formType != null && efoFormSearchDto.formType != '' */
	AND form.FORM_TYPE  = /*efoFormSearchDto.formType*/
	/*END*/

/*IF orders != null*/
ORDER BY /*$orders*/
-- ELSE ORDER BY form.ID DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/