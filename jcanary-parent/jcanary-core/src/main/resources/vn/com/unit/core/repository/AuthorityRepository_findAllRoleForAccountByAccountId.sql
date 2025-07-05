--
-- AuthorityRepository_findAllRoleForAccountByAccountId.sql

SELECT DISTINCT *
FROM VW_GET_AUTHORITY_ACCOUNT
WHERE ACCOUNT_ID = /*accountId*/0;