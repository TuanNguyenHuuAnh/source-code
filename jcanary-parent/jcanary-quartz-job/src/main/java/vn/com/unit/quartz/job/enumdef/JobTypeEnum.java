/*******************************************************************************
 * Class        ：JobTypeEnum
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.enumdef;

/**
 * <p>
 * JobTypeEnum
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
public enum JobTypeEnum {

	/** The execute sp. */
	EXECUTE_SP, /** The export excel. */
 EXPORT_EXCEL, /** The export excel to sftp. */
 EXPORT_EXCEL_TO_SFTP, /** The sync agent product. */
 SYNC_AGENT_PRODUCT, /** The sync movement to las. */
 SYNC_MOVEMENT_TO_LAS, /** The remind sp. */
 REMIND_SP, /** The send email. */
 SEND_EMAIL, /** The sla alert. */
 SLA_ALERT, /** The clean draft file. */
 CLEAN_DRAFT_FILE, /** The pussh notification. */
 PUSSH_NOTIFICATION;
	
}
