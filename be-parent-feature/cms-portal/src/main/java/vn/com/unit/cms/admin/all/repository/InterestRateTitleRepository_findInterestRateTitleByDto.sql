select mirt.id,
	mirt.interest_rate_type,
	mirt.language_code,
	mirt.title,
    mirt.m_customer_type_id as customer_type_id
from m_interest_rate_title mirt
where mirt.delete_date is null and mirt.delete_by is null 
and mirt.interest_rate_type = /*dto.interestRateType*/
/*IF dto.customerTypeId != null*/
and mirt.m_customer_type_id = /*dto.customerTypeId*/
/*END*/
/*IF dto.languageCode != null*/
and mirt.language_code = UPPER(/*dto.languageCode*/)
/*END*/
order by interest_rate_type, mirt.id