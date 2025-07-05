SELECT
  newsType.ID                         AS  id
  , newsTypeLang.LABEL                as  name
FROM M_NEWS_TYPE newsType
INNER JOIN M_NEWS_TYPE_LANGUAGE newsTypeLang
	ON newsType.ID = newsTypeLang.M_NEWS_TYPE_ID 
  	AND newsTypeLang.DELETE_DATE is NULL
WHERE 
  newsType.DELETE_DATE is NULL
  AND newsType.ENABLED = 1
  AND newsTypeLang.M_LANGUAGE_CODE = UPPER(/*lang*/'VI')
  /*IF customerId != null*/
  AND newsType.M_CUSTOMER_TYPE_ID = /*customerId*/9
  /*END*/
  AND newsType.CHANNEL is null
ORDER BY newsType.SORT ASC