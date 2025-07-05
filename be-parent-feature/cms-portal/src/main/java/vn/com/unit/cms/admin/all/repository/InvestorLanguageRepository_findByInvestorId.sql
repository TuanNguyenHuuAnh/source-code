SELECT *
FROM m_investor_language
WHERE delete_date is null
	AND m_investor_id = /*investorId*/