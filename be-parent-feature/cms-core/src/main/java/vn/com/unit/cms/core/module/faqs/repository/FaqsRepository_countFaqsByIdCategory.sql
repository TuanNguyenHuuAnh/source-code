select
	count(*)
from m_faqs faq
left join M_FAQS_LANGUAGE faqlang
	on (faq.ID = faqlang.M_FAQS_ID
		and faqlang.M_LANGUAGE_CODE = UPPER(/*language*/'VI')
		and faqlang.DELETE_BY is null )
OUTER APPLY dbo.[FN_GET_STATUS_PROCESS](faq.DOC_ID, /*language*/'VI') STA
where
	faq.DELETE_BY is null
	AND ((TRY_CONVERT(DATE, faq.POSTED_DATE, 103) <= TRY_CONVERT(DATE, faq.EXPIRATION_DATE, 103) AND TRY_CONVERT(DATE, faq.EXPIRATION_DATE, 103) > TRY_CONVERT(DATE, GETDATE(), 103) )
    OR faq.EXPIRATION_DATE is null)
    AND (STA.STATUS_CODE = '994' OR STA.STATUS_CODE = '999')
/*IF idCategory != null */
	and faq.M_FAQS_CATEGORY_ID = /*idCategory*/ 
/*END*/
/*IF searchKey != null && searchKey != ''*/
    and (faqlang.KEYWORDS_SEO LIKE CONCAT( '%', CONCAT(/*searchKey*/, '%' )))
/*END*/
/*IF modeView == 0*/
	and faq.ENABLED = 1
/*END*/
