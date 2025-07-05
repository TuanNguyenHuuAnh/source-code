SELECT *
FROM M_NEWS_TYPE newsType
JOIN M_NEWS_TYPE_LANGUAGE newsTypeLang
ON (newsType.ID = newsTypeLang.M_NEWS_TYPE_ID 
  AND newsTypeLang.DELETE_DATE is NULL
  )
WHERE 
  newsType.DELETE_DATE is NULL
  AND newsType.ENABLED = 1
  AND newsTypeLang.M_LANGUAGE_CODE = UPPER(/*lang*/)
  AND newsType.M_CUSTOMER_TYPE_ID = /*customerId*/
  /*IF typeId != null && typeId != -1*/
  AND newsType.id != /*typeId*/
  /*END*/
ORDER BY  newsType.sort ASC, newsType.create_date DESC, newsType.ENABLED DESC, newsTypeLang.label