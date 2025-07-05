--
-- EfoFormRepository_getSelect2DtoWithEfoFormByCompanyId.sql
SELECT  
	 form.ID            	AS   ID
	 ,form.NAME				AS TEXT
	 ,form.NAME				AS NAME

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
WHERE 
	form.DELETED_ID = 0
	/*IF keySearch != null && keySearch != ''*/
	AND	(
			UPPER(form.NAME) LIKE CONCAT('%',CONCAT(UPPER(/*keySearch*/''),'%'))
		)
	/*END*/
	/*IF companyId != null*/
	AND form.COMPANY_ID  = /*companyId*/
	/**END*/

ORDER BY form.NAME
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY