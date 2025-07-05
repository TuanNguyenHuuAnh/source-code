SELECT m.COMMENT_NAME 		AS comment_name
    , m.COMMENT_CODE 		AS comment_code
    , m.CREATE_BY 			AS create_by
    , m.CREATE_DATE  		AS create_date
    , m.ID					AS id
    , m.PROCESSED_USER 		AS processed_user
    , m.PROCESSING_STATUS  	AS status_title
    , m.processing_status 	AS status
    , m.m_product_consulting_infor_id as product_consulting_infor_id
FROM M_PRODUCT_CONSULTING_INFOR_UPDATE_ITEM m
WHERE m.delete_date is null
AND m.M_PRODUCT_CONSULTING_INFOR_ID = /*productConsultingId*/
ORDER BY m.create_date DESC