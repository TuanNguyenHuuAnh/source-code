/*******************************************************************************
 * Class        pluploadDto
 * Created date 2017/08/07
 * Lasted date  2017/08/07
 * Author       phunghn
 * Change log   2017/08/0701-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.dto;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * pluploadDto
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public class PluploadDto {

	private String name;
	
	private String isMultiple;
	private int chunks = -1;
	
	private int chunk = -1;
	
	private HttpServletRequest request;
	
	private MultipartFile multipartFile;

	/**
	 * Get name
	 * @return String
	 * @author phunghn
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name
	 * @param   name
	 *          type String
	 * @return
	 * @author  phunghn
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getIsMultiple() {
		return isMultiple;
	}
	
	public void setIsMultiple(String isMultiple) {
		this.isMultiple = isMultiple;
	}
	
	/**
	 * Get chunks
	 * @return int
	 * @author phunghn
	 */
	public int getChunks() {
		return chunks;
	}

	/**
	 * Set chunks
	 * @param   chunks
	 *          type int
	 * @return
	 * @author  phunghn
	 */
	public void setChunks(int chunks) {
		this.chunks = chunks;
	}

	/**
	 * Get chunk
	 * @return int
	 * @author phunghn
	 */
	public int getChunk() {
		return chunk;
	}

	/**
	 * Set chunk
	 * @param   chunk
	 *          type int
	 * @return
	 * @author  phunghn
	 */
	public void setChunk(int chunk) {
		this.chunk = chunk;
	}

	/**
	 * Get request
	 * @return HttpServletRequest
	 * @author phunghn
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Set request
	 * @param   request
	 *          type HttpServletRequest
	 * @return
	 * @author  phunghn
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Get multipartFile
	 * @return MultipartFile
	 * @author phunghn
	 */
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	/**
	 * Set multipartFile
	 * @param   multipartFile
	 *          type MultipartFile
	 * @return
	 * @author  phunghn
	 */
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

}
