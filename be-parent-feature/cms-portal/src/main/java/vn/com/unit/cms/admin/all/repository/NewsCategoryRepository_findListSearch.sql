WITH TBL_DATA AS (
	SELECT
		tbl.id												AS	id
	  	, tbl.code											AS	code
	  	, typeLang.label									AS	type_name
	  	, tbl.M_NEWS_TYPE_ID								AS	M_NEWS_TYPE_ID
	  	, tbl.enabled										AS	enabled
	  	, tblLang.label										AS	TITLE
	  	, tbl.sort  										AS	sort
	  	, tbl.create_date									AS  create_date
	  	, tbl.create_by										AS	create_by
	  	, tbl.update_by										AS	update_by
	  	, tbl.update_date									AS	update_date
        , tmp.name                                          AS  CATEGORY_TYPE
        ,(SELECT count(1)
            FROM m_news tmpTbl
            WHERE 
                tmpTbl.delete_date is NULL
                AND tmpTbl.M_NEWS_CATEGORY_ID = tbl.id
        ) AS status_delete   
	FROM m_news_category tbl
	INNER JOIN m_news_category_language tblLang 
		ON tbl.id = tblLang.m_news_category_id 
		AND tblLang.delete_date is null
	INNER JOIN M_NEWS_TYPE mType
		ON mType.ID = tbl.M_NEWS_TYPE_ID
	INNER JOIN M_NEWS_TYPE_LANGUAGE typeLang
		ON typeLang.M_NEWS_TYPE_ID = tbl.M_NEWS_TYPE_ID
		AND UPPER(typeLang.M_LANGUAGE_CODE) = UPPER(tblLang.m_language_code)
    LEFT JOIN JCA_CONSTANT tmp
              ON tmp.CODE = tbl.CATEGORY_TYPE
                  AND tmp.GROUP_CODE = 'NEWS_TYPE' AND tmp.LANG_CODE = UPPER(/*searchDto.languageCode*/'VI')
	WHERE
		tbl.delete_date is null
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