/*******************************************************************************
 * Class        ：ImportCommonDto
 * Created date ：2020/05/31
 * Lasted date  ：2020/05/31
 * Author       ：TaiTM
 * Change log   ：2020/05/31：01-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.dto;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

/**
 * ImportCommonDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 * @param <E>
 */
public class ImportCommonDto<E> extends PluploadDto {

    private Long id;

    private String sessionKey;

    private String messageError;

    private List<String> listMessageErrors;

    private String messageWarning;

    private List<String> listMessageWarnings;

    private boolean isError;

    private boolean isWarning;

    private boolean isSaved;

    private Integer startRowData = new Integer(5);

    private Class<E> enumImport;

    private Map<String, List<String>> colsValidate;

    private ModelAndView mav;
    
    private String username;
    
    private String token;
    
    private String channel;

    /**
     * @return the sessionKey
     * @author taitm
     * @date May 30, 2020
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * @param sessionKey the sessionKey to set
     * @author taitm
     * @date May 30, 2020
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * @author TaiTM
     * @date Jun 9, 2020
     * @return the enumImport
     */
    public Class<E> getEnumImport() {
        return enumImport;
    }

    /**
     * @author TaiTM
     * @date Jun 9, 2020
     * @param enumImport the enumImport to set
     */
    public void setEnumImport(Class<E> enumImport) {
        this.enumImport = enumImport;
    }

    /**
     * @return the startRowData
     * @author taitm
     * @date May 27, 2020
     */
    public Integer getStartRowData() {
        return startRowData;
    }

    /**
     * @param startRowData the startRowData to set
     * @author taitm
     * @date May 27, 2020
     */
    public void setStartRowData(Integer startRowData) {
        this.startRowData = startRowData;
    }

    /**
     * @return the messageError
     * @author taitm
     * @date May 28, 2020
     */
    public String getMessageError() {
        return messageError;
    }

    /**
     * @param messageError the messageError to set
     * @author taitm
     * @date May 28, 2020
     */
    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    /**
     * @return the messageWarming
     * @author taitm
     * @date May 28, 2020
     */
    public String getMessageWarning() {
        return messageWarning;
    }

    /**
     * @param messageWarming the messageWarming to set
     * @author taitm
     * @date May 28, 2020
     */
    public void setMessageWarning(String messageWarning) {
        this.messageWarning = messageWarning;
    }

    /**
     * @return the isError
     * @author taitm
     * @date May 28, 2020
     */
    public boolean isError() {
        return isError;
    }

    public boolean getIsError() {
        return isError;
    }

    /**
     * @param isError the isError to set
     * @author taitm
     * @date May 28, 2020
     */
    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    /**
     * @return the isWarning
     * @author taitm
     * @date May 28, 2020
     */
    public boolean isWarning() {
        return isWarning;
    }

    public boolean getIsWarning() {
        return isWarning;
    }

    /**
     * @param isWarning the isWarning to set
     * @author taitm
     * @date May 28, 2020
     */
    public void setIsWarning(boolean isWarning) {
        this.isWarning = isWarning;
    }

    /**
     * @return the colsValidate
     * @author taitm
     * @date May 29, 2020
     */
    public Map<String, List<String>> getColsValidate() {
        return colsValidate;
    }

    /**
     * @param colsValidate the colsValidate to set
     * @author taitm
     * @date May 29, 2020
     */
    public void setColsValidate(Map<String, List<String>> colsValidate) {
        this.colsValidate = colsValidate;
    }

    /**
     * @return the mav
     * @author taitm
     * @date Jun 1, 2020
     */
    public ModelAndView getMav() {
        return mav;
    }

    /**
     * @param mav the mav to set
     * @author taitm
     * @date Jun 1, 2020
     */
    public void setMav(ModelAndView mav) {
        this.mav = mav;
    }

    /**
     * @return the isSaved
     * @author taitm
     * @date Jun 2, 2020
     */
    public boolean isSaved() {
        return isSaved;
    }

    public boolean getIsSaved() {
        return isSaved;
    }

    /**
     * @param isSaved the isSaved to set
     * @author taitm
     * @date Jun 2, 2020
     */
    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    /**
     * @author TaiTM
     * @date Jun 6, 2020
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @author TaiTM
     * @date Jun 6, 2020
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @return the listMessageErrors
     */
    public List<String> getListMessageErrors() {
        return listMessageErrors;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @param listMessageErrors the listMessageErrors to set
     */
    public void setListMessageErrors(List<String> listMessageErrors) {
        this.listMessageErrors = listMessageErrors;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @return the listMessageWarnings
     */
    public List<String> getListMessageWarnings() {
        return listMessageWarnings;
    }

    /**
     * @author TaiTM
     * @date Jun 8, 2020
     * @param listMessageWarnings the listMessageWarnings to set
     */
    public void setListMessageWarnings(List<String> listMessageWarnings) {
        this.listMessageWarnings = listMessageWarnings;
    }

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
    
}
