SELECT
    investor.id
    , investor.INVESTOR_CATEGORY_PARENT_ID		as	parent_id 
    , investor.create_date
    , investorCatelang.TITLE         as    title
FROM
     m_investor_category investor
LEFT JOIN m_investor_CATEGORY_LANGUAGE investorCatelang
ON (investorCatelang.M_INVESTOR_CATEGORY_ID = investor.ID 
    AND investorCatelang.DELETE_DATE is NULL)
WHERE
	investor.delete_by IS NULL
	AND investor.INVESTOR_CATEGORY_PARENT_ID IN /*parentIds*/()
	AND investorCatelang.M_LANGUAGE_CODE = UPPER(/*lang*/)
	/*IF status != null*/
    AND investor.status = /*status*/
    /*END*/
ORDER BY
   investor.sort ASC, investor.create_date DESC
