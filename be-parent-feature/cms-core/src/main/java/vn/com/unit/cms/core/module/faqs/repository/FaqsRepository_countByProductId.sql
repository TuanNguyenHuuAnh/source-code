SELECT COUNT(*) FROM M_FAQS m 
WHERE m.delete_date is null AND m.STATUS != 100
AND UPPER(m.M_PRODUCT_ID) LIKE ('%'||UPPER(TRIM(/*productId*/))||'%')