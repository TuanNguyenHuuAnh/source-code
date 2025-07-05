SELECT
	menu_path.ID				as ID
	, menu_path.DEPTH			as DEPTH
	, menu_path.ANCESTOR_ID		as ANCESTOR_ID
	, menu_path.DESCENDANT_ID	as DESCENDANT_ID
	, menu_path.CREATED_DATE	as CREATED_DATE
	, menu_path.CREATED_ID		as CREATED_ID
	, menu_path.TYPE			AS TYPE
FROM M_PARENT_PATH menu_path
WHERE  
	menu_path.DESCENDANT_ID = /*descendantId*/
	AND TYPE = /*type*/
