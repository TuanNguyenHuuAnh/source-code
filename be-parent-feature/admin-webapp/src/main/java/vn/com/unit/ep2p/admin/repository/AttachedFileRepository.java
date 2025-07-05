/*******************************************************************************
 * Class        ：AttachedFileRepository
 * Created date ：2019/02/19
 * Lasted date  ：2019/02/19
 * Author       ：VinhLT
 * Change log   ：2019/02/19：01-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.core.entity.JcaAttachFile;
import vn.com.unit.core.repository.JcaAttachFileRepository;

/**
 * AttachedFileRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
public interface AttachedFileRepository extends JcaAttachFileRepository {

	/**
	 * findListAttachedFileByRef
	 *
	 * @param ref
	 * @return
	 * @author VinhLT
	 */
	List<JcaAttachFile> findListAttachedFileByRef(@Param("ref") String ref);
}
