select max(COURSE_CODE) code
from M_TRAINING_COURSES
where COURSE_CODE LIKE concat('%',/*prefix*/,'%')