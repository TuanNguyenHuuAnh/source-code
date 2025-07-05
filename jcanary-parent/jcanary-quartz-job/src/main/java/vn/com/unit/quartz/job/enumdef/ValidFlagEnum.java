/*******************************************************************************
 * Class        ：ValidFlagEnum
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.enumdef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * <p>
 * ValidFlagEnum
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public enum ValidFlagEnum {
	
	/** The actived. */
	ACTIVED(StatusEnum.ACTIVED, null), 
	
	/** The history. */
	HISTORY(StatusEnum.HISTORY, null),
	
	/** The waiting approve new. */
	WAITING_APPROVE_NEW(StatusEnum.WAITING, RequestTypeEnum.NEW),
	
	/** The waiting approve update. */
	WAITING_APPROVE_UPDATE(StatusEnum.WAITING, RequestTypeEnum.UPDATE), 
	
	/** The waiting approve delete. */
	WAITING_APPROVE_DELETE(StatusEnum.WAITING, RequestTypeEnum.DELETE),
	
	/** The canceled new. */
	CANCELED_NEW(StatusEnum.CANCELED, RequestTypeEnum.NEW), 
	
	/** The canceled update. */
	CANCELED_UPDATE(StatusEnum.CANCELED, RequestTypeEnum.UPDATE), 
	
	/** The canceled delete. */
	CANCELED_DELETE(StatusEnum.CANCELED, RequestTypeEnum.DELETE), 
	
	/** The reject new. */
	REJECT_NEW(StatusEnum.REJECTED, RequestTypeEnum.NEW), 
	
	/** The reject update. */
	REJECT_UPDATE(StatusEnum.REJECTED, RequestTypeEnum.UPDATE), 
	
	/** The reject delete. */
	REJECT_DELETE(StatusEnum.REJECTED, RequestTypeEnum.DELETE),
	
	/** The deleted. */
	DELETED(StatusEnum.DELETED, null),
	
	/** The waiting bo. */
	WAITING_BO(StatusEnum.BO, RequestTypeEnum.NEW),
	
	/** The error bo. */
	ERROR_BO(StatusEnum.BO, RequestTypeEnum.UPDATE),
	
	/** The complete bo. */
	COMPLETE_BO(StatusEnum.BO, RequestTypeEnum.DELETE);
	
	/**
     * <p>
     * ValidFlagEnumInjector
     * </p>
     * .
     *
     * @author khadm
     * @version 01-00
     * @since 01-00
     */
	@Component("validFlagEnum")
    public static class ValidFlagEnumInjector {
		
        /**
         * <p>
         * Get by value.
         * </p>
         *
         * @author khadm
         * @param value
         *            type {@link Integer}
         * @return {@link ValidFlagEnum}
         */
        public ValidFlagEnum getByValue(Integer value) {
        	return ValidFlagEnum.resolveByValue(value);
        }
        
        /**
         * <p>
         * Get status by value.
         * </p>
         *
         * @author khadm
         * @param value
         *            type {@link Integer}
         * @return {@link StatusEnum}
         */
        public StatusEnum getStatusByValue(Integer value) {
        	return ValidFlagEnum.resolveByValue(value).getStatus();
        }
        
        /**
         * <p>
         * Get request type by value.
         * </p>
         *
         * @author khadm
         * @param value
         *            type {@link Integer}
         * @return {@link RequestTypeEnum}
         */
        public RequestTypeEnum getRequestTypeByValue(Integer value) {
        	return ValidFlagEnum.resolveByValue(value).getType();
        }
        
    }
	
	/** The value. */
	private Integer value;
	
	/** The status. */
	private StatusEnum status;
	
	/** The type. */
	private RequestTypeEnum type;

	/** The Constant mappings. */
	private static final Map<Integer, ValidFlagEnum> mappings = new HashMap<>(13);


	static {
		for (ValidFlagEnum validFlagEnum : values()) {
			mappings.put(validFlagEnum.getValue(), validFlagEnum);
		}
	}

	/**
     * <p>
     * Resolve by value.
     * </p>
     *
     * @author khadm
     * @param value
     *            type {@link Integer}
     * @return {@link ValidFlagEnum}
     */
	public static ValidFlagEnum resolveByValue(Integer value) {
		return (value != null ? mappings.get(value) : null);
	}

	/**
     * <p>
     * Resolve by regex pattern.
     * </p>
     *
     * @author khadm
     * @param pattern
     *            type {@link String}
     * @return {@link List<String>}
     */
	public static List<String> resolveByRegexPattern(String pattern) {
		List<String> flags = new ArrayList<>();
		for (ValidFlagEnum flag : ValidFlagEnum.values()) {
			if (flag.toString().matches(pattern)) {
				flags.add(flag.toString());
			}
		}
		return flags;
	}

	/**
     * <p>
     * Instantiates a new valid flag enum.
     * </p>
     *
     * @author khadm
     * @param status
     *            type {@link StatusEnum}
     * @param type
     *            type {@link RequestTypeEnum}
     */
	private ValidFlagEnum(StatusEnum status, RequestTypeEnum type) {
		this.value = type == null ? status.getValue() : status.getValue() * 10 + type.getValue();
		this.status = status;
		this.type = type;
	}
	
	/**
     * <p>
     * Get value.
     * </p>
     *
     * @author khadm
     * @return {@link Integer}
     */
	public Integer getValue() {
		return value;
	}

	/**
     * <p>
     * Get status.
     * </p>
     *
     * @author khadm
     * @return {@link StatusEnum}
     */
	public StatusEnum getStatus() {
		return status;
	}

	/**
     * <p>
     * Get type.
     * </p>
     *
     * @author khadm
     * @return {@link RequestTypeEnum}
     */
	public RequestTypeEnum getType() {
		return type;
	}

	/**
     * <p>
     * To long.
     * </p>
     *
     * @author khadm
     * @return {@link Long}
     */
	public Long toLong() {
		return Long.parseLong(value.toString());
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return value.toString();
	}
	
}
