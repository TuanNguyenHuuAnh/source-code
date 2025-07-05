SELECT
    *
FROM
    M_DOCUMENT doc 
WHERE
    doc.delete_date is null
    AND doc.code = /*docCode*/
    AND doc.M_CATEGORY_ID =/*categoryId*/