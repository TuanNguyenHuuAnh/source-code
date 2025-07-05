SELECT
	COUNT(*)
FROM EFO_FORM FR
INNER JOIN (select distinct FUNCTION_CODE from VW_GET_AUTHORITY_ACCOUNT where account_Id = /*search.accountId*/1 and access_flg = 0) AU ON AU.FUNCTION_CODE = FR.FUNCTION_CODE
LEFT JOIN EFO_CATEGORY CA ON CA.COMPANY_ID = FR.COMPANY_ID AND CA.DELETED_ID = 0 AND CA.ID = FR.CATEGORY_ID
WHERE FR.DELETED_ID = 0
AND FR.ACTIVED = 1
/*IF search.companyId != null*/
AND FR.COMPANY_ID = /*search.companyId*/1
/*END*/
/*IF search.categoryId != null*/
AND FR.CATEGORY_ID = /*search.categoryId*/1
/*END*/