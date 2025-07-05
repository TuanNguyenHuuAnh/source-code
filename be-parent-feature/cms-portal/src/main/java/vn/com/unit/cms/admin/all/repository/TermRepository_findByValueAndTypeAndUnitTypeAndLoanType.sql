SELECT * FROM m_term
WHERE term_value = /*termValue*/
AND term_type = /*termType*/
AND unit_type = /*unitType*/
AND is_loan_term = /*loanTerm*/
AND delete_date IS null
AND delete_by IS null