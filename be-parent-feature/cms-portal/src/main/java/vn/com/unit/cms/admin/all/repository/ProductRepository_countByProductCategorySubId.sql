SELECT COUNT(*) FROM M_PRODUCT m 
WHERE m.delete_date is null AND m.STATUS != 100
AND m.M_PRODUCT_CATEGORY_SUB_ID = /*productCategorySubId*/