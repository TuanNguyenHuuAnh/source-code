SELECT
	    id					AS id
	  , m_language_code		AS language_code
	  , label 				AS label
	  , link_alias			AS link_alias
	  , key_word			AS key_word
	  , description_keyword	AS description_keyword
FROM m_news_category_language 
WHERE
	delete_date is null
	AND m_news_category_id = /*mNewsCategoryId*/