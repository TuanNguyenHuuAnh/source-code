SELECT
    tb1.field_code,
	tb2.field_name,
	tb1.sort,
	tb1.type,
	tb1.is_display,
	tb1.is_visible,
	tb2.label_name,
	tb2.placeholder_name,
	tb2.m_language_code,
	tb1.field_type,
	tb1.is_default
FROM m_setting_chat tb1
LEFT JOIN m_setting_chat_language tb2
    ON (tb1.field_code = tb2.field_code)
WHERE
	tb1.delete_date is null
	AND tb1.type = /*type*/
ORDER BY tb1.SORT