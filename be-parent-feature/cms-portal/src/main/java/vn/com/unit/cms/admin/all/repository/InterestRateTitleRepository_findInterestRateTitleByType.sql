select mirt.id,
	mirt.interest_rate_type,
	mirt.language_code,
	mirt.title,
    mirt.m_customer_type_id as customer_type_id,
    ROW_NUMBER() OVER (PARTITION BY mirt.language_code ORDER BY mirt.id) AS orderLanguage
from m_interest_rate_title mirt
join jca_m_language jml on upper(jml.code) = upper(mirt.language_code)
where mirt.delete_date is null and mirt.delete_by is null
and interest_rate_type = /*type*/
/*IF customerTypeId != null*/
and m_customer_type_id = /*customerTypeId*/
/*END*/
order by interest_rate_type, orderLanguage, jml.sort