SELECT
	  product.id				AS	id
  	, product.code				AS	code
  	, product.name				AS	name
  	, product.image_url			AS	image_url
  	, physical_img				AS  physical_img
  	, product.key_word			AS	key_word
  	, product.enabled			AS	enabled
  	, product.process_id		AS	process_id
  	, product.status			AS	status
  	, category.title			AS  category_name
  	, productType.title 		AS  type_name
  	, productLan.title			AS	title
  	, productLan.short_content	AS	short_content
  	, product.description		AS	description
  	, product.create_date		AS  create_date
  	, categorySub.title			AS	category_sub_name
FROM m_product product
JOIN m_product_language productLan ON (productLan.m_product_id = product.id AND productLan.delete_date is null)
JOIN m_product_category_language category ON (category.m_product_category_id = product.m_product_category_id 
											  AND category.m_language_code = productLan.m_language_code
											  AND category.delete_date is null)
JOIN m_customer_type_language productType ON (productType.m_customer_type_id = product.m_customer_type_id 
											  AND productType.m_language_code = productLan.m_language_code
											  AND productType.delete_date is null
											)
LEFT JOIN m_product_category_sub_language categorySub ON (categorySub.m_product_category_sub_id = product.m_product_category_sub_id 
														  AND categorySub.m_language_code = productLan.m_language_code
														  AND categorySub.delete_date is null)
WHERE
	product.delete_date is null
	AND product.ENABLED = 1
	AND UPPER(productLan.m_language_code) = UPPER(/*languageCode*/)
	/*IF typeId != null */
	AND product.m_customer_type_id = /*typeId*/
	/*END*/	
	/*IF categorySubListId != null*/
	AND product.m_product_category_sub_id = /*categorySubListId*/
	/*END*/	
	
	/*IF status != null*/
        AND product.status = /*status*/
    /*END*/
ORDER BY productLan.title asc, product.sort asc