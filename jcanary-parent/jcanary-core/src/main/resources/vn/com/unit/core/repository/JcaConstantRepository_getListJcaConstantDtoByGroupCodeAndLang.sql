SELECT
    con.code             AS code,
    con.group_code       AS group_code,
    con.display_order    AS display_order,
    con.LANG_CODE   	 AS lang_code,
    con.Name          AS name
FROM
    jca_constant            con
WHERE
    con.ACTIVED = 1
    AND con.group_code = /*groupCode*/''
    AND Upper(con.LANG_CODE) = Upper(/*lang*/'')
  