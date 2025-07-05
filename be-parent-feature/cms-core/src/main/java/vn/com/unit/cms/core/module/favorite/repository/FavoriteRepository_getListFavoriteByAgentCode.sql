SELECT 
	 F.ID 				AS ID
	,M.ID				AS MENU_ID
	,F.TYPE				AS TYPE
	,F.TITLE			AS TITLE
	,F.LINK				AS LINK
	,F.NAMED			AS NAME
	/*IF channel != null && channel == 'AD'*/
	,I.ICON_PATH		AS ICON
	-- ELSE ,I.ICON_PATH_AG	AS ICON
	/*END*/
	,F.ICON				AS ICON
	,F.FUNCTION_CODE	AS ITEM_ID
	,case when M.URL like 'http%' then 'false' else 'true' end as LOCAL_LINK
	,F.DEFAULT_FLAG		AS DEFAULT_FLAG
FROM
	M_MENU_FAVORITE F
inner join JCA_ITEM I on F.FUNCTION_CODE = I.FUNCTION_CODE
inner join JCA_MENU M on M.ITEM_ID = I.ID
/*IF channel != null && channel == 'AD'*/
and M.MENU_MODULE = 'FE'
-- ELSE and M.MENU_MODULE = 'FE-AG'
/*END*/
WHERE
	F.AGENT_CODE = /*agentCode*/
AND ISNULL(F.DEFAULT_FLAG, 0) = 0