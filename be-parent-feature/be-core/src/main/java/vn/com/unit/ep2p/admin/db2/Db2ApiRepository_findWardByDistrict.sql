SELECT
    P_ZIP.WARD         AS	ID
     , P_ZIP.NAME      AS	TEXT
     , P_ZIP.NAME      AS	NAME
FROM STG_ING.ZIPCODE P_ZIP

WHERE P_ZIP.WARD <> '00000'
    /*IF district != null && district != ''*/
  AND P_ZIP.DISTRICT   = /*district*/
    /*END*/
ORDER BY  P_ZIP.NAME