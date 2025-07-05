SELECT investorCategory.*
FROM m_investor_category	investorCategory
WHERE
	investorCategory.delete_date is NULL
	AND investorcategory.enable = 1
	AND investorCategory.code = /*code*/
