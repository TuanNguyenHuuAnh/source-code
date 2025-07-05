SELECT
    COUNT(1)
FROM
    jca_group_constant grpc
WHERE
    grpc.DELETED_ID = 0
    AND grpc.company_id = /*companyId*/1
    AND grpc.code = /*code*/'1'