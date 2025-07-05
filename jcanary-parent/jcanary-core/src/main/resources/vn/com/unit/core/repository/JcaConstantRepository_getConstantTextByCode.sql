SELECT
    con.name AS text
FROM
    jca_constant            con
WHERE
    con.ACTIVED = 1
    AND con.code = /*code*/''
    AND UPPER(con.LANG_CODE) = UPPER(/*lang*/)