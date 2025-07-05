SELECT mcl.*
FROM m_product_category mc
join M_PRODUCT_CATEGORY_LANGUAGE mcl on mcl.M_PRODUCT_CATEGORY_ID = mc.id
WHERE
	mcl.link_alias = /*linkAlias*/'test'
    and mcl.m_language_code = /*languageCode*/9
	AND mc.m_customer_type_id = /*customerId*/9
	AND mc.ENABLED = 1
	AND mc.delete_date IS NULL
