SELECT * 
FROM m_exchange_rate
WHERE m_currency_id = /*mcurrencyId*/
AND  DATEDIFF(display_date , /*displayDate*/) = 0