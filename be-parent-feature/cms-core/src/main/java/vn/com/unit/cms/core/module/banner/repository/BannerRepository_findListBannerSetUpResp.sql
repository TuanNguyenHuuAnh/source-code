SELECT
    b.ID
    , BL.BANNER_IMG
    , IIF(B.BANNER_TYPE = 1
    , BL.BANNER_PHYSICAL_IMG
    , IIF(BL.BANNER_VIDEO_TYPE = 1 
    , BL.BANNER_PHYSICAL_VIDEO, BL.BANNER_YOUTUBE_VIDEO))		AS	BANNER_PHYSICAL_IMG
    , BL.BANNER_LINK                                                        																AS  BANNER_LINK
    , BL.TITLE                                                               																AS  TITLE
    , BL.DESCRIPTION
FROM m_banner b
LEFT JOIN m_banner_language bl ON (b.id = bl.banner_id)
WHERE
    b.delete_date is null
    AND UPPER(bl.m_language_code) = UPPER(/*langCode*/'vi')
    /*IF bannerType != null && bannerType != ''*/
    AND b.banner_type = /*bannerType*/'1'
    /*END*/