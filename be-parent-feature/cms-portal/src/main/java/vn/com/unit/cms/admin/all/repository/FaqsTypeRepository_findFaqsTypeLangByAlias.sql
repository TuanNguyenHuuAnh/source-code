SELECT mcl.*
FROM m_faqs_type mc
join m_faqs_type_language mcl on mcl.M_FAQS_TYPE_ID = mc.id
WHERE
	mcl.link_alias = /*linkAlias*/
    and mcl.m_language_code = /*languageCode*/'vi'
	AND mc.m_customer_type_id = /*customerId*/9
	AND mc.ENABLED = 1
	AND mc.delete_date IS NULL
