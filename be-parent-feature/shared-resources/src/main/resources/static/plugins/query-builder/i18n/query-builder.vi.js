/*!
 * jQuery QueryBuilder 2.5.2
 * Locale: Vietnam (vi)
 * Author: KhuongTH
 * Licensed under MIT (https://opensource.org/licenses/MIT)
 */

(function(root, factory) {
    if (typeof define == 'function' && define.amd) {
        define(['jquery', 'query-builder'], factory);
    }
    else {
        factory(root.jQuery);
    }
}(this, function($) {
"use strict";

var QueryBuilder = $.fn.queryBuilder;

QueryBuilder.regional['vi'] = {
  "__locale": "Vietnam (vi)",
  "__author": "KhuongTH",
  "add_rule": "Thêm biểu thức",
  "add_group": "Thêm nhóm",
  "delete_rule": "Xóa",
  "delete_group": "Xóa",
  "conditions": {
    "AND": "VÀ",
    "OR": "HOẶC"
  },
  "operators": {
    "equal": "bằng",
    "not_equal": "không bằng",
    "in": "nằm trong",
    "not_in": "không nằm trong",
    "less": "nhỏ hơn",
    "less_or_equal": "nhỏ hơn hoặc bằng",
    "greater": "lớn hơn",
    "greater_or_equal": "lớn hơn hoặc bằng",
    "between": "giữa",
    "not_between": "không nằm giữa",
    "begins_with": "bắt đầu với",
    "not_begins_with": "không bắt đầu với",
    "contains": "chứa đựng",
    "not_contains": "không chứa",
    "ends_with": "kết thúc với",
    "not_ends_with": "không kết thúc với",
    "is_empty": "rỗng",
    "is_not_empty": "không rỗng",
    "is_null": "không xác định",
    "is_not_null": "xác định"
  },
  "errors": {
    "no_filter": "Không có bộ lọc được chọn",
    "empty_group": "Nhóm rỗng",
    "radio_empty": "Không có giá trị được chọn",
    "checkbox_empty": "Không có giá trị được chọn",
    "select_empty": "Không có giá trị được chọn",
    "string_empty": "Giá trị rỗng",
    "string_exceed_min_length": "Phải chứa ít nhất {0} ký tự",
    "string_exceed_max_length": "Không được chứa nhiều hơn {0} ký tự",
    "string_invalid_format": "Định dạng không hợp lệ ({0})",
    "number_nan": "Không phải là số",
    "number_not_integer": "Không phải là số nguyên",
    "number_not_double": "Không phải là số thực",
    "number_exceed_min": "Phải lớn hơn {0}",
    "number_exceed_max": "Phải nhỏ hơn {0}",
    "number_wrong_step": "Phải là bội số của {0}",
    "number_between_invalid": "Các giá trị không hợp lệ, {0} lớn hơn {1}",
    "datetime_empty": "Giá trị rỗng",
    "datetime_invalid": "Định dạng ngày không hợp lệ ({0})",
    "datetime_exceed_min": "Phải sau {0}",
    "datetime_exceed_max": "Phải trước {0}",
    "datetime_between_invalid": "Các giá trị không hợp lệ, {0} lớn hơn {1}",
    "boolean_not_valid": "Giá trị không phải kiểu đúng/sai",
    "operator_not_multiple": "Toán tử \"{1}\" không thể chấp nhận nhiều giá trị"
  },
  "invert": "Nghịch đảo",
  "NOT": "PHỦ ĐỊNH"
};

QueryBuilder.defaults({ lang_code: 'vi' });
}));