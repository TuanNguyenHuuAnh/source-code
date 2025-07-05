SELECT
    *
FROM
    jca_account_password
WHERE
        CONVERT(datetime, effected_date, 101) <= CONVERT(datetime, GETDATE(), 101)
    AND
        expired_date IS NULL
    AND
        account_id = /*accountId*/''