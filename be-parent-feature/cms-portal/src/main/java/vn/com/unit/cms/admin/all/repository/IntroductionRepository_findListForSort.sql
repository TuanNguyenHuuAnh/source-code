SELECT
	  intro.id				AS	id
  	, clanguage.title		AS	text
  	, clanguage.title		AS	name
FROM m_introduction intro
LEFT JOIN m_introduction_language clanguage ON (intro.id = clanguage.M_INTRODUCTION_ID AND clanguage.delete_date is null)
WHERE
	intro.delete_date is null
	AND intro.ENABLED = 1
	AND UPPER(clanguage.m_language_code) = UPPER(/*languageCode*/'vi')
    AND intro.CUSTOMER_TYPE_ID = /*customerId*/11
	/*IF categoryId != null*/	
	AND intro.M_INTRODUCTION_CATEGORY_ID = /*categoryId*/100049
	/*END*/
	/*IF id != null*/
	AND intro.id != /*id*/100045
	/*END*/
ORDER BY intro.sort