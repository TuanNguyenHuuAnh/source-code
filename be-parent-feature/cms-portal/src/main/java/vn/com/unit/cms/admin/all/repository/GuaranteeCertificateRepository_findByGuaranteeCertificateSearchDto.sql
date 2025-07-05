SELECT
	guarante.id				AS	id,
	guarante.certificate_number AS certificate_number,
	guarante.certificate_type AS certificate_type,
	guarante.issue_date AS issue_date,
	guarante.guarantee_amount AS guarantee_amount,
	guarante.guarantee_certificate_duration AS guarantee_certificate_duration,
	guarante.guarantee_certificate_duration_type AS guarantee_certificate_duration_type,
	guarante.guarantee AS guarantee,
	guarante.beneficiary AS beneficiary,
	guarante.create_date		AS  create_date
	
FROM  m_guarantee_certificate guarante
WHERE
guarante.delete_date is null
/*BEGIN*/ AND ( 
	/*IF searchCond.certificateNumber != null && searchCond.certificateNumber != ''*/
	OR guarante.certificate_number LIKE concat('%',  /*searchCond.certificateNumber*/, '%')
	/*END*/
	/*IF searchCond.certificateType != null && searchCond.certificateType != ''*/
	OR guarante.certificate_type LIKE concat('%',  /*searchCond.certificateType*/, '%')
	/*END*/
	/*IF searchCond.guarantee != null && searchCond.guarantee != ''*/
	OR guarante.guarantee LIKE concat('%',  /*searchCond.guarantee*/, '%')
	/*END*/
	/*IF searchCond.beneficiary != null && searchCond.beneficiary != ''*/
	OR guarante.beneficiary LIKE concat('%',  /*searchCond.beneficiary*/, '%')
	/*END*/
	
	 )/*END*/
	 
ORDER BY guarante.create_date DESC
LIMIT /*sizeOfPage*/ OFFSET /*offset*/