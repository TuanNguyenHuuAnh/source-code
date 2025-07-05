SELECT COUNT(1)
FROM M_APPOINTMENT A
WHERE 1 = 1
/*IF agentCode != null && agentCode != ''*/
  AND A.CREATED_BY = /*agentCode*/'131597'
/*END*/
/*IF appointmentDate != null*/
  AND CONVERT(DATE, A.APPOINTMENT_TIME) = CONVERT(DATE, /*appointmentDate*/'2025-05-13')
/*END*/