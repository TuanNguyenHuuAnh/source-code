SELECT mcl.*
    FROM M_NEWS mc
    join M_NEWS_LANGUAGE mcl on mcl.M_NEWS_ID = mc.id
WHERE
	mcl.link_alias = /*linkAlias*/
	and mcl.m_language_code = /*languageCode*/
	AND mc.customer_type_id = /*customerId*/
	/*IF categoryId != null*/
	AND mc.M_NEWS_CATEGORY_ID = /*categoryId*/
	/*END*/
	/*IF typeId != null*/
	AND mc.M_NEWS_TYPE_ID = /*typeId*/
	/*END*/
AND mc.ENABLED = 1
AND mc.delete_date IS NULL
