SELECT
  max(sort)
FROM m_product
WHERE
	delete_date is null
	AND m_customer_type_id = /*typeId*/
	AND m_product_category_id = /*categoryId*/
	/*IF categorySubId != null*/
	AND m_product_category_sub_id = /*categorySubId*/
	/*END*/
	/*IF categorySubId == null*/
	AND m_product_category_sub_id is null
	/*END*/
	

