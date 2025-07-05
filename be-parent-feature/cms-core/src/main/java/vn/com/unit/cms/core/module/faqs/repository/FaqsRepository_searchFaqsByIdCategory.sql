select
	  faq.id					AS	id
	, faq.code					AS	code
	, faq.M_CUSTOMER_TYPE_ID	AS	CUSTOMER_ID
	, faq.M_FAQS_CATEGORY_ID	AS	CATEGORY_ID
	, faq.NOTE					AS	note
	, faq.SORT					AS	sort
	, faq.enabled				AS	enabled
	, faq.POSTED_DATE			AS	POSTED_DATE
	, faq.EXPIRATION_DATE 		AS	EXPIRATION_DATE
	, faq.DOC_ID				AS	DOC_ID
	, faq.create_date			AS	create_date
	, faq.create_by				AS	create_by
	, faqlang.short_content		AS	SHORT_CONTENT
	, faqlang.CONTENT 			AS	content
	, faqlang.TITLE				AS	title
	, faqlang.KEYWORDS_SEO		AS	LINK_ALIAS
	, faqlang.KEYWORDS			AS	KEYWORD
	, faqlang.KEYWORDS_DESC		AS	KEYWORD_DESCRIPION
	, faqlang.M_LANGUAGE_CODE	AS	languageCode
from m_faqs faq
left join M_FAQS_LANGUAGE faqlang
	on ( faqlang.M_FAQS_ID = faq.ID 
		and faqlang.M_LANGUAGE_CODE = UPPER(/*language*/'VI')
		and faqlang.DELETE_BY is null )
OUTER APPLY dbo.[FN_GET_STATUS_PROCESS](faq.DOC_ID, /*language*/'VI') STA
where
    faq.DELETE_BY is null
    AND ((TRY_CONVERT(DATE, faq.POSTED_DATE, 103) <= TRY_CONVERT(DATE, faq.EXPIRATION_DATE, 103) AND TRY_CONVERT(DATE, faq.EXPIRATION_DATE, 103) > TRY_CONVERT(DATE, GETDATE(), 103) )
    OR faq.EXPIRATION_DATE is null)
    AND (STA.STATUS_CODE = '994' OR STA.STATUS_CODE = '999')
	/*IF idCategory != null */
		and faq.M_FAQS_CATEGORY_ID = /*idCategory*/100081 
	/*END*/
	/*IF searchKey != null && searchKey != ''*/
		and (faqlang.KEYWORDS_SEO LIKE CONCAT( '%', CONCAT(/*searchKey*/, '%' )))
	/*END*/
	/*IF modeView == 0*/
		and faq.ENABLED = 1
	/*END*/
ORDER BY
	  faq.sort 						DESC
	, faq.create_date 				DESC
	, faq.ENABLED 					DESC
OFFSET /*offset*/0 ROWS FETCH NEXT /*size*/5 ROWS ONLY