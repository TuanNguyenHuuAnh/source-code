SELECT *
FROM JCA_ACCOUNT_QUESTION
WHERE
    1 = 1
    AND ISNULL(DELETED_ID, 0) = 0
    /*IF userId != null*/
    AND USER_ID = /*userId*/1
    /*END*/