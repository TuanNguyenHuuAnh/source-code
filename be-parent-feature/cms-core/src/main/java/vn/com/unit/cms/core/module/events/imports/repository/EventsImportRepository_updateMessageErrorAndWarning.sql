UPDATE M_EVENTS_IMPORT
SET ID=ID
/*IF error != null*/
,MESSAGE_ERROR = /*error*/''
,IS_ERROR = 1
/*END*/
/*IF warning != null*/
,MESSAGE_WARNING = /*warning*/''
,IS_WARNING = 1
/*END*/
WHERE
    ID = /*id*/''