SELECT
  newsCate.ID                         AS  id
  , newsCateLang.LABEL                as  name
  , newsCate.M_CUSTOMER_TYPE_ID       as  customer_type_id
  , newsCate.M_NEWS_TYPE_ID           as  m_news_type_id
  , newsCate.*
FROM M_NEWS_CATEGORY newsCate
JOIN M_NEWS_CATEGORY_LANGUAGE newsCateLang
ON (newsCate.ID = newsCateLang.M_NEWS_CATEGORY_ID 
  AND newsCateLang.DELETE_DATE is NULL
  )
WHERE 
  newsCate.DELETE_DATE is NULL
  AND newsCate.ENABLED = 1
  AND newsCateLang.M_LANGUAGE_CODE = UPPER(/*lang*/'vi')
  /*IF typeId != null*/
  AND newsCate.M_NEWS_TYPE_ID = /*typeId*/
  /*END*/
  /*IF categoryId != null && categoryId != -1*/
  AND newsCate.ID != /*categoryId*/
  /*END*/
  AND newsCate.M_CUSTOMER_TYPE_ID = /*customerId*/