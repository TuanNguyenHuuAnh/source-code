package vn.com.unit.ep2p.constant;

import org.springframework.stereotype.Component;

/**
 * ItemConstant
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
@Component(value = "itemConstant")
public class ItemConstant {
	public static final String ITEM_SCREEN_USER_MANAGEMENT = "SU1#S01_UserManagementList";

	// EP2P master data
	public static final String ITEM_SCREEN_BANK = "EP2P#S01_M_Bank";
	public static final String ITEM_SCREEN_CHANNEL = "EP2P#S01_M_Channel";
	public static final String ITEM_SCREEN_COST_CENTER = "EP2P#S01_M_CostCenter";
	public static final String ITEM_SCREEN_EXCHANGE_RATE = "EP2P#S01_M_ExchangeRate";
	public static final String ITEM_SCREEN_EXPENSE_TYPE = "EP2P#S01_M_ExpenseType";
	public static final String ITEM_SCREEN_PRODUCT_CATEGORY = "EP2P#S01_M_ProductCategory";
	public static final String ITEM_SCREEN_PRODUCT_TYPE = "EP2P#S01_M_ProductType";
	public static final String ITEM_SCREEN_REASON_WRITE_OFF = "EP2P#S01_M_ReasonWriteOff";
	public static final String ITEM_SCREEN_STOCK = "EP2P#S01_M_Stock";
	public static final String ITEM_SCREEN_SUB_CATEGORY = "EP2P#S01_M_SubCategory";
	public static final String ITEM_SCREEN_T_CODE7 = "EP2P#S01_M_TCode7";
	public static final String ITEM_SCREEN_UNIT = "EP2P#S01_M_Unit";
	
	/** ITEM_SCREEN_COMPONENT_AUTHORITY */
	public static final String ITEM_SCREEN_COMPONENT_AUTHORITY = "EF2#S05_RoleForComponent";
	public static final String ITEM_SCREEN_REGISTER_SVC = "EF2#S02_RegisterService";
	public static final String ITEM_SCREEN_SVC_MANAGEMENT = "EF2#S03_ServiceList";
}
