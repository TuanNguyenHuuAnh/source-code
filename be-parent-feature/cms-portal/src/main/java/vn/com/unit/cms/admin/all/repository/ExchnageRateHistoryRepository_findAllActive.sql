SELECT * 
FROM t_exchange_rate_history
WHERE create_date IS NOT NULL
/*IF exRateDto.displayDate != null*/
   AND (DATEDIFF(update_date_time,/*exRateDto.displayDate*/) = 0)
/*END*/
ORDER BY update_date_time desc
LIMIT /*sizeOfPage*/ OFFSET /*offset*/