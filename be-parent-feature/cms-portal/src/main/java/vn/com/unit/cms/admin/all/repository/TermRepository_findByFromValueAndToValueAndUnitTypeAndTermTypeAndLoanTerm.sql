SELECT * FROM m_term
WHERE from_value = /*fromValue*/
AND to_value = /*toValue*/
AND unit_type = /*unitType*/
AND term_type = /*termType*/
AND is_loan_term = /*loanTerm*/
AND delete_date IS null
AND delete_by IS null