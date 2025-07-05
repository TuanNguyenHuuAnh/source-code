SELECT *
FROM m_product_language
WHERE delete_date is null
	AND m_product_id = /*productId*/