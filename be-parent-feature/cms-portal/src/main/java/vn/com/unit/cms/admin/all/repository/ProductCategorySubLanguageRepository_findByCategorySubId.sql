SELECT
	    id				AS id
	  , m_language_code	AS language_code
	  , title 			AS title
	  , description 			AS description
	  , keyword					AS keyword
	  , keyword_description		AS keyword_description
	  , link_alias				AS link_alias
	  , text_on_banner			AS text_on_banner
	  , banner_desktop			AS banner_desktop
	  , banner_mobile			AS banner_mobile
FROM m_product_category_sub_language 
WHERE
	delete_date is null
	AND m_product_category_sub_id = /*productCategorySubId*/