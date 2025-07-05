SELECT
    *
FROM
    jca_account_password
WHERE
        trunc(effected_date) <= trunc(SYSDATE)
    AND
        expired_date IS NULL
    AND
        account_id = /*accountId*/''