--
-- FaqsCategoryRepository_countFaqsCategory.sql

WITH TBL_TMP AS (
SELECT DISTINCT
		  tbl.id												AS	id
		, tbl.code												AS	code
		, tblLang.title											AS	title
		, item.FUNCTION_NAME									AS	ITEM_FUNCTION_CODE
	  	, tbl.SORT												AS	SORT
		, tbl.ENABLED											AS	enabled
	  	, tbl.create_by											AS	CREATE_BY
	  	, tbl.create_date										AS	create_date
	  	, tbl.UPDATE_BY											AS	UPDATE_BY
	  	, tbl.UPDATE_DATE										AS	UPDATE_DATE
	  	, (SELECT count(1)
	  		FROM m_faqs tmpTbl
	  		WHERE 
	  			tmpTbl.delete_date is NULL
	  			AND tmpTbl.ENABLED = 1
	  			AND tmpTbl.m_faqs_CATEGORY_ID = tbl.id
	  	) 
	  	+
	  	(SELECT count(1)
		  		FROM m_faqs_category tmpTbl
		  		WHERE 
		  			tmpTbl.delete_date is NULL
		  			AND tmpTbl.ENABLED = 1
		  			AND tmpTbl.M_FAQS_CATEROTY_PARENT_ID = tbl.id
		 )														AS	count_used
	FROM m_faqs_category tbl
	LEFT JOIN m_faqs_category_language tblLang 
		ON tblLang.m_faqs_category_id = tbl.id
	LEFT JOIN JCA_ITEM item
		ON item.FUNCTION_CODE = tbl.ITEM_FUNCTION_CODE
	WHERE
		tbl.delete_date is null
		AND UPPER(tblLang.m_language_code) = UPPER(/*searchDto.languageCode*/'VI')
		
)

SELECT
	count(*)
FROM TBL_TMP tbl
WHERE 1=1 
/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
			AND(/*$searchDto.querySearch*/)
		/*END*/


		