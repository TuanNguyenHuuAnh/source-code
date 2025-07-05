select  st.ID                 			AS id
	, st.SLA_SETTING_ID				AS sla_setting_id
    , st.USER_TYPE					AS user_type
    , st.ACCOUNT_ID               AS account_id
    , st.GROUP_ID                AS group_id
    , se.WORK_TIME				 AS work_time
    , se.TIME_TYPE				AS time_type
    , so.CALENDAR_TYPE   		AS calendar_type_id
    , ss.IMMEDIATELY			AS immediately
    , st.EMAIL					AS email
    , st.COMPANY_ID				AS company_id
    , st.CREATED_BY					AS created_by
    , st.CREATED_DATE				AS created_date
    , st.UPDATED_BY					AS updated_by
    , st.UPDATED_DATE				AS updated_date
    , st.DELETED_BY					AS deleted_by
    , st.DELETED_DATE				AS deleted_date
from SLA_SETTING_ALERT_TO st
left join SLA_SETTING ss on ss.ID = st.SLA_SETTING_ID
left join SLA_STEP se on ss.SLA_STEP_ID = se.ID
left join SLA_INFO so on se.SLA_INFO_ID = so.ID

WHERE
	st.DELETED_BY is null
	AND ss.DELETED_BY is null
	AND se.DELETED_BY is null
	AND so.DELETED_BY is null
	/*IF stepId != null */
	AND se.ID = /*stepId*/
	/*END*/
