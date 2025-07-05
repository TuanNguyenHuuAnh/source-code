SELECT bu.ID           AS id,
	   bu.BUSINESS_NAME         AS text,
	   bu.BUSINESS_NAME         AS name
FROM
	JPM_BUSINESS bu
WHERE 
	bu.DELETED_ID = 0
	AND bu.COMPANY_ID = /*companyId*/1
	AND bu.ACTIVED  = 1
	AND bu.PROCESS_TYPE IN /*processTypes*/()
	AND bu.ID NOT IN(SELECT DISTINCT ITEM.BUSINESS_ID 
					FROM EFO_FORM ITEM WHERE ITEM.BUSINESS_ID IS NOT NULL AND ITEM.DELETED_ID = 0)
	/*IF keySearch != null && keySearch != ''*/
		AND UPPER(bu.NAME) LIKE UPPER(concat(concat('%',  /*keySearch*/''), '%'))
	/*END*/
ORDER BY bu.BUSINESS_NAME
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/