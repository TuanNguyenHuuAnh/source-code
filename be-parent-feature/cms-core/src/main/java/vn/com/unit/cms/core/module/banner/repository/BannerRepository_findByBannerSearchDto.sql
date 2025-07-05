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
	  		) 		
	  		AS NUMBER_BANNER

		, (select count(1) from m_homepage_setting tmp 
			where 
	  		tmp.delete_date is null
			AND
			tbl.id in (select cast(value as bigint)  from STRING_SPLIT(tmp.M_BANNER_TOP_MOBILE_ID, ','))
			)
			AS CAN_BE_DELETED_MOBILE_APP
		
		, (select count(1) from m_homepage_setting tmp 
			where 
	  		tmp.delete_date is null
			AND
			tbl.id in (select cast(value as bigint) from STRING_SPLIT(tmp.M_BANNER_TOP_ID, ',')) 
			)
			AS CAN_BE_DELETED_WEB

		, (select count(1) from m_homepage_setting tmp 
			where 
	  		tmp.delete_date is null
			AND 
			tbl.id in (select cast(value as bigint)  from STRING_SPLIT(tmp.M_BANNER_TOP_MOBILE_ID, ',') 
			where tmp.END_DATE >= (CAST(GETDATE() AS DATE)) AND tmp.START_DATE <= (CAST(GETDATE() AS DATE)))	
			)
			AS CAN_BE_UPDATED_MOBILE_APP
		
		, (select count(1) from m_homepage_setting tmp 
			where 
	  		tmp.delete_date is null
			AND 
			tbl.id in (select cast(value as bigint) from STRING_SPLIT(tmp.M_BANNER_TOP_ID, ',') 
			where tmp.END_DATE >= (CAST(GETDATE() AS DATE)) AND tmp.START_DATE <= (CAST(GETDATE() AS DATE))) 
			) 
			AS CAN_BE_UPDATED_WEB

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
)
SELECT *
FROM TBL_DATA tbl
WHERE
	1 = 1
	/*IF searchDto.querySearch != NULL && searchDto.querySearch != ''*/
	AND
	(
		/*$searchDto.querySearch*/'BANNER'
	)
	/*END*/
	
/*IF orders != null*/
ORDER BY /*$orders*/tbl.ID
-- ELSE ORDER BY ISNULL(tbl.update_date, tbl.create_date) DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/