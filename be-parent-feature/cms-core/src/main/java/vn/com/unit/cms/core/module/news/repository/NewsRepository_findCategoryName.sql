
SELECT count(*)
FROM M_NEWS new
WHERE new.M_NEWS_CATEGORY_ID = /*categoryId*/'100030' 
AND	new.M_NEWS_TYPE_ID = /*typeId*/'100004'
/*IF newsId != NULL && newsId != '' */
	AND	(/*newsId*/ <> id)
/*END*/
AND HOT = 1
AND delete_date is NULL
