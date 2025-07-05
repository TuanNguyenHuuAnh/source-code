select org.ZONE_CODE, count(distinct ac.ID) as NUMBER_WAITING_APPROVAL
from SAM_ACTIVITY ac
inner join SAM_ORGANIZATION_LOCATION org
on ac.ID = org.ACTIVITY_ID
where ac.STATUS_ID = 2
and org.ZONE_CODE is not null
and CHARINDEX(',', org.BU_CODE) = 0	-- Ignore error code.
group by org.ZONE_CODE