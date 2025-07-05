select tb1.id, tb1.code,tb2.title as name
from m_currency tb1, m_currency_language tb2
where tb1.id = tb2.m_currency_id
and tb1.delete_date is null 
and tb1.id = /*id*/
and UPPER(tb2.m_language_code) = UPPER(/*languageCode*/)