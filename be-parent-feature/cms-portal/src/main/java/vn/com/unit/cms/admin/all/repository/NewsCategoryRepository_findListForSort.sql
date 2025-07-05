SELECT
	  categ.id				AS	id
  	, categ.code			AS	code
  	, clanguage.label		AS	label
  	, categ.create_date		AS  create_date
FROM m_news_category categ
JOIN m_news_category_language clanguage ON (categ.id = clanguage.m_news_category_id AND clanguage.delete_date is null)
JOIN m_news_type newsType ON (newsType.id = categ.m_news_type_id 
							  AND newsType.delete_date is null)
WHERE
	categ.delete_date is null
	AND categ.ENABLED = 1
	AND UPPER(clanguage.m_language_code) = UPPER(/*cond.languageCode*/)
	AND newsType.id =  /*cond.newsTypeId*/
ORDER BY categ.sort ASC
