SELECT 
	FR.FUNCTION_CODE
FROM EFO_FORM FR
WHERE FR.DELETED_ID = 0
	AND FR.ACTIVED = 1
	/* IF categoryId !=null */	
	AND	FR.CATEGORY_ID = /*categoryId*/
	/* END */
	/* IF companyId !=null */
	AND	FR.COMPANY_ID = /*companyId*/
	/* END */
	/* IF formId !=null */
	AND	FR.ID = /*formId*/
	/* END */