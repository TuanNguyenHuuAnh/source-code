SELECT p.id,
	p.M_CUSTOMER_TYPE_ID type_id,
    pl.TITLE name
FROM m_product p 
join M_PRODUCT_LANGUAGE pl on pl.M_PRODUCT_ID = p.id
WHERE p.delete_date is null
AND p.IS_MICROSITE = 1
and pl.M_LANGUAGE_CODE = UPPER(/*language*/)
and rownum = 1
	
