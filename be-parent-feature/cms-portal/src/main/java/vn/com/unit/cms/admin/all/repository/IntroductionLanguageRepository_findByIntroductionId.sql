--
-- IntroductionLanguageRepository_findByIntroductionId.sql

SELECT
	  i_l.id				AS	id
  	, i_l.m_introduction_id			AS	introduction_id
  	, i_l.m_language_code			AS	language_code
  	, i_l.title	AS	title
  	, i_l.short_content	AS	short_content
  	, i_l.content	AS	content
  	, i_l.banner_img_desktop AS banner_img_desktop
  	, i_l.banner_img_desktop_physical AS banner_img_desktop_physical
  	, i_l.banner_img_mobile AS banner_img_mobile
  	, i_l.banner_img_mobile_physical AS banner_img_mobile_physical
  	, i_l.link_alias AS link_alias
  	, l.name		AS	language_disp_name
  	, i_l.keyword	AS	keyword
  	, i_l.KEYWORD_DESCRIPTION	AS keyword_description
FROM m_introduction_language i_l
LEFT JOIN jca_m_language l ON (l.code = i_l.m_language_code)

WHERE
	i_l.delete_date is null
	AND i_l.m_introduction_id = /*introId*/
	
ORDER BY l.code