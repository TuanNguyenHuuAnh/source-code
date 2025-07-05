
SELECT
    *
FROM
    jca_account
WHERE
 DELETED_ID = 0
  AND ACCOUNT_TYPE <> 2
  AND EMAIL = /*email*/
