DELETE 
FROM jca_authority m_authority
WHERE m_authority.role_id = /*roleId*/
AND m_authority.item_id in (
			SELECT id 
			FROM JCA_ITEM m_item
			WHERE 0 = 0 
			AND m_item.function_type = /*functionType*/
	    )