UPDATE m_product_category
SET sort = /*cond.sortValue*/
WHERE id = /*cond.objectId*/
	AND delete_date IS NULL