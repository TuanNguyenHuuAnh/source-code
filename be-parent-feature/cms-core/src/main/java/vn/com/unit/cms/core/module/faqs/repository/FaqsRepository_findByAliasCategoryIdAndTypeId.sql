SELECT mcl.*
    FROM m_faqs mc
    join m_faqs_language mcl on mcl.M_FAQS_ID = mc.id
WHERE
mcl.link_alias = /*linkAlias*/9
and mcl.m_language_code = /*languageCode*/'vi'
AND mc.m_customer_type_id = /*customerId*/9
/*IF categoryId != null*/
AND mc.m_faqs_category_id = /*categoryId*/9
/*END*/
/*IF typeId != null*/
AND mc.m_faqs_type_id = /*typeId*/9
/*END*/
AND mc.ENABLED = 1
AND mc.delete_date IS NULL
