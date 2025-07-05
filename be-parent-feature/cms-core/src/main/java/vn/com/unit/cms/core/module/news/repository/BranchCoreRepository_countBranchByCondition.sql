select COUNT(branch.id) as total
from JCA_M_BRANCH branch
WHERE 1 = 1
AND DELETE_DATE IS NULL
/*IF dto.lstDistrict != null*/
AND branch.DISTRICT IN /*dto.lstDistrict*/()
/*END*/
AND branch.TYPE = /*dto.branch*/''
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