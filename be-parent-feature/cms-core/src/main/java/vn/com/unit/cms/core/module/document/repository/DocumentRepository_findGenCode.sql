	SELECT DISTINCT
		 tbl.code												AS	code
	FROM M_DOCUMENT_CATEGORY tbl
	LEFT JOIN M_DOCUMENT_CATEGORY_language tblLang 
		ON tblLang.M_CATEGORY_ID = tbl.id
	WHERE
		tbl.delete_date is null		
		AND tblLang.M_CATEGORY_ID = /*categoryLangId*/'37'