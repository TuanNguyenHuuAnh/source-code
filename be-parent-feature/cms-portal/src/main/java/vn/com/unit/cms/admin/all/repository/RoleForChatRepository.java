/*******************************************************************************
 * Class        ：RoomClientRepository
 * Created date ：2017/05/12
 * Lasted date  ：2017/05/12
 * Author       ：phunghn
 * Change log   ：2017/05/12：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.repository;

import java.util.List;

//import org.springframework.data.mirage.repository.MirageRepository;
//import org.springframework.data.mirage.repository.query.Modifying;
import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.cms.admin.all.dto.ChatUserProductDto;
import vn.com.unit.cms.admin.all.dto.ChatUserProductSearchDto;
import vn.com.unit.cms.admin.all.entity.RoleForChat;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.cms.admin.all.jcanary.dto.AccountAddDto;

/**
 * RoomClientRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
public interface RoleForChatRepository extends DbRepository<RoleForChat, Long> {

	@Modifying
	void deleteUserByRole(@Param("role") String roleCode, @Param("user") String user);

	List<ChatUserProductDto> findListUserByRole(@Param("role") String roleCode);

	int countUserRoleAllActive(@Param("userDto") ChatUserProductSearchDto userProductDto, @Param("lang") String lang);

	List<ChatUserProductDto> findAllUserRoleActive(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("userDto") ChatUserProductSearchDto userDto, @Param("lang") String lang);

	List<AccountAddDto> findFullNameByRole(@Param("role") String roleCode);

	RoleForChat findRoleForChatByRoleAndUsername(@Param("role") String roleCode, @Param("username") String username);

}
