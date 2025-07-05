WITH TBL_DATA AS (
	SELECT
	hs.ID                         			AS ID
	, hs.banner_page				        AS BANNER_PAGE
	, cDevice.name							AS BANNER_PAGE_NAME
	, cType.name							AS BANNER_TYPE_NAME
	, hs.START_DATE					        AS START_DATE
	, hs.END_DATE					        AS END_DATE
	, hs.M_BANNER_TOP_ID 					AS M_BANNER_TOP_ID
	, hs.M_BANNER_TOP_MOBILE_ID				AS M_BANNER_TOP_MOBILE_ID 
	, hs.CREATE_BY							AS CREATE_BY
	, hs.CREATE_DATE						AS CREATE_DATE
	, hs.UPDATE_BY							AS UPDATE_BY
	, hs.UPDATE_DATE						AS UPDATE_DATE
	, hs.DELETE_DATE						AS DELETE_DATE
			
	from M_HOMEPAGE_SETTING hs

	INNER JOIN JCA_CONSTANT cType
		ON cType.GROUP_CODE = 'M_BANNER'
		AND cType.KIND = 'TYPE'
		AND cType.CODE = hs.BANNER_TYPE
		AND cType.LANG_CODE = UPPER(/*searchDto.languageCode*/'VI')
	INNER JOIN JCA_CONSTANT cDevice
		ON  cDevice.GROUP_CODE = 'PAGE_TYPE'
		AND cDevice.KIND = hs.BANNER_PAGE
		AND cDevice.LANG_CODE = UPPER(/*searchDto.languageCode*/'VI')
	WHERE
		hs.delete_date is null
		
)
SELECT count(*) 
FROM TBL_DATA tbl
WHERE
	1 = 1
	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/
	)
	/*END*/