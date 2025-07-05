SELECT
    count(*)            AS  countProduct
FROM
    m_math_expression math
JOIN m_product pro
    ON(pro.delete_date IS NULL
        AND math.id = pro.MATH_EXPRESSION)
WHERE
    math.delete_date IS NULL
    AND math.id = /*mathId*/100160
    AND pro.status NOT IN /*lstStatus*/(1, 98, 100)