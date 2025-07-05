SELECT
	param.ID                     AS PARAM_ID
	, param.PROCESS_ID           AS PROCESS_ID
	, param.FIELD_NAME           AS FIELD_NAME
	, param.DATA_TYPE            AS DATA_TYPE
	, param.FORM_FIELD_NAME      AS FORM_FIELD_NAME
FROM
	JPM_PARAM param
WHERE
	param.DELETED_ID = 0
	AND param.ID = /*paramId*/