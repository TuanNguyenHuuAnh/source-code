WITH tmp AS (
	SELECT cate.id AS parent_id
	, doc.id AS id
	, docDetail.PHYSICAL_FILE_NAME
	, docLang.TITLE
	, ROW_NUMBER() OVER (ORDER BY doc.id DESC) AS no
FROM M_DOCUMENT_CATEGORY cate
INNER JOIN M_DOCUMENT_CATEGORY_LANGUAGE cateLang
	ON cate.id = cateLang.M_CATEGORY_ID
	AND cateLang.DELETE_BY is null
	AND cateLang.M_LANGUAGE_CODE = /*locale*/'vi'
INNER JOIN dbo.M_DOCUMENT doc ON doc.M_CATEGORY_ID = cate.ID
INNER JOIN dbo.M_DOCUMENT_DETAIL docDetail ON doc.id = docDetail.M_DOCUMENT_ID
INNER JOIN dbo.M_DOCUMENT_LANGUAGE docLang ON doc.id = docLang.M_DOCUMENT_ID  AND docLang.M_LANGUAGE_CODE = /*locale*/'vi'
OUTER APPLY dbo.[FN_GET_STATUS_PROCESS](doc.DOC_ID, 'vi') STA
WHERE 1=1
	AND CATE.id = /*id*/'31'
	AND CATE.DELETE_BY IS NULL
	AND doc.ENABLED = 1
	AND doc.POSTED_DATE <= GETDATE()
	AND ISNULL(doc.EXPIRATION_DATE, '99991231') >= GETDATE()
	AND STA.STATUS_CODE IN ('994', '999')
	/*IF keySearch != null && keySearch != ''*/
		AND (docLang.TITLE LIKE CONCAT( '%', CONCAT(/*keySearch*/'', '%' )))
	/*END*/
	and docDetail.VERSION_CURRENT = 1
	/*IF channel == null || channel == ''*/
	AND isnull(cate.CHANNEL, 'AG') = 'AG'
	/*END*/
	/*IF channel != null && channel == 'AG'*/
	AND isnull(cate.CHANNEL, 'AG') = /*channel*/
	/*END*/
	/*IF channel != null && channel == 'AD'*/
	AND cate.CHANNEL = /*channel*/
	/*END*/
)
SELECT count(1)
FROM tmp
