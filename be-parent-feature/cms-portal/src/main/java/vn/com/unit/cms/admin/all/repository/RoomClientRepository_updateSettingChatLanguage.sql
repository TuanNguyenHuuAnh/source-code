update m_setting_chat_language 
set field_name = /*field_name*/, 
label_name = /*label*/,
placeholder_name = /*placeHolder*/,
update_by = /*username*/,
update_date = CURRENT_DATE
where field_code = /*field*/ 
and m_language_code = /*language*/