SELECT
    COUNT(*)
FROM
    jca_repository repo
WHERE
    repo.DELETED_ID = 0
    AND repo.company_id = /*companyId*/1
    AND repo.code = /*code*/''