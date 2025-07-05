/*******************************************************************************
 * Class        ：AttachedFileService
 * Created date ：2019/02/19
 * Lasted date  ：2019/02/19
 * Author       ：VinhLT
 * Change log   ：2019/02/19：01-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.ep2p.core.dto.AttachedFileDto;
import vn.com.unit.ep2p.core.entity.AttachedFile;

//import vn.com.unit.jcanary.dto.AttachedFileDto;
//import vn.com.unit.jcanary.entity.AttachedFile;

/**
 * AttachedFileService
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public interface AttachedFileService {

	/**
	 * processAttachedFile
	 *
	 * @param attachedFileDto
	 * @return
	 * @author VinhLT
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	List<AttachedFile> processAttachedFile(AttachedFileDto attachedFileDto) throws IllegalStateException, IOException;

	/**
	 * downloadAttachedFile
	 *
	 * @param id
	 * @param response
	 * @throws Exception
	 * @author VinhLT
	 */
	void downloadAttachedFile(Long id, HttpServletResponse response) throws Exception;

	/**
	 * deleteFileById
	 *
	 * @param id
	 * @author VinhLT
	 */
	void deleteFileById(Long id);

	/**
	 * initComponent
	 *
	 * @param mav
	 * @param businessCode
	 * @param refAttachment
	 * @param isShowAction
	 * @param itemPermission
	 * @author VinhLT
	 */
	void initComponent(ModelAndView mav, String businessCode, String refAttachment, boolean isShowAction,
			String itemPermission);

}
