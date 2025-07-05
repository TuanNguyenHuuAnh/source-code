SELECT
	  product.id 										AS	value
	,(product.code||' - '||productLan.title)		AS	name
										   
FROM m_product product
JOIN m_product_language productLan ON (productLan.m_product_id = product.id
						               AND productLan.delete_date IS NULL)
WHERE
	product.delete_date IS NULL
	AND product.m_customer_type_id = /*customerId*/
	AND UPPER(productLan.m_language_code) = UPPER(/*languageCode*/)
ORDER BY product.sort, product.id

