SELECT
    b.ID
    , BL.BANNER_IMG							AS BANNER_IMG
    , BL.BANNER_PHYSICAL_VIDEO				AS BANNER_PHYSICAL_VIDEO
    , home.YOUTUBE_VIDEO 					AS BANNER_YOUTUBE_VIDEO
	, BL.BANNER_LINK 						AS BANNER_LINK
    , BL.TITLE								AS TITLE
    , BL.DESCRIPTION 						AS DESCRIPTION
    , cType.name 							AS TYPE
	, home.AUTO_PLAY 						AS AUTO_PLAY  
	, home.AUTO_MUTE  						AS IS_MUTE
	, home.AUTO_REPLAY 						AS REPEAT_PLAY
FROM M_HOMEPAGE_SETTING home 
LEFT JOIN m_banner b
	ON home.BANNER_TYPE = b.BANNER_TYPE
	AND 
	(
		(CHARINDEX(CONCAT(',', b.ID,','), CONCAT(',', home.M_BANNER_TOP_ID,',')) > 0
			AND b.BANNER_DEVICE = 1
		)
		OR
		(CHARINDEX(CONCAT(',', b.ID,','), CONCAT(',', home.M_BANNER_TOP_MOBILE_ID,',')) > 0
			AND b.BANNER_DEVICE = 2
		)
	)
LEFT JOIN m_banner_language bl ON (b.id = bl.banner_id)
LEFT JOIN JCA_CONSTANT cType
	ON cType.GROUP_CODE = 'M_BANNER'
	AND cType.KIND = 'TYPE'
	AND cType.CODE = b.BANNER_TYPE
	AND cType.LANG_CODE = bl.M_LANGUAGE_CODE
LEFT JOIN JCA_CONSTANT cPro
	ON cPro.GROUP_CODE = 'HOMEPAGE'
	AND cPro.KIND = 'PROPERTIES'
	AND cPro.CODE = b.BANNER_TYPE
	AND cPro.LANG_CODE = bl.M_LANGUAGE_CODE
WHERE
	home.delete_date is null
   	AND b.delete_date is null
    AND UPPER(bl.m_language_code) = UPPER(/*searchDto.languageCode*/'vi')
    AND b.BANNER_TYPE=2
	/*IF searchDto.bannerDevice!= null && searchDto.bannerDevice!= ''*/
	AND b.BANNER_DEVICE = /*searchDto.bannerDevice*/
	/*END*/
	/*IF searchDto.bannerPage != null && searchDto.bannerPage != ''*/
	AND home.BANNER_PAGE = /*searchDto.bannerPage*/
	/*END*/
	/*IF modeView == 0*/
	AND b.ENABLED = 1
	/*END*/
	AND home.START_DATE <= GETDATE()
	AND ISNULL(home.END_DATE, DATEADD(day, 1, GETDATE())) >= GETDATE()