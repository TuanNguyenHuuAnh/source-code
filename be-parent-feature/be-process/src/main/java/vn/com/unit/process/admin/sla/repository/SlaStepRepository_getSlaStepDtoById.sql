select  st.ID                 			AS id
	, st.SLA_INFO_ID				AS sla_info_id
    , st.STEP_ID					AS step_id
    , st.BUSINESS_ID               AS business_id
    , bus.CODE					   AS business_code
    , st.PROCESS_ID                AS process_id
    , st.WORK_TIME                 AS work_time
    , st.TIME_TYPE                 AS time_type
    , st.ACTIVED			        AS actived
    , jd.STEP_NO					AS step_no
    , jd.CODE						AS step_code
    , jd.NAME						AS step_name 
    , jd.STEP_TYPE					AS step_type 
    , jbd.BUTTON_CODE 				AS button_code
    , st.AUTO_ACTION				AS auto_action
    , st.ACTION_BUTTON_ID			AS action_button_id
    , st.CREATED_BY					AS created_by
    , st.CREATED_DATE				AS created_date
    , st.UPDATED_BY					AS updated_by
    , st.UPDATED_DATE				AS updated_date
    , st.DELETED_BY					AS deleted_by
    , st.DELETED_DATE				AS deleted_date
from SLA_STEP st
LEFT JOIN JPM_STEP_DEPLOY jd ON st.STEP_ID = jd.ID
LEFT JOIN JPM_BUTTON_FOR_STEP jbs on st.STEP_ID = jbs.STEP_ID
LEFT JOIN JPM_BUTTON_DEPLOY jbd on jbs.BUTTON_ID = jbd.ID
LEFT JOIN JPM_BUSINESS bus ON bus.ID = st.BUSINESS_ID 
WHERE
	st.DELETED_BY is null
	AND jd.DELETED_BY is null
	AND jbs.DELETED_BY is null
	AND jbd.DELETED_BY is null
	/*IF id != null */
	AND st.ID = /*id*/
	/*END*/
	