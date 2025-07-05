SELECT
	  tblLang.id					AS	id
  	, tblLang.m_category_id			AS	category_id
  	, tblLang.m_language_code		AS	language_code
  	, tblLang.title					AS	title
  	, tblLang.KEYWORDS_SEO			AS	keywords_seo
  	, tblLang.KEYWORDS				AS	keywords
  	, tblLang.KEYWORDS_DESC			AS	keywords_desc
FROM m_document_category_language tblLang

WHERE
	tblLang.delete_date is null
	AND tblLang.m_category_id = /*cateId*/