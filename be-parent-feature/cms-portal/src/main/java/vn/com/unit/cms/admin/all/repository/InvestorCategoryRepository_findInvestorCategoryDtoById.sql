SELECT
    	investor.id 
    , investor.INVESTOR_CATEGORY_PARENT_ID			as	parent_id 
    , investor.create_date
    , investorCatelang.TITLE         				as  title
    , investor.INVESTOR_CATEGORY_LEVEL_ID			as	level_id
    , investor.INVESTOR_CATEGORY_TYPE				as	category_type
FROM
     m_investor_category investor
LEFT JOIN m_investor_category_LANGUAGE investorCatelang
    ON (investorCatelang.M_INVESTOR_CATEGORY_ID = investor.ID 
        AND investorCatelang.DELETE_DATE is NULL
    )
WHERE investor.delete_by is NULL
    AND investor.id = /*id*/
    AND investor.enable = 1
    /*IF status != null*/
    AND investor.status = /*status*/
    /*END*/
    AND UPPER(investorCatelang.M_LANGUAGE_CODE) = UPPER(/*language*/'')