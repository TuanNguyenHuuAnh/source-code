SELECT
	  c_l.id								AS	id
  	, c_l.m_introduce_category_id			AS	category_id
  	, c_l.m_language_code					AS	language_code
  	, c_l.m_language_code					AS language_code
  	, c_l.label								AS	label
  	, c_l.description 						AS description
  	, c_l.banner_desktop					AS	banner_desktop
  	, c_l.banner_mobile						AS	banner_mobile
  	, c_l.link_alias						AS	link_alias
  	, c_l.key_word							AS	key_word
  	, c_l.description_keyword				AS	description_keyword
  	, c_l.short_content                     AS short_content
FROM m_introduction_category_language c_l

WHERE
	c_l.delete_date is null
	AND c_l.m_introduce_category_id = /*cateId*/