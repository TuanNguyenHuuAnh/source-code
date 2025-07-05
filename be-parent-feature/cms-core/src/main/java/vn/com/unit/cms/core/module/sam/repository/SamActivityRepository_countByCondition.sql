SELECT count(1)
FROM SAM_ACTIVITY a
	INNER JOIN SAM_ORGANIZATION_LOCATION l ON a.ID = l.ACTIVITY_ID
	LEFT JOIN M_EVENTS_MASTERDATA c ON cast(a.CATEGORY_ID as varchar) = c.CODE AND c.TYPE = 'ACTIVITY_CATEGORY'
	INNER JOIN SAM_M_ACTIVITY_STATUS s ON s.ID = a.STATUS_ID AND s.IS_DELETED = 0
	LEFT JOIN SAM_PLAN p ON p.ACTIVITY_ID = a.ID AND p.PLAN_DATE IS NOT NULL AND p.ACTUAL_DATE IS NULL
WHERE 1 = 1
/*IF date != null && date != ''*/
	AND FORMAT(p.PLAN_DATE, 'MM/yyyy') = /*date*/'10/2023'
/*END*/
/*IF statusId != null && statusId != ''*/
	AND s.ID = /*statusId*/'1'
/*END*/
/*IF planDate != null && planDate != ''*/
	AND FORMAT(p.PLAN_DATE, 'dd/MM/yyyy') = /*planDate*/'10/10/2023'
/*END*/
/*IF actCode != null && actCode != ''*/
	AND a.ACT_CODE = /*actCode*/'A000000001'
/*END*/
/*IF createBy != null && createBy != ''*/
	AND a.CREATED_BY = /*createBy*/'ntr.bang'
/*END*/
/*IF zones != null && zones.size() > 0*/ 
	AND l.ZONE_CODE IN /*zones*/()
/*END*/