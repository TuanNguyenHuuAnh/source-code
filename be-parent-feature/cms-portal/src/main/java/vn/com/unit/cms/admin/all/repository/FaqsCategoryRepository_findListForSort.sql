SELECT
    tbl.id 									AS id
    , tbl.code								AS code
    , tblLang.title 						AS title
    , tbl.enabled
    , tbl.create_date
FROM m_faqs_category tbl
LEFT JOIN m_faqs_category_language tblLang 
	ON tblLang.m_faqs_category_id = tbl.id
WHERE
	tbl.delete_date is null
	AND tbl.ENABLED = 1
	AND UPPER(tblLang.m_language_code) = UPPER(/*searchCond.languageCode*/'VI')
	AND tbl.M_FAQS_CATEROTY_PARENT_ID = /*searchCond.faqsCategoryParentId*/1
order by tbl.sort