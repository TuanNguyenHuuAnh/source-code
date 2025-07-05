SELECT
    m_role."id"
 	, m_role."code" 	
    , m_role."name"
    , m_role."description"
    , m_role."role_type"
    , m_role."active"
FROM
    "HSSA"."JCA_ROLE" m_role
WHERE
   m_role."code" = /*code*/
   AND m_role."DELETED_BY" IS NULL