SELECT
	 product.id					AS	id,
  	 product.code				AS	code,
  	 (CASE product.m_customer_type_id 
 	  	WHEN 9 THEN CONCAT(N'Cá nhân - ',title)
  		WHEN 10 THEN CONCAT(N'Doanh nghiệp - ',title)
  		ELSE title END) AS	title,
     product.m_customer_type_id AS customer_id
FROM m_product product
JOIN m_product_language productLan ON (productLan.m_product_id = product.id AND productLan.delete_date is null)
WHERE
	product.delete_date is null
	AND UPPER(productLan.m_language_code) = UPPER(/*languageCode*/)	
	AND product.is_microsite = 1
ORDER BY upper(productLan.title) ASC