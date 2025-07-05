DELETE 
FROM JCA_AUTHORITY
WHERE role_id = /*roleId*/
AND item_id in (
			SELECT id 
			FROM jca_item m_item
			WHERE 0 = 0 
			/*IF functionType == 1*/
		    AND m_item.function_type IN ('1','2')
		    /*END*/
		    
		    /*IF functionType != 1*/
		    AND m_item.function_type = /*functionType*/
		    /*END*/
	    )