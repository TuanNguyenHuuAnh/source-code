SELECT bu.ID           AS id,
	   bu.CODE         AS code,
	   bu.NAME         AS name,
	   bu.DESCRIPTION  AS description,
	   bu.IS_ACTIVE    AS is_active,
	   bu.COMPANY_ID   AS company_id,
	   bu.PROCESS_TYPE AS process_type
FROM
	JPM_BUSINESS bu
WHERE 
	bu.DELETED_ID = 0
	/*IF !companyAdmin*/
	AND (bu.COMPANY_ID = /*companyId*/1
	 OR bu.COMPANY_ID IS NULL )
	 /*END*/
	AND bu.IS_ACTIVE  = 1
	AND 
		bu.PROCESS_TYPE NOT IN /*processTypeIgnores*/()
ORDER BY 
	NAME
/*IF isPaging == 1*/
OFFSET /*pageIndex*/ ROWS FETCH NEXT /*pageSize*/ ROWS ONLY
/*END*/