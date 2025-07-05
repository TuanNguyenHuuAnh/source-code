SELECT COUNT(1)
FROM M_APPOINTMENT A
WHERE 1 = 1
/*IF agentCode != null && agentCode != ''*/
  AND A.CREATED_BY = /*agentCode*/'131597'
/*END*/
/*IF appointmentDate != null*/
  AND format(A.APPOINTMENT_TIME, 'yyyy-MM-dd HH:mm') = format(/*appointmentDate*/'', 'yyyy-MM-dd HH:mm')
/*END*/
/*IF purposeCode != null && purposeCode != ''*/
  AND A.PURPOSE_CODE = /*purposeCode*/''
/*END*/
/*IF clientId != null && clientId != ''*/
  AND A.CLIENT_ID = /*clientId*/''
/*END*/