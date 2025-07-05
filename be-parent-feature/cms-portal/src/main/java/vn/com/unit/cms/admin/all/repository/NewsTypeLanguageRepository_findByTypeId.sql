SELECT
	    id				AS id
	  , m_language_code	AS language_code
	  , label 			AS label
	  , description		AS description
	  , LINK_ALIAS		AS link_alias
	  , KEY_WORD		AS key_word
	  , DESCRIPTION_KEYWORD	AS description_keyword
	  , IMAGE_NAME		AS IMAGE_NAME
	  , PHYCICAL_IMG	AS PHYCICAL_IMG
FROM m_news_type_language 
WHERE
	delete_date is null
	AND m_news_type_id = /*newsTypeId*/