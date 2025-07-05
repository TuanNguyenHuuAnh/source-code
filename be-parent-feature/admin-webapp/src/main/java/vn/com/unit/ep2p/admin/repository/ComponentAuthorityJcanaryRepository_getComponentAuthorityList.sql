select
	/*itemId*/										as item_id
	,co.id											as comp_id
	,co.comp_name									as comp_name
    , m_author_pivot.can_access_flg					as can_access_flg
    , m_author_pivot.can_disp_flg					as can_disp_flg
    , m_author_pivot.can_edit_flg					as can_edit_flg
from efo_component co
left join efo_form fr on co.form_id = fr.id and fr.DELETED_ID = 0 and fr.ACTIVED = 1
left join
    vw_component_authority_pivot m_author_pivot
on
    m_author_pivot.comp_id = co.id
    and m_author_pivot.item_id = /*itemId*/0
where
    co.DELETED_ID = 0
    /*IF formId != null*/
	and fr.id = /*formId*/0
	/*END*/    
order by
	co.display_order,
	co.comp_name;