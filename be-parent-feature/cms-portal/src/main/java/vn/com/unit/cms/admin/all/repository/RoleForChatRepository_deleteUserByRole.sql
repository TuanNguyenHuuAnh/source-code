update m_role_for_chat
set delete_date = systimestamp,
delete_by = /*user*/
where role_code = /*role*/