WITH TBL_DATA AS (
	SELECT
		  tbl.id																					AS	id
	  	, tbl.code																					AS	code
	  	, tblLang.title																				AS	title
	  	, tblLang.banner_img																		AS	banner_img
	  	, IIF(tbl.BANNER_TYPE = 1, tblLang.banner_physical_img, tblLang.BANNER_PHYSICAL_VIDEO)		AS	banner_physical_url
	  	, IIF(tbl.BANNER_TYPE = 1, tblLang.banner_physical_img, tblLang.BANNER_PHYSICAL_VIDEO)		AS	BANNER_PHYSICAL_IMG
	  	, tblLang.BANNER_VIDEO_TYPE																	AS	BANNER_VIDEO_TYPE
	  	, tblLang.BANNER_YOUTUBE_VIDEO																AS	BANNER_YOUTUBE_VIDEO
	  	, tbl.BANNER_TYPE																			AS	BANNER_TYPE
	  	, cType.name																				AS	BANNER_TYPE_NAME
	  	, cDevice.name																				AS	BANNER_DEVICE_NAME
		, tbl.BANNER_DEVICE																			AS	BANNER_DEVICE
	  	, tbl.create_by																				AS	create_by
	  	, tbl.create_date																			AS	create_date
	  	, tbl.UPDATE_BY																				AS	UPDATE_BY
	  	, tbl.UPDATE_DATE																			AS	UPDATE_DATE
	  	, (select count(1) 
	  		from m_homepage_setting hm 
	  		where 
	  			hm.delete_by is null 
	  			AND hm.delete_date is null
	  			AND 
	  			(
	  				hm.m_banner_top_id LIKE tbl.id 
	  				OR hm.m_banner_top_mobile_id LIKE tbl.id
	  			)
	  	) 																							AS	number_banner
	FROM m_banner tbl
	LEFT JOIN m_banner_language tblLang
		ON tbl.id = tblLang.banner_id 
		AND tblLang.delete_date is null
	INNER JOIN JCA_CONSTANT cType
		ON cType.GROUP_CODE = 'M_BANNER'
		AND cType.KIND = 'TYPE'
		AND cType.CODE = tbl.BANNER_TYPE
		AND cType.LANG_CODE = tblLang.M_LANGUAGE_CODE
	INNER JOIN JCA_CONSTANT cDevice
		ON cDevice.GROUP_CODE = 'M_BANNER'
		AND cDevice.KIND = 'DEVICE'
		AND cDevice.CODE = tbl.BANNER_DEVICE
		AND cDevice.LANG_CODE = tblLang.M_LANGUAGE_CODE
	WHERE
		tbl.delete_date is null
		AND UPPER(tblLang.m_language_code) = UPPER(/*searchDto.languageCode*/'VI')
)
SELECT count(*)
FROM TBL_DATA tbl
WHERE
	1 = 1
	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/'BANNER'
	)
	/*END*/

