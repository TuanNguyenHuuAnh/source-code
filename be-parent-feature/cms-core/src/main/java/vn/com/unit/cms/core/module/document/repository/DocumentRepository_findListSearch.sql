WITH TBL_DATA AS (
	SELECT 
		tbl.id													AS id
		, tbl.code 												AS code
		, tblLang.title 										AS title
		, category.title 										AS category_name
		, sta.status_name										AS status_name
		, sta.status_code										AS status_code
		, tbl.enabled											as enabled
		, tbl.posted_date										AS posted_date
		, tbl.expiration_date									AS expiration_date
		, tbl.create_date 										AS create_date
		, tbl.create_by											AS create_by
		, tbl.update_date 										AS update_date
		, tbl.update_by											AS update_by
		, TBL.SORT												AS SORT
	FROM M_DOCUMENT tbl WITH (NOLOCK)
	INNER JOIN M_DOCUMENT_language tblLang WITH (NOLOCK)
		ON tbl.id = tblLang.M_DOCUMENT_id
			
	LEFT JOIN M_DOCUMENT_CATEGORY_LANGUAGE category WITH (NOLOCK)
		ON category.M_CATEGORY_ID = tbl.M_CATEGORY_ID 
			AND category.m_language_code = tblLang.m_language_code
			AND category.delete_date is null
				
	LEFT JOIN VW_PENDING_AT pending
		ON pending.DOC_ID = tbl.DOC_ID
			
	LEFT JOIN VW_APPROVED approved
		ON approved.DOC_ID = tbl.DOC_ID
			
	OUTER APPLY dbo.[FN_GET_STATUS_PROCESS](tbl.DOC_ID, /*searchDto.languageCode*/'VI') STA
				
	WHERE
		tbl.delete_date is null
		AND UPPER(tblLang.m_language_code) = UPPER(/*searchDto.languageCode*/'VI')
		/*IF searchDto.channel != null && searchDto.channel != ''*/
	  	AND isnull(tbl.CHANNEL, 'AG') = /*searchDto.channel*/
	  	/*END*/
)
SELECT *
FROM TBL_DATA TBL
WHERE
	1 = 1
	
	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/
	)
	/*END*/
		
/*IF orders != null*/
ORDER BY /*$orders*/tbl.ID
-- ELSE ORDER BY tbl.sort ASC, ISNULL(tbl.update_date, tbl.create_date) DESC, tbl.ENABLED DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/