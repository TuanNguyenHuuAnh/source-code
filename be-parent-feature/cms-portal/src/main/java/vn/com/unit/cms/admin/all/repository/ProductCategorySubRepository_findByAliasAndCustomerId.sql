SELECT mcl.*
FROM m_product_category_sub mc
join m_product_category_sub_language mcl on mcl.m_product_category_sub_id = mc.id
WHERE
	mcl.link_alias = /*linkAlias*/'test'
    and mcl.m_language_code = /*languageCode*/'vi'
	AND mc.m_customer_type_id = /*customerId*/9
	AND mc.m_product_category_id = /*typeId*/
	AND mc.ENABLED = 1
	AND mc.delete_date IS NULL
