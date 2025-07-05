SELECT mcl.*
    FROM M_DOCUMENT_TYPE mc
    join M_DOCUMENT_TYPE_language mcl on mcl.M_DOCUMENT_TYPE_ID = mc.id
WHERE
mcl.link_alias = /*linkAlias*/
and mcl.m_language_code = /*languageCode*/'vi'
AND mc.m_customer_type_id = /*customerId*/9
AND mc.ENABLED = 1
AND mc.delete_date IS NULL
