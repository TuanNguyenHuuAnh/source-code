-- EfoFormRepository_countEfoFormByCondition.sql
SELECT  
	count(1)
FROM
	EFO_FORM form
INNER JOIN EFO_FORM_AUTHORITY authorForm
	ON
		authorForm.FORM_ID = form.ID
INNER JOIN
	(
		select *
		from FN_AUTHORITY_ROLE_FOR_ACCOUNT(/*accountId*/500,'3','0')
	) authoAcc
	ON 
	  authorForm.ROLE_ID = authoAcc.ROLE_ID
WHERE
	form.DELETED_ID = 0
	/*BEGIN*/
		AND(
			/*IF efoFormSearchDto.formName != null && efoFormSearchDto.formName != ''*/
				OR	UPPER(form.NAME) LIKE CONCAT('%',CONCAT(UPPER(/*efoFormSearchDto.formName*/''),'%'))
				
			/*END*/
			/*IF efoFormSearchDto.description != null && efoFormSearchDto.description != '' */
				OR UPPER(form.DESCRIPTION) LIKE CONCAT('%',CONCAT(UPPER(/*efoFormSearchDto.description*/''),'%'))
			/*END*/
				/*IF efoFormSearchDto.fileName != null && efoFormSearchDto.fileName != '' */
				OR UPPER(form.OZ_FILE_PATH)  LIKE CONCAT('%',CONCAT(UPPER(/*efoFormSearchDto.fileName*/''),'%'))
			/*END*/	
		)
	/*END*/

	/*IF efoFormSearchDto.formType != null && efoFormSearchDto.formType != '' */
	AND form.FORM_TYPE  = /*efoFormSearchDto.formType*/
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
;