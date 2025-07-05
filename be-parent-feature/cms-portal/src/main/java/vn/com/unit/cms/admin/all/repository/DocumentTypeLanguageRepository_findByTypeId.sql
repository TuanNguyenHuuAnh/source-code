SELECT
	    id				as id
	  , m_language_code	as m_language_code
	  , title 			as title
	  , introduce		as introduce
	  , key_word		
	  , key_word_description		
	  , link_alias		
FROM m_document_type_language 
WHERE
	delete_date is null
	AND m_document_type_id = /*typeId*/
