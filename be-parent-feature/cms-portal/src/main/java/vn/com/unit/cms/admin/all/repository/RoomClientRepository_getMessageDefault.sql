select tb2.placeholder_name 
from m_setting_chat tb1, m_setting_chat_language tb2
where tb1.field_code = tb2.field_code
and UPPER(tb2.m_language_code) =UPPER(/*lang*/)
and tb1.field_code = 'MSG_WELCOME';