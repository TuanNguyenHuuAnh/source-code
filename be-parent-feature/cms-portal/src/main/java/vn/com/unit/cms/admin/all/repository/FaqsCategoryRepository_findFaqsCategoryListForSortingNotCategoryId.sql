SELECT
  faqsCate.ID                         AS  id
  , faqsCateLang.TITLE                as  name
  , faqsCate.M_CUSTOMER_TYPE_ID       as  customer_type_id
  , faqsCate.M_faqs_TYPE_ID           as  m_faqs_type_id
  , faqsCate.*
FROM M_faqs_CATEGORY faqsCate
JOIN M_faqs_CATEGORY_LANGUAGE faqsCateLang
ON (faqsCate.ID = faqsCateLang.M_faqs_CATEGORY_ID 
  AND faqsCateLang.DELETE_DATE is NULL
  )
WHERE 
  faqsCate.DELETE_DATE is NULL
  AND faqsCate.ENABLED = 1
  AND faqsCateLang.M_LANGUAGE_CODE = UPPER(/*lang*/'vi')
    AND faqsCate.M_CUSTOMER_TYPE_ID = /*customerId*/9
  /*IF typeId != null && typeId != -1*/
  AND faqsCate.M_faqs_TYPE_ID = /*typeId*/
  /*END*/
  /*IF categoryId != null && categoryId != -1*/
  AND faqsCate.ID != /*categoryId*/
  /*END*/
ORDER BY faqsCateLang.TITLE ASC