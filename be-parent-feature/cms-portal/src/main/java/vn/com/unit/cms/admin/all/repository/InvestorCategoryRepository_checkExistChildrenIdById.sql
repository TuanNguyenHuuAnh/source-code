select 
    investor.id as id,
    investor.investor_category_parent_id as parent_id
from M_INVESTOR_CATEGORY investor
where INVESTOR.DELETE_DATE is null
      AND INVESTOR.ENABLE = 1
      AND INVESTOR.CUSTOMER_TYPE_ID = /*customerId*/
      AND INVESTOR.INVESTOR_CATEGORY_PARENT_ID = /*id*/
ORDER BY investor.SORT ASC, INVESTOR.CREATE_DATE DESC