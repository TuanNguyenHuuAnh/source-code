/*******************************************************************************
 * Class        ：ConditionSearchUtils
 * Created date ：2018/12/27
 * Lasted date  ：2018/12/27
 * Author       ：phatlt
 * Change log   ：2018/12/27：01-00 phatlt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.utils;

import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.core.dto.ConditionSearchCommonDto;

/**
 * ConditionSearchUtils
 * 
 * @version 01-00
 * @since 01-00
 * @author phatlt
 * 
 * lưu condition search vào session, sau đó sẽ dựa vào prefix của controller để lấy condition tương ứng
 * với danh sách containUrl là các url trước khi trở về lại thời điểm gọi để lấy condition từ session
 * nếu gọi từ controller khác sẽ tự động xóa session cũ và lưu session mới theo controller đó
 * referer :là đường dẫn trước khi đến page hiện tại, nằm ở header của request
 * 
 */
public class ConditionSearchUtils<T extends ConditionSearchCommonDto> {
    
    private static final String PREFIX = "__CONDITION_SEARCH__"; 
    
    private static final int PAGE = 1;
    private static final int PAGESIZE = 0;
    
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" } )
    public T getConditionSearch(Class<?> controllerClass, T object, String [] containUrl, HttpServletRequest request){
        HttpSession session = request.getSession();
        Enumeration keys = session.getAttributeNames();
        String nameSession = controllerClass.getSimpleName().concat(PREFIX);
        String referer =  request.getHeader("referer");
        while(keys.hasMoreElements()){
            String sessionName = (String) keys.nextElement();
            if(sessionName!=null && sessionName.contains(PREFIX) && !nameSession.equals(sessionName)){
                session.removeAttribute(sessionName);
            }
        }
        T objectFromSession = (T)session.getAttribute(nameSession);
        boolean isGetFromSession = false;
        if(referer !=null && containUrl!=null){
            for(String url : containUrl){
                if(referer.contains(url)){
                    isGetFromSession = true;
                }
            }
        }
        
        // nếu không nằm trong danh sách url lưu session và session name có tồn tại thì xóa luôn.
        if(!isGetFromSession && session.getAttribute(nameSession)!=null)
        	session.removeAttribute(nameSession);
        
        return (isGetFromSession && objectFromSession !=null) ? objectFromSession : object;
    }
    
    public T getConditionSearch(Class<?> controllerClass, T object, String [] containUrl, HttpServletRequest request, int defaultPage, int defaultPageSize) {
    	T condition = this.getConditionSearch(controllerClass, object, containUrl, request);
    	condition.setPage(Optional.ofNullable(condition.getPage()).orElse(defaultPage));
    	condition.setPageSize(Optional.ofNullable(condition.getPageSize()).orElse(defaultPageSize));
    	return condition;
    } 
    
    public void setConditionSearch(HttpServletRequest request, Class<?> controllerClass, T conditionSearch ){
    	setConditionSearch(request, controllerClass, conditionSearch, PAGE, PAGESIZE);
    }
    
    public void setConditionSearch(HttpServletRequest request, Class<?> controllerClass, T conditionSearch , Integer page, Integer pageSize){
        HttpSession session =  request.getSession();
        page = page == null ? PAGE : page;
        pageSize = pageSize == null ? PAGESIZE : pageSize;
        conditionSearch.setPage(page);
        conditionSearch.setPageSize(pageSize);
        conditionSearch.setJsonSearch(null);
        String json = "{}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        try {
			json = mapper.writeValueAsString(conditionSearch);
		} catch (Exception e) {
		}
        conditionSearch.setJsonSearch(json);
        session.setAttribute(controllerClass.getSimpleName().concat(PREFIX), conditionSearch);
    }
    
}
