SELECT * 
FROM jca_account_org 
WHERE ACCOUNT_ID = /*accountId*/
AND (ORG_ID = /*orgId*/ OR ORG_CODE = /*orgCode*/)
AND DELETED_ID = 0;