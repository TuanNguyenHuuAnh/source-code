SELECT * 
FROM (
	SELECT
		org.ID 							as ORG_ID
		,org.CODE						as CODE
		,org.NAME						as NAME
		,org.NAME_ABV					as NAME_ABV
		,org.DISPLAY_ORDER				as DISPLAY_ORDER
		,org.ORG_TYPE					as ORG_TYPE
		,org_path.ANCESTOR_ID			as ORG_PARENT_ID
		,org.EMAIL						as EMAIL
		,org.PHONE						as PHONE
		,org.COMPANY_ID					as COMPANY_ID
		,org.ADDRESS					as ADDRESS
		,org.ACTIVED					as ACTIVED
		,org_path.DEPTH					as DEPTH
		,CASE WHEN org_path_next.DESCENDANT_ID IS NULL THEN 1 ELSE 0 END as IS_LEAF
	    ,ROW_NUMBER() OVER(partition by org.ID ORDER BY org.ID ASC) AS NO
	FROM 
		JCA_ORGANIZATION org
	INNER JOIN
		JCA_ORGANIZATION_PATH org_path
	ON 
		org.ID  = org_path.DESCENDANT_ID
		AND org_path.DEPTH = /*depth*/1
	LEFT JOIN
		JCA_ORGANIZATION_PATH org_path_next
	ON 
		org_path.DESCENDANT_ID = org_path_next.ANCESTOR_ID
		AND org_path_next.DEPTH = /*depth*/1
	WHERE  
	org.DELETED_ID = 0
	AND org_path.ANCESTOR_ID = /*parentId*/1
	) a
WHERE NO = 1
ORDER BY
	DEPTH, DISPLAY_ORDER