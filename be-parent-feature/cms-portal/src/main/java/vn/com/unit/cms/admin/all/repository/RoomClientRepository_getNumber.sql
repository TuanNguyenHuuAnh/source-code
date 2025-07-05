SELECT count(id) 
FROM M_SETTING_CHAT
WHERE
	type = /*type*/ 
	AND DELETE_BY is NULL
	AND FIELD_CODE LIKE (/*typeString*/ ||'%')