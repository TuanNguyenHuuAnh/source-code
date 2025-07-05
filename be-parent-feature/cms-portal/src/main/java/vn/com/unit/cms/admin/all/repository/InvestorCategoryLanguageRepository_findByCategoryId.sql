SELECT investlaguage.id as id,
       investlaguage.M_INVESTOR_CATEGORY_ID as category_id,
       investlaguage.m_language_code					AS	language_code
      , investlaguage.title								  AS	title
    , investlaguage.description 						 AS description
  	, investlaguage.link_alias						   AS	link_alias
  	, investlaguage.key_word							   AS	key_word
  	, investlaguage.description_keyword				AS	description_keyword
  	, investlaguage.short_content                     AS short_content
FROM M_INVESTOR_CATEGORY_LANGUAGE investlaguage
WHERE
	investlaguage.delete_date is null
  AND investlaguage.m_investor_category_id = /*cateId*/