SELECT mcl.*
FROM m_faqs_category mc
join m_faqs_category_language mcl on mcl.m_faqs_category_id = mc.id
WHERE
	mcl.keywords_seo = /*linkAlias*/
    and mcl.m_language_code = /*languageCode*/'vi'
	AND mc.m_customer_type_id = /*customerId*/9
	AND mc.ENABLED = 1
	AND mc.delete_date IS NULL
