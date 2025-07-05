SELECT
    'Danh sách tài liệu có ' as name, COUNT(*) as document
FROM M_DOCUMENT m 
WHERE
    m.delete_date is null
    AND m.STATUS NOT IN /*lstStatus*/()
    AND m.M_DOCUMENT_TYPE_ID = /*documentTypeId*/