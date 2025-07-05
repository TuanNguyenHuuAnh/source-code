select  st.ID                 			AS id
	, st.SLA_SETTING_ID				AS sla_setting_id
    , st.USER_TYPE					AS user_type
    , st.ACCOUNT_ID               AS account_id
    , st.GROUP_ID                AS group_id
    , st.EMAIL					AS email
    , st.COMPANY_ID				AS company_id
    , st.CREATED_BY					AS created_by
    , st.CREATED_DATE				AS created_date
    , st.UPDATED_BY					AS updated_by
    , st.UPDATED_DATE				AS updated_date
    , st.DELETED_BY					AS deleted_by
    , st.DELETED_DATE				AS deleted_date
    , st.SEND_MAIL_TYPE				AS send_mail_type
from SLA_SETTING_ALERT_TO st
WHERE
	st.DELETED_BY is null	
	/*IF settingId != null */
	AND st.SLA_SETTING_ID = /*settingId*/
	/*END*/
