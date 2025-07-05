/**
 * Common JS
 */
$(function() {
	
	// Define variable from SESSION_JS_INLINE
	/*[# th:if="${SESSION_JS_INLINE}" th:each="jsInline : ${SESSION_JS_INLINE}" ]*/
		var key = /*[[${jsInline.key}]]*/'';
		window[key] = /*[[${jsInline.value}]]*/'';
    /*[/]*/

});