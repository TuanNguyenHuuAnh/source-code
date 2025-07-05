package vn.com.unit.ep2p.dto;

public abstract class AbstractTempEntity {
	
	/**
	 * @return the importId
	 */
	public abstract String getImportId();

	/**
	 * @param importId the importId to set
	 */
	public abstract void setImportId(String importId);

	/**
	 * @return the tempId
	 */
	public abstract Long getTempId();

	/**
	 * @param tempId the tempId to set
	 */
	public abstract void setTempId(Long tempId);

	/**
	 * @return the importNote
	 */
	public abstract String getImportNote();

	/**
	 * @param importNote the importNote to set
	 */
	public abstract void setImportNote(String importNote);

	/**
	 * @return the importResultCheck
	 */
	public abstract String getImportResultCheck();

	/**
	 * @param importResultCheck the importResultCheck to set
	 */
	public abstract void setImportResultCheck(String importResultCheck);

}
