SELECT
	    id							AS id
	  , banner_id					AS banner_id
	  , m_language_code 			AS language_code
	  , banner_image_middle			AS banner_text_left	
	  , banner_link					AS banner_link
	  , banner_img					AS banner_img
	  , banner_physical_img			AS banner_physical_img
	  , banner_video				AS banner_video
	  , banner_video_type			AS banner_video_type
	  , banner_physical_video		AS banner_physical_video
	  , banner_youtube_video		AS banner_youtube_video
	  , title						AS title
	  , description					AS description
FROM m_banner_language 
WHERE
	delete_date is null
	AND banner_id = /*bannerId*/