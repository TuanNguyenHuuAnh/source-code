select  
  --decode(count(cate.INVESTOR_CATEGORY_LOCATION_RIGHT_TOP), 0, 'NO', 'YES') as count_location_right_top
  case when count(cate.INVESTOR_CATEGORY_LOCATION_RIGHT_TOP)  = 0 then 'NO' else 'YES' end as count_location_right_top
from M_INVESTOR_CATEGORY cate
where EXISTS(select  *
from M_INVESTOR_CATEGORY cate
where
			cate.INVESTOR_CATEGORY_LOCATION_RIGHT_TOP = 1
    	and cate.DELETE_DATE is null
		and cate.ENABLE = 1
		and cate.CUSTOMER_TYPE_ID = /*customerId*/
	    /*IF id != null*/
	    and cate.id = /*id*/
	    /*END*/
	   )