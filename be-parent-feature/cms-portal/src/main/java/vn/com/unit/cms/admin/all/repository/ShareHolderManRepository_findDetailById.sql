SELECT
	  id, code, name, address, status, id_number, date_of_issue, place_of_issue, sort,
	  owned_shares_amount, dividend_amount, status, process_id, process_intance_id, create_date
	FROM
	    m_shareholders
	WHERE 
		id = /*id*/
		AND 
		delete_by is NULL