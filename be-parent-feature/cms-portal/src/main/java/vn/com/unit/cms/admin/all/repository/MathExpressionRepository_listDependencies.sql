SELECT
    'Danh sách sản phẩm có ' as name, count(*) as product           
FROM
    m_math_expression math
JOIN m_product pro
    ON(pro.delete_date IS NULL
        AND math.id = pro.MATH_EXPRESSION)
WHERE
    math.delete_date IS NULL
    AND math.id = /*mathId*/
    AND pro.status NOT IN /*lstStatus*/()