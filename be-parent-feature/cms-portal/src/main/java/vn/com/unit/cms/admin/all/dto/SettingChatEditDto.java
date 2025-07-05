/*******************************************************************************
 * Class        ：SettingChatEditDto
 * Created date ：2017/07/12
 * Lasted date  ：2017/07/12
 * Author       ：phunghn
 * Change log   ：2017/07/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.dto;

import java.util.List;

/**
 * SettingChatEditDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class SettingChatEditDto {
	List<SettingChatLanguageDto> listSettingLanguageChat;

	private String jsonDto;
	/**
	 * Get listSettingLanguageChat
	 * @return List<SettingChatLanguageDto>
	 * @author phunghn
	 */
	public List<SettingChatLanguageDto> getListSettingLanguageChat() {
		return listSettingLanguageChat;
	}

	/**
	 * Set listSettingLanguageChat
	 * @param   listSettingLanguageChat
	 *          type List<SettingChatLanguageDto>
	 * @return
	 * @author  phunghn
	 */
	public void setListSettingLanguageChat(List<SettingChatLanguageDto> listSettingLanguageChat) {
		this.listSettingLanguageChat = listSettingLanguageChat;
	}

    /**
     * @return the jsonDto
     * @author taitm
     */
    public String getJsonDto() {
        return jsonDto;
    }

    /**
     * @param jsonDto
     *            the jsonDto to set
     * @author taitm
     */
    public void setJsonDto(String jsonDto) {
        this.jsonDto = jsonDto;
    }

}
