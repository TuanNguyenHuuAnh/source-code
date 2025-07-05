SELECT * 
FROM m_exchange_rate
WHERE delete_by IS NULL
/*IF exRateDto.displayDate != null*/
   AND (DATEDIFF(display_date , /*exRateDto.displayDate*/) = 0)
/*END*/
ORDER BY display_date desc
LIMIT /*sizeOfPage*/ OFFSET /*offset*/