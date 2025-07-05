SELECT *
FROM JCA_ACCOUNT_QUESTION
WHERE
    ISNULL(DELETED_ID, 0) = 0
    AND COMPANY_ID = /*companyId*/1
    /*IF username != null && username != ''*/
    AND USERNAME = /*username*/1
    /*END*/
    