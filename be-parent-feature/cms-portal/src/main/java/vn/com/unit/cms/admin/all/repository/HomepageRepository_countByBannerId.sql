SELECT COUNT(*) FROM M_HOMEPAGE_SETTING homepageSetting
WHERE homepageSetting.DELETE_DATE IS NULL
AND homepageSetting.STATUS NOT IN (1, 98, 100)
AND (homepageSetting.M_BANNER_TOP_ID LIKE ('%'|| /*bannerId*/ ||'%') 
	OR homepageSetting.M_BANNER_TOP_MOBILE_ID LIKE ('%'|| /*bannerId*/ ||'%'));