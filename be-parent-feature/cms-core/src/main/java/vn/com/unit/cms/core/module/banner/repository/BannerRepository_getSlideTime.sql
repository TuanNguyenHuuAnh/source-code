SELECT 
	SLIDE_TIME AS slide_time
FROM M_HOMEPAGE_SETTING home
where
	1 = 1
	/*IF searchDto.bannerPage != null && searchDto.bannerPage != ''*/
	AND home.BANNER_PAGE = UPPER(/*searchDto.bannerPage*/)
	/*END*/
	AND home.BANNER_TYPE ='1'
	AND home.DELETE_DATE is null
	AND home.START_DATE <= GETDATE()
	AND ISNULL(home.END_DATE, GETDATE()) >= GETDATE()