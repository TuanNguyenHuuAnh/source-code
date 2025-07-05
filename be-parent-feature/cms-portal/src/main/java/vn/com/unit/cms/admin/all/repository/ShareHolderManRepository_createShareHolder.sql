UPDATE
	    m_shareholders
	    SET
	    name = /*s_holder_obj.name*/,
	    id_number = /*s_holder_obj.idNumber*/,
	    date_of_issue = /*s_holder_obj.dateOfIssue*/,
	    place_of_issue = /*s_holder_obj.placeOfIssue*/,
	    owned_shares_amount = /*s_holder_obj.ownedSharesAmount*/,
	    dividend_amount_amount = /*s_holder_obj.dividendAmount*/,
	    status = /*s_holder_obj.status*/
WHERE 
		code = /*condition.code*/