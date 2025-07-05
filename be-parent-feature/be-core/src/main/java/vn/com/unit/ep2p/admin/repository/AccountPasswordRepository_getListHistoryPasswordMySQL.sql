SELECT
    password
FROM
    jca_account_password
WHERE
        expired_date IS NOT NULL
    AND
        trunc(expired_date) <= trunc(SYSDATE)
    AND
        account_id = /*accountId*/''
ORDER BY id DESC
LIMIT /*numberOfOldPassword*/