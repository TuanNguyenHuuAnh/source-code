SELECT
	    COUNT(*)
	FROM
	    m_shareholders
	WHERE 
	delete_by is NULL
	/*BEGIN*/
	AND
		(
			/*IF condition.name != null && condition.name != ''*/
			OR
			replace(name,' ','') like CONCAT('%',/*condition.name*/,'%')
			/*END*/
			/*IF condition.code != null && condition.code != ''*/
			OR
			replace(code,' ','') like CONCAT('%',/*condition.code*/,'%')
			/*END*/
			/*IF condition.address != null && condition.address != ''*/
			OR
			replace(address,' ','')  like CONCAT('%',/*condition.address*/,'%')
			/*END*/
			/*IF condition.placeOfIssue != null && condition.placeOfIssue != ''*/
			OR
			replace(place_of_issue,' ','') like CONCAT('%',/*condition.placeOfIssue*/,'%')
			/*END*/
			/*IF condition.dateOfIssue != null && condition.dateOfIssue != ''*/
			OR
			replace(date_of_issue,' ','') like CONCAT('%',/*condition.dateOfIssue*/,'%')
			/*END*/
			/*IF condition.ownedSharesAmount != null && condition.ownedSharesAmount != ''*/
			OR
			replace(owned_shares_amount,' ','') like CONCAT('%',/*condition.ownedSharesAmount*/,'%')
			/*END*/
			/*IF condition.dividendAmount != null && condition.dividendAmount != ''*/
			OR
			replace(dividend_amount,' ','') like CONCAT('%',/*condition.dividendAmount*/,'%')
			/*END*/
			/*IF condition.idNumber != null && condition.idNumber != ''*/
			OR
			replace(id_number,' ','') like CONCAT('%',/*condition.idNumber*/,'%')
			/*END*/
			/*IF condition.status != null && condition.status != ''*/
			OR
			replace(status,' ','') like CONCAT('%',/*condition.status*/,'%')
			/*END*/
		)
	/*END*/


