DELETE 
FROM jca_authority authority
WHERE authority.role_id = /*roleId*/
AND authority.item_id in (
			SELECT id 
			FROM jca_item item
			WHERE 0 = 0 
			/*IF funcType == 1*/
		    AND item.function_type IN ('1','2')
		    /*END*/
		    
		    /*IF funcType != 1*/
		    AND item.function_type = /*funcType*/
		    /*END*/
	    );