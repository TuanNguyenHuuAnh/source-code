/*******************************************************************************
 * Class        ：JpmTaskAssigneeService
 * Created date ：2021/03/04
 * Lasted date  ：2021/03/04
 * Author       ：Tan Tai
 * Change log   ：2021/03/04：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.workflow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.com.unit.workflow.dto.JpmTaskAssigneeDto;
import vn.com.unit.workflow.entity.JpmTaskAssignee;

/**
 * JpmTaskAssigneeService.
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */
public interface JpmTaskAssigneeService {

	/**
	 * <p>MATRIX_FLAG</p>.
	 *
	 * @author Tan Tai
	 * @version 01-00
	 * @since 01-00
	 */
	enum MATRIX_FLAG {
		
        /** The assignee flag. */
        ASSIGNEE_FLAG("1", "ASSIGNEE_FLAG"),
        /** The delegate flag. */
        DELEGATE_FLAG("2", "DELEGATE_FLAG"),
        /** The owner flag. */
        OWNER_FLAG("3", "OWNER_FLAG"),
        /** The submitted flag. */
        SUBMITTED_FLAG("4", "SUBMITTED_FLAG");

		/** The value. */
		private String value;
		
		/** The name. */
		private String name;
		
		
	    /** The Constant mappings. */
    	private static final Map<String, MATRIX_FLAG> mappings = new HashMap<>(MATRIX_FLAG.values().length);
	    static {
	        for (MATRIX_FLAG type : values()) {
	            mappings.put(type.getValue(), type);
	        }
	    }

	    /**
    	 * <p>Resolve by value.</p>
    	 *
    	 * @author Tan Tai
    	 * @param value type {@link String}
    	 * @return {@link MATRIX_FLAG}
    	 */
    	public static MATRIX_FLAG resolveByValue(String value) {
	        return mappings.get(value);
	    }
	    
		/**
		 * <p>Instantiates a new matrix flag.</p>
		 *
		 * @author Tan Tai
		 * @param value type {@link String}
		 * @param name type {@link String}
		 */
		private MATRIX_FLAG(String value, String name) {
			this.value = value;
			this.name = name;
		}
		
		/**
		 * <p>Get value.</p>
		 *
		 * @author Tan Tai
		 * @return {@link String}
		 */
		public String getValue() {
			return value;
		}
		
		/**
		 * <p>Get value boolean.</p>
		 *
		 * @author Tan Tai
		 * @return {@link boolean}
		 */
		public boolean getValueBoolean() {
			return Boolean.valueOf(value);
		}
		
		/**
		 * <p>Get name.</p>
		 *
		 * @author Tan Tai
		 * @return {@link String}
		 */
		public String getName() {
			return name;
		}

	}

	/**
	 * getAccIdsByTaskId.
	 *
	 * @author Tan Tai
	 * @param taskId type {@link Long}
	 * @return {@link List<Long>}
	 */
	List<Long> getAccIdsByTaskId(Long taskId);

	/**
	 * createTaskSubmitted.
	 *
	 * @author Tan Tai
	 * @param jpmTaskAssigneeDto type {@link JpmTaskAssigneeDto}
	 * @param accountIds type {@link List<Long>}
	 */
	void createTaskSubmitted(JpmTaskAssigneeDto jpmTaskAssigneeDto, List<Long> accountIds);

	/**
	 * createTaskDelegate.
	 *
	 * @author Tan Tai
	 * @param jpmTaskAssigneeDto type {@link JpmTaskAssigneeDto}
	 * @param accountIds type {@link List<Long>}
	 */
	void createTaskDelegate(JpmTaskAssigneeDto jpmTaskAssigneeDto, List<Long> accountIds);

	/**
	 * createTaskOwner.
	 *
	 * @author Tan Tai
	 * @param jpmTaskAssigneeDto type {@link JpmTaskAssigneeDto}
	 * @param accountIds type {@link List<Long>}
	 */
	void createTaskOwner(JpmTaskAssigneeDto jpmTaskAssigneeDto, List<Long> accountIds);

	/**
	 * createTaskAssignee.
	 *
	 * @author Tan Tai
	 * @param jpmTaskAssigneeDto type {@link JpmTaskAssigneeDto}
	 * @param accountIds type {@link List<Long>}
	 */
	void createTaskAssignee(JpmTaskAssigneeDto jpmTaskAssigneeDto, List<Long> accountIds);

	/**
	 * completeTaskAssignee.
	 *
	 * @author Tan Tai
	 * @param taskId type {@link Long}
	 */
	void completeTaskAssignee(Long taskId);
	
	/**
     * <p>
     * Get list account by task id.
     * </p>
     *
     * @param taskId
     *            type {@link Long}
     * @param type
     *            type {@link Long}
     * @return {@link List<Long>}
     * @author tantm
     */
	List<Long> getListAccountByTaskIdAndType(Long taskId, Long type);
	
	List<JpmTaskAssignee> saveJpmtaskWithFlag(JpmTaskAssigneeDto jpmTaskAssigneeDto, List<Long> accountIds, String matrixFlag);
}
