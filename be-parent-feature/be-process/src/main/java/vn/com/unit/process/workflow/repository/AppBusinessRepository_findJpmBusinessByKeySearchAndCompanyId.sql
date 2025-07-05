SELECT bu.ID           AS id,
	   bu.CODE         AS code,
	   bu.NAME         AS name,
	   bu.DESCRIPTION  AS description,
	   bu.IS_ACTIVE    AS is_active
FROM
	JPM_BUSINESS bu
WHERE 
	bu.DELETED_ID = 0
	/*IF !companyAdmin*/
	AND bu.COMPANY_ID = /*companyId*/1
	/*END*/
	
	/*IF keySearch != null && keySearch != ''*/
	AND UPPER(NAME) like concat('%',  concat(UPPER(/*keySearch*/''), '%'))
	/*END*/
	AND bu.IS_ACTIVE  = 1
ORDER BY 
	NAME
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/