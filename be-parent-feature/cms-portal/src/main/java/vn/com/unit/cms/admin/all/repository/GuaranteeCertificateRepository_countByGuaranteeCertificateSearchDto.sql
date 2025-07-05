SELECT
	count(*)
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