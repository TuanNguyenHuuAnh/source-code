select top 1
	home.YOUTUBE_VIDEO 						AS YOUTUBE_VIDEO
	, home.AUTO_PLAY 						AS AUTO_PLAY  
	, home.AUTO_MUTE  						AS IS_MUTE
	, home.AUTO_REPLAY 						AS REPEAT_PLAY
	, cType.name 							AS TYPE
	from M_HOMEPAGE_SETTING home
	LEFT JOIN JCA_CONSTANT cType
	ON cType.GROUP_CODE = 'M_BANNER'
	AND cType.KIND = 'TYPE'
	AND cType.CODE = home.BANNER_TYPE
	WHERE
	home.delete_date is null
	/*IF searchDto.bannerPage != null && searchDto.bannerPage != ''*/
	and home.BANNER_PAGE= /*searchDto.bannerPage*/'PUBLIC'
	/*END*/
	AND CONVERT(DATE, home.START_DATE) <= CONVERT(DATE, GETDATE())
	AND CONVERT(DATE, ISNULL(home.END_DATE, GETDATE())) >= CONVERT(DATE, GETDATE())
	and home.BANNER_TYPE=2
