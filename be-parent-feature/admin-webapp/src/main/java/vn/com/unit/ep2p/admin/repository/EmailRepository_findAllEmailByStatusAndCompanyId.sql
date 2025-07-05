select 
* from JCA_EMAIL
where SEND_STATUS in /*listStatus*/()
/*IF companyId != null */
AND COMPANY_ID = /*companyId*/
/*END*/