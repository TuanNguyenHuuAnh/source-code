SELECT
    COUNT(1)
FROM
    jca_constant con
WHERE
    con.DELETED_ID = 0
    AND con.code = /*code*/''