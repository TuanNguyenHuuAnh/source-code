UPDATE m_exchange_rate
SET
display_date = /*exRate.displayDate*/,
buying = /*exRate.buying*/,
transfer = /*exRate.transfer*/,
selling = /*exRate.selling*/,
update_date = /*exRate.updateDate*/,
update_by = /*exRate.updateBy*/
WHERE id = /*exRate.id*/ AND m_currency_id = /*exRate.mCurrencyId*/

