SELECT
    tb2.fullname
FROM
    m_role_for_chat tb1
    , jca_m_account tb2
WHERE
    tb1.role_code = /*role*/
    AND tb1.username = tb2.username 
    AND tb1.delete_date is null