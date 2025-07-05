SELECT
	  cate.id				AS	id
  	, cate.code				AS	code
  	, cateLang.LABEL		AS	title
  	, cate.SORT  			AS	sort
  	, cate.create_date		AS  create_date
FROM M_INTRODUCTION_CATEGORY cate
JOIN M_INTRODUCTION_CATEGORY_LANGUAGE cateLang ON (cate.id = cateLang.M_INTRODUCE_CATEGORY_ID AND cateLang.delete_date is null)
WHERE
	cate.delete_date is null
	AND cate.ENABLED = 1
	AND UPPER(cateLang.m_language_code) = UPPER(/*language*/)
	AND cate.CUSTOMER_TYPE_ID = /*customerTypeId*/
	AND cate.status != 100
ORDER BY cate.SORT ASC