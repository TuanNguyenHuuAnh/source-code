SELECT COUNT(*)
FROM M_MENU_FAVORITE
WHERE AGENT_CODE = /*agentCode*/
/*IF favorite != null && favorite.itemId != null && favorite.itemId != ''*/
	AND FUNCTION_CODE = /*favorite.itemId*/
/*END*/
/*IF favorite != null && favorite.link != null && favorite.link != ''*/
	AND LINK = /*favorite.link*/
/*END*/
