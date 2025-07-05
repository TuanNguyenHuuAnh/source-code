SELECT
	investorCate.INVESTOR_CATEGORY_PARENT_ID				AS parent_id
    , investorCate.*
    , investorCatelang.TITLE         						AS    title
FROM m_investor_category investorCate
LEFT JOIN M_investor_CATEGORY_LANGUAGE investorCatelang
ON (investorCatelang.M_INVESTOR_CATEGORY_ID = investorCate.ID
    AND investorCatelang.DELETE_DATE is NULL)
WHERE investorCate.delete_by is NULL
	AND investorCate.INVESTOR_CATEGORY_PARENT_ID  = 0
	AND investorCate.ENABLE = 1
	AND UPPER(investorCatelang.M_LANGUAGE_CODE) = UPPER(/*language*/)
	/*IF status != null*/
	AND investorCate.status = /*status*/
	/*END*/
ORDER BY
     investorCate.sort ASC, investorCate.create_date DESC, investorCate.code ASC