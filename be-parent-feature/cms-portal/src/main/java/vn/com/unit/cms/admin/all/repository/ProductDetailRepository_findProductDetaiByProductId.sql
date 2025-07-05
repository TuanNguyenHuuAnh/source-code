SELECT
	  id						AS	id
	, m_product_id				AS	product_id
	, m_language_code			AS	language_code
	, key_content				AS	key_content
	, content					AS	content
	, background_url			AS	background_url
	, background_physical		AS	background_physical
	, group_content				AS	group_content
	, microsite_content			AS	microsite_content
	, microsite_banner_desktop	AS	microsite_banner_desktop
	, microsite_banner_mobile	AS	microsite_banner_mobile
	, microsite_banner_physical_desktop	AS	microsite_banner_physical_desktop
	, microsite_banner_physical_mobile	AS	microsite_banner_physical_mobile
	, microsite_title			AS	title
	, microsite_image_url		AS	image_url
	, microsite_physical_img	AS	physical_img
	, microsite_icon_img		AS	icon_img
	, microsite_physical_icon	AS	physical_icon
FROM m_product_detail
WHERE delete_date is null
	AND m_product_id = /*productId*/
ORDER BY group_content