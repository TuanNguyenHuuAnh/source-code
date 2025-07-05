SELECT
	    id              AS id
	  , m_language_code	AS language_code
	  , title           AS title
	  , keywords_seo    AS keywords_seo
	  , keywords        AS keywords
	  , keywords_desc   AS keywords_desc
FROM m_faqs_category_language
WHERE
	delete_date is null
	AND m_faqs_category_id = /*categoryId*/