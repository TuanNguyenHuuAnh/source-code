SELECT
    b.ID
    , BL.BANNER_IMG
    , BL.BANNER_PHYSICAL_IMG 				AS BANNER_PHYSICAL_IMG
    , BL.BANNER_LINK 						AS BANNER_LINK
    , BL.TITLE 								AS TITLE
    , BL.DESCRIPTION 						AS DESCRIPTION
    , cType.name 							AS TYPE
	, home.AUTO_PLAY 						AS AUTO_PLAY
	, home.AUTO_MUTE  						AS IS_MUTE
	, home.AUTO_REPLAY 						AS REPEAT_PLAY
	, home.M_BANNER_TOP_ID					as M_BANNER_TOP_ID
	, home.M_BANNER_TOP_MOBILE_ID			as M_BANNER_TOP_MOBILE_ID
	, home.M_BANNER_TOP_ID					as STRING_BANNER_TOP_ID
	, home.M_BANNER_TOP_MOBILE_ID			as STRING_BANNER_TOP_MOBILE_ID
	, b.ID									AS BANNER_ID
	, home.CHANNEL							AS CHANNEL
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
INNER JOIN JCA_CONSTANT cType
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
	/*IF searchDto.bannerDevice!= null && searchDto.bannerDevice!= ''*/
    AND b.BANNER_TYPE=1
	AND b.BANNER_DEVICE = /*searchDto.bannerDevice*/
	/*END*/
	/*IF searchDto.bannerPage != null && searchDto.bannerPage != ''*/
	AND home.BANNER_PAGE = /*searchDto.bannerPage*/
	/*END*/
	/*IF modeView == 0*/
	AND b.ENABLED = 1
	/*END*/
	/*IF channel == null || channel == ''*/
	AND isnull(home.CHANNEL, 'AG') = /*channel*/
	/*END*/
	/*IF channel != null && channel == 'AG'*/
	AND isnull(home.CHANNEL, 'AG') = /*channel*/
	/*END*/
	/*IF channel != null && channel == 'AD'*/
	AND home.CHANNEL = /*channel*/
	/*END*/
	AND home.START_DATE <= GETDATE()
	AND ISNULL(home.END_DATE, GETDATE()) >= GETDATE()
	ORDER BY ID 
	/*IF offset != null*/
	OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
	/*END*/