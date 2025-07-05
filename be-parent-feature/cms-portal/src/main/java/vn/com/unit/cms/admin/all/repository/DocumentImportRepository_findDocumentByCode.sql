SELECT
    *
FROM
    M_DOCUMENT doc 
WHERE
    doc.delete_date is null
    AND doc.code = /*docCode*/