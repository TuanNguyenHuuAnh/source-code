SELECT 
	 DOC.ID							AS	 id
	,DOC.M_CATEGORY_ID				AS	 categoryId
	,docLang.TITLE					AS	 title
	,docLang.KEYWORDS_SEO			AS	 keywords_seo
	,docDetail.PHYSICAL_FILE_NAME	AS   physical_file_name
	,docDetail.FILE_NAME			AS   file_name
FROM M_DOCUMENT DOC
INNER JOIN M_DOCUMENT_LANGUAGE docLang
	ON DOC.ID = docLang.M_DOCUMENT_ID
	AND docLang.DELETE_BY IS NULL 
	AND docLang.M_LANGUAGE_CODE = /*language*/'VI'
INNER JOIN M_DOCUMENT_DETAIL docDetail
	ON DOC.ID = docDetail.M_DOCUMENT_ID
	AND docDetail.DELETE_BY IS NULL
WHERE 
	DOC.DELETE_BY IS NULL
	AND DOC.M_CATEGORY_ID = /*categoryId*/'8'
	/*IF searchKey != null && searchKey != ''*/
		and (docLang.KEYWORDS_SEO LIKE CONCAT( '%', CONCAT(/*searchKey*/'test', '%' )))
	/*END*/
	/*IF modeView == 0*/
		and DOC.ENABLED = 1
	/*END*/
ORDER BY
	  DOC.sort 						DESC
	, DOC.create_date 				DESC
	, DOC.ENABLED 					DESC
OFFSET /*offset*/0 ROWS FETCH NEXT /*size*/10 ROWS ONLY