select
	it.function_code								as function_code
	, co.comp_id									as comp_id
	, co.comp_name									as comp_name
    , m_author_pivot.can_access_flg					as can_access_flg
    , m_author_pivot.can_disp_flg					as can_disp_flg
    , m_author_pivot.can_edit_flg					as can_edit_flg
from vw_component_authority_pivot m_author_pivot
left join efo_component co on m_author_pivot.comp_id = co.id and co.DELETED_ID = 0
left join efo_form fr on co.form_id = fr.id
left join JCA_ITEM it on it.id = m_author_pivot.item_id
where co.id is not null
	and it.id is not null
	and fr.id = /*formId*/0
order by
	fr.display_order,fr.name,
	co.display_order, co.comp_name;