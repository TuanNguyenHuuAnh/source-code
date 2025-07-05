SELECT LANG.CODE AS LANGUAGE_CODE
	,con.id
    ,con.company_id
    ,con.content
FROM JCA_LANGUAGE LANG
    LEFT JOIN jca_contact CON ON LANG.CODE = CON.LANGUAGE_CODE AND con.company_id = /*companyId*/1
WHERE LANG.DELETED_ID = 0
    /*IF lang!=null*/
    AND UPPER(LANG.CODE) = UPPER(/*lang*/'en')
    /*END*/