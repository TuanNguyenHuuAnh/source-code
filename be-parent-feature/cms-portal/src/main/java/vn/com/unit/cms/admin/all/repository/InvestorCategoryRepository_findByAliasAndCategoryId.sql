select invescatelang.id as id,
	   invescatelang.m_investor_category_id  as category_id,
       invescatelang.m_language_code as language_code,
       invescatelang.title as label,
       invescatelang.short_content as short_content,
       invescatelang.link_alias as link_alias,
       invescatelang.key_word as key_word,
       invescatelang.description_keyword as description_keyword
from m_investor_category invescate
left join m_investor_category_language invescatelang
	on (invescatelang.delete_date is null
		and invescatelang.m_investor_category_id = invescate.id
		and UPPER(invescatelang.m_language_code) = UPPER(/*language*/'')
	)
where
	invescate.delete_date is null
	and invescate.customer_type_id = /*customerId*/
	and UPPER(invescatelang.link_alias) = UPPER(/*linkAlias*/'')