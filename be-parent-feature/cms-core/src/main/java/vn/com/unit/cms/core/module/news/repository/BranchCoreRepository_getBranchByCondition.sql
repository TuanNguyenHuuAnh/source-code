
select 
branch.ID AS ID
, branch.CODE AS CODE
, branch.NAME AS NAME
, branch.ADDRESS AS ADDRESS
, branch.LATITUDE AS LATITUDE
, branch.LONGTITUDE AS LONGTITUDE
, branch.IS_PRIMARY AS IS_PRIMARY
, branch.TYPE AS TYPE
, branch.ICON AS ICON
, branch.PHONE AS PHONE
, branch.FAX AS FAX
, branch.DISTRICT AS DISTRICT
, branch.CITY AS CITY
, branch.EMAIL AS EMAIL
, branch.WORKING_HOURS AS WORKING_HOURS
, branch.NOTE AS NOTE
from JCA_M_BRANCH branch
WHERE 1 = 1
AND DELETE_DATE IS NULL
AND branch.TYPE = /*dto.branch*/''
/*IF dto.lstDistrict != null*/
AND branch.DISTRICT IN /*dto.lstDistrict*/()
/*END*/
/*IF dto.city != null && dto.city != ''*/
AND branch.CITY LIKE concat('%', /*dto.city*/,'%')
/*END*/
/*IF dto.district != null && dto.district != ''*/
AND branch.DISTRICT LIKE concat('%', /*dto.district*/,'%')
/*END*/
/*IF dto.address != null && dto.address != ''*/
AND (branch.ADDRESS LIKE concat('%', /*dto.address*/,'%')
	OR branch.NAME LIKE concat('%', /*dto.address*/,'%')
)
/*END*/
/*IF modeView == 0*/
	and branch.ACTIVE_FLAG = 1
/*END*/
ORDER BY branch.NAME ASC
OFFSET /*offset*/0 ROWS FETCH NEXT /*sizeOfPage*/5 ROWS ONLY