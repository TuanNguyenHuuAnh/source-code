UPDATE
	    m_shareholders
	    SET
	    name = /*sHolderObj.name*/,
	    address = /*sHolderObj.address*/,
	    id_number = /*sHolderObj.idNumber*/,
	    date_of_issue = /*sHolderObj.dateOfIssue*/,
	    place_of_issue = /*sHolderObj.placeOfIssue*/,
	    owned_shares_amount = /*sHolderObj.ownedSharesAmount*/,
	    dividend_amount = /*sHolderObj.dividendAmount*/,
	    status = /*sHolderObj.status*/
WHERE 
		id = /*sHolderObj.id*/
