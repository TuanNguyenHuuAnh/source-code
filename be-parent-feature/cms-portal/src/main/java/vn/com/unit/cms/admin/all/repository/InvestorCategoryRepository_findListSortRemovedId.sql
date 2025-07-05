SELECT
  investorCate.*,
  investorCatelang.TITLE         as    title
  
FROM M_INVESTOR_CATEGORY investorCate
LEFT JOIN M_INVESTOR_CATEGORY_LANGUAGE investorCatelang
ON (investorCatelang.M_INVESTOR_CATEGORY_ID = investorCate.ID 
    AND investorCatelang.DELETE_DATE is NULL)
    
WHERE investorCate.DELETE_DATE is NULL
  AND investorCate.enable = 1
  AND UPPER(investorCatelang.M_LANGUAGE_CODE) = UPPER(/*lang*/'')
  
  /*IF id != null && id != ''*/
  AND investorCate.ID != /*id*/
  /*END*/
  
  /*IF parentId == null || parentId == '' || parentId == -1*/
    AND investorCate.INVESTOR_CATEGORY_PARENT_ID is NULL
  /*END*/
    
  /*IF parentId != null && parentId != '' && parentId != -1*/
  AND investorCate.INVESTOR_CATEGORY_PARENT_ID = /*parentId*/
  /*END*/
ORDER BY investorCate.sort asc, investorCate.create_date desc