select
	  *
from m_document_type
where
	delete_date is null
	and enabled = 1
	and m_customer_type_id = /*typeId*/9
	/*IF idExpect != null*/
    and id != /*idExpect*/
    /*END*/
	order by sort_order asc