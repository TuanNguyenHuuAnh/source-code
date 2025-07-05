SELECT
    TOP (/*numberOfOldPassword*/) password
FROM
    jca_account_password
WHERE
        expired_date IS NOT NULL
    AND
     	CONVERT(datetime, expired_date, 101) <= CONVERT(datetime, GETDATE(), 101)
    AND
        account_id = /*accountId*/''
ORDER BY id DESC