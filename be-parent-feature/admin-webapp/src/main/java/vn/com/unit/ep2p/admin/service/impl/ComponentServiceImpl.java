/*******************************************************************************
 * Class        :ComponentServiceImpl
 * Created date :2019/04/22
 * Lasted date  :2019/04/22
 * Author       :HungHT
 * Change log   :2019/04/22:01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.dto.ComponentSearchDto;
import vn.com.unit.ep2p.admin.repository.ComponentRepository;
import vn.com.unit.ep2p.admin.service.ComponentService;
import vn.com.unit.ep2p.core.efo.dto.EfoComponentDto;
import vn.com.unit.ep2p.core.efo.entity.EfoComponent;

/**
 * ComponentServiceImpl
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ComponentServiceImpl implements ComponentService {  

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    ComponentRepository componentRepository;
    
//    @Autowired
//	private CommonService comService;

	// Model mapper
	ModelMapper modelMapper = new ModelMapper();

    /**
     * getComponentList
     * @param search
     * @param pageSize
     * @param page
     * @return
     * @author HungHT
     */
    public PageWrapper<EfoComponentDto> getComponentList(ComponentSearchDto search, int pageSize, int page) {
        List<Integer> listPageSize = systemConfig.getListPageSize();
        int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        PageWrapper<EfoComponentDto> pageWrapper = new PageWrapper<>(page, sizeOfPage);
        pageWrapper.setListPageSize(listPageSize);
        pageWrapper.setSizeOfPage(sizeOfPage);
        int count = componentRepository.countComponentList(search);
        List<EfoComponentDto> result = new ArrayList<>();
        if (count > 0) {
            int currentPage = pageWrapper.getCurrentPage();
            int startIndex = (currentPage - 1) * sizeOfPage;
            result = componentRepository.getComponentList(search, startIndex, sizeOfPage);
        }
        pageWrapper.setDataAndCount(result, count);
        return pageWrapper;
    }

	/**
     * findById
     * @param id
     * @return
     * @author HungHT
     */
    public EfoComponentDto findById(Long id) {
        return componentRepository.findById(id);
    }

	/**
     * initScreenDetail
     * @param mav
     * @param objectDto
     * @param locale
     * @author HungHT
     */
    public void initScreenDetail(ModelAndView mav, EfoComponentDto objectDto, Locale locale) {
        // TODO 
    }

	/**
     * saveComponent
     * @param objectDto
     * @return
     * @author HungHT
     */
    public EfoComponent saveComponent(EfoComponentDto objectDto) {
    	EfoComponent objectResult = null;
//		Date sysDate = comService.getSystemDateTime();
//        String user = UserProfileUtils.getUserNameLogin();
        EfoComponent objectSave = modelMapper.map(objectDto, EfoComponent.class);
        if (objectSave != null) {            
            if (objectSave.getId() == null) {
                // Add history change
                // TODO
            } else {
//            	EfoComponent objectCurrent = componentRepository.findOne(objectDto.getComponentId());
                // Keep value not change
                // TODO
            }
            objectResult = componentRepository.save(objectSave);
        }
        return objectResult;
    }

	/**
     * deleteComponent
     * @param id
     * @return
     * @author HungHT
     */
    public boolean deleteComponent(Long id) {
//		Date sysDate = comService.getSystemDateTime();
//        String user = UserProfileUtils.getUserNameLogin();
        EfoComponent object = componentRepository.findOne(id);
        // TODO
        componentRepository.save(object);
        return true;
    }

    /**
     * @author KhuongTH
     */
    @Override
    public List<EfoComponentDto> getListcomponentByBusinessId(Long businessId) {
        return componentRepository.getListcomponentByBusinessId(businessId);
    }
}