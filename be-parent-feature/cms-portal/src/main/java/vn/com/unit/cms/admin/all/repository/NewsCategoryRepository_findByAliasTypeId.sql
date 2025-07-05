SELECT mcl.*
    FROM M_NEWS_CATEGORY mc
    join M_NEWS_CATEGORY_LANGUAGE mcl on mcl.M_NEWS_CATEGORY_ID = mc.id
WHERE
mcl.link_alias = /*linkAlias*/
and mcl.m_language_code = /*languageCode*/'vi'
AND mc.m_customer_type_id = /*customerId*/9
/*IF typeId != null*/
AND mc.M_NEWS_TYPE_ID = /*typeId*/
/*END*/
AND mc.ENABLED = 1
AND mc.delete_date IS NULL

