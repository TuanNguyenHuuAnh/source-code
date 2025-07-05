	WITH tmp AS (
	SELECT cate.id AS parent_id
	, doc.id AS id
	, docDetail.PHYSICAL_FILE_NAME
	, docLang.TITLE
	, ROW_NUMBER() OVER (ORDER BY doc.sort ASC, ISNULL(doc.update_date, doc.create_date) DESC, doc.ENABLED DESC) AS no
FROM M_DOCUMENT_CATEGORY cate
INNER JOIN M_DOCUMENT_CATEGORY_LANGUAGE cateLang 
	ON cate.id = cateLang.M_CATEGORY_ID
	AND cateLang.DELETE_BY is null
	AND cateLang.M_LANGUAGE_CODE = 'VI'
INNER JOIN dbo.M_DOCUMENT doc ON doc.M_CATEGORY_ID = cate.ID
INNER JOIN dbo.M_DOCUMENT_DETAIL docDetail ON doc.id = docDetail.M_DOCUMENT_ID
INNER JOIN dbo.M_DOCUMENT_LANGUAGE docLang ON doc.id = docLang.M_DOCUMENT_ID  AND docLang.M_LANGUAGE_CODE = 'vi'
OUTER APPLY dbo.[FN_GET_STATUS_PROCESS](doc.DOC_ID, 'vi') STA
WHERE 1=1
	--AND CATE.id = '31'
	AND CATE.CATEGORY_TYPE IN ('AQP2112.0001') -- Quy trinh GA
	AND CATE.DELETE_BY IS NULL
	AND doc.ENABLED = 1
	AND doc.POSTED_DATE <= GETDATE()
		
	AND ISNULL(doc.EXPIRATION_DATE, '99991231') >= GETDATE()
	and docDetail.VERSION_CURRENT = 1
	AND STA.STATUS_CODE IN ('994', '999')
	/*IF keySearch != null && keySearch != ''*/
		AND (docLang.TITLE LIKE CONCAT( '%', CONCAT(/*keySearch*/'', '%' )))
	/*END*/
)
SELECT * 
FROM tmp 
ORDER BY no
/*IF offset != null && pageSize != null*/
OFFSET /*offset*/0 ROWS FETCH NEXT /*pageSize*/10 ROWS ONLY
/*END*/