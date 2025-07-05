DELETE 
FROM jca_authority m_authority
WHERE m_authority.role_id = /*roleId*/
AND m_authority.item_id in (
			SELECT id 
			FROM JCA_ITEM m_item
			WHERE m_item.function_type = /*functionType*/
			AND ((
				m_item.process_id = /*processId*/
        		)
				OR (
					m_item.process_id IS NULL
					AND m_item.business_id = /*businessId*/
					)
	    	)
	    )