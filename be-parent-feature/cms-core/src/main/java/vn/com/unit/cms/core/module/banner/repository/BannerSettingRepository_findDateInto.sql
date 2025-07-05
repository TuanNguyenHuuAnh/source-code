SELECT TOP 1 * 
FROM M_HOMEPAGE_SETTING  
WHERE  BANNER_PAGE = /*bannerPage*/'ACTIVITY'
  AND BANNER_TYPE = /*bannerType*/'1'
  AND delete_date is NULL
  
 /*IF homgpageId != NULL && homgpageId != ''*/
AND (/*homgpageId*/'285' <> id)
/*END*/

/*IF channel == null || channel == ''*/
AND isnull(CHANNEL, 'AG') = /*channel*/
/*END*/
/*IF channel != null && channel == 'AG'*/
AND isnull(CHANNEL, 'AG') = /*channel*/
/*END*/
/*IF channel != null && channel == 'AD'*/
AND CHANNEL = /*channel*/
/*END*/
ORDER BY ISNULL(END_DATE, '99991231') DESC, id DESC