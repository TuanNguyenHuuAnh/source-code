SELECT investor.*
FROM m_investor	investor
WHERE
	investor.delete_date is NULL
	AND investor.enabled = 1
	AND investor.code = /*code*/