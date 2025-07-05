/*******************************************************************************
 * Class        ：MenuPathServiceImpl
 * Created date ：2020/12/10
 * Lasted date  ：2020/12/10
 * Author       ：SonND
 * Change log   ：2020/12/10：01-00 SonND create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.dto.JcaMenuPathDto;
import vn.com.unit.core.entity.JcaMenuPath;
import vn.com.unit.core.service.JcaMenuPathService;
import vn.com.unit.ep2p.core.service.AbstractCommonService;
import vn.com.unit.ep2p.service.MenuPathService;

/**
 * MenuPathServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author SonND
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MenuPathServiceImpl extends AbstractCommonService implements MenuPathService {

	@Autowired
	private JcaMenuPathService jcaMenuPathService;
	

	@Override
	public JcaMenuPath save(JcaMenuPath jcaMenuPath) {
		return jcaMenuPathService.saveJcaMenuPath(jcaMenuPath);
	}

	@Override
	public JcaMenuPath getMenuPathById(Long menuPathId) {
		return jcaMenuPathService.getJcaMenuPathById(menuPathId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JcaMenuPath create(JcaMenuPathDto jcaMenuPathDto) {
		JcaMenuPath jcaMenuPath = new JcaMenuPath();
		jcaMenuPath.setAncestorId(jcaMenuPathDto.getAncestorId());
		jcaMenuPath.setDescendantId(jcaMenuPathDto.getDescendantId());
		jcaMenuPath.setDepth(jcaMenuPathDto.getDepth());
		this.save(jcaMenuPath);
		return null;
	}

	@Override
	public JcaMenuPath getMenuPathByDescendantId(Long descendantId) {
		return jcaMenuPathService.getJcaMenuPathByDescendantId(descendantId);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenuPathByDescendantId(Long descendantId) {
        jcaMenuPathService.deleteMenuPathByDescendantId(descendantId);
    }

}
