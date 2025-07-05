select ((case  when value01 is not null then 1 else 0 end)
    + (case  when value02 is not null then 1 else 0 end)
    + (case  when value03 is not null then 1 else 0 end)
    + (case  when value04 is not null then 1 else 0 end)
    + (case  when value05 is not null then 1 else 0 end)
    + (case  when value06 is not null then 1 else 0 end)
    + (case  when value07 is not null then 1 else 0 end)
    + (case  when value08 is not null then 1 else 0 end)
    + (case  when value09 is not null then 1 else 0 end)
    + (case  when value10 is not null then 1 else 0 end)
    ) as total_title
from m_interest_rate_value
where delete_by is null and delete_date is null
and interest_rate_type = /*interestRateType*/
--and rownum = 1