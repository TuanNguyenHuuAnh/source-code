select  
		count(distinct cate.INVESTOR_CATEGORY_LOCATION_LEFT)  
from M_INVESTOR_CATEGORY cate
where cate.DELETE_DATE is null
		and cate.ENABLE = 1
		and cate.CUSTOMER_TYPE_ID = /*customerId*/
		and cate.INVESTOR_CATEGORY_LOCATION_LEFT = 1