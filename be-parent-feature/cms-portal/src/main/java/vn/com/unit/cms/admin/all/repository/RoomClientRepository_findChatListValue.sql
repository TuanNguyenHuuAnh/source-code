SELECT * 
FROM 
    m_setting_chat_control_value
WHERE
    field_code = /*field*/'OCONTENT'
    AND UPPER(M_LANGUAGE_CODE) = UPPER(/*lang*/)