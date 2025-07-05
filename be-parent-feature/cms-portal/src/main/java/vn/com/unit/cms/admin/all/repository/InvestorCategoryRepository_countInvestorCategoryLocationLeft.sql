select  
  --decode(count(cate.INVESTOR_CATEGORY_LOCATION_LEFT), 0, 'NO', 'YES') as count_location_left
  	case when count(cate.INVESTOR_CATEGORY_LOCATION_LEFT)  = 0 then 'NO' else 'YES' end as count_location_left
from M_INVESTOR_CATEGORY cate
	where EXISTS(select  *
	from M_INVESTOR_CATEGORY cate
	where
				cate.INVESTOR_CATEGORY_LOCATION_LEFT = 1
	    	and cate.DELETE_DATE is null
			and cate.ENABLE = 1
			and cate.CUSTOMER_TYPE_ID = /*customerId*/
		    /*IF id != null*/
		    and cate.id = /*id*/
		    /*END*/
		    )