/*******************************************************************************
 * Class        ：RecruitmentServicImpl
 * Created date ：2017/04/13
 * Lasted date  ：2017/04/26
 * Author       ：phunghn
 * Change log   ：2017/04/26：01-00 phunghn create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.exception.BusinessException;
import vn.com.unit.cms.admin.all.dto.RecruimentSearchDto;
import vn.com.unit.cms.admin.all.dto.RecruitmentDto;
import vn.com.unit.cms.admin.all.entity.Recruitment;
import vn.com.unit.cms.admin.all.enumdef.RecruitmentSearchEnum;
import vn.com.unit.cms.admin.all.jcanary.dto.BranchDto;
import vn.com.unit.cms.admin.all.jcanary.repository.BranchRepository;
import vn.com.unit.cms.admin.all.repository.RecruitmentRepository;
import vn.com.unit.cms.admin.all.service.RecruitmentService;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.common.dto.PageWrapper;
//import vn.com.unit.jcanary.config.SystemConfig;
//import vn.com.unit.jcanary.dto.BranchDto;
//import vn.com.unit.jcanary.repository.BranchRepository;
import vn.com.unit.ep2p.core.utils.Utility;

/**
 * RecruitmentServicImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author phunghn
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RecruitmentServicImpl implements RecruitmentService {
    @Autowired
    RecruitmentRepository recruitmentRepository;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    BranchRepository branchRepository;

    /**
     * setSearchParam
     *
     * @param recSearchDto
     * @author phunghn
     */
    private void setSearchParam(RecruimentSearchDto recSearchDto) {
        if (recSearchDto.getFieldValues() != null && recSearchDto.getFieldValues().size() > 0) {
            for (String item : recSearchDto.getFieldValues()) {
                if (item.equals(RecruitmentSearchEnum.POSITION.name())) {
                    recSearchDto.setPosition(recSearchDto.getFieldSearch().trim());
                }
            }
        }
    }

    /**
     * findAllActive
     *
     * @param page
     * @param recDto
     * @return PageWrapper<RecruitmentDto>
     * @author phunghn
     */
    @Override
    public PageWrapper<RecruitmentDto> findAllActive(int page, RecruimentSearchDto recDto) {
        // TODO Auto-generated method stub
        int sizeOfPage = systemConfig.getIntConfig(SystemConfig.PAGING_SIZE);
        PageWrapper<RecruitmentDto> pageWrapper = new PageWrapper<RecruitmentDto>(page, sizeOfPage);
        try {
            // set param
            setSearchParam(recDto);
            //
            int countRecruitment = recruitmentRepository.countRecruitmentDto(recDto);
            List<RecruitmentDto> listRecruitment = new ArrayList<RecruitmentDto>();
            if (countRecruitment > 0) {
                int offsetSQL = Utility.calculateOffsetSQL(page, sizeOfPage);
                listRecruitment = recruitmentRepository.findAllActive(offsetSQL, sizeOfPage, recDto);
                // custom branchNam in list
                for (RecruitmentDto item : listRecruitment) {
                    String[] arrBranch = item.getBranchId().split(",");
                    String branchName = "";
                    if (arrBranch.length > 0) {
                        for (String code : arrBranch) {
                            Long codeBrach = Long.parseLong(code);
                            BranchDto braDto = new BranchDto();
                            braDto = branchRepository.findBranchDtoById(codeBrach);
                            if (braDto != null) {
                                branchName += " - " + braDto.getName();
                                branchName += "</br>";
                            }

                        }

                    }
                    item.setBranchName(branchName);
                    if (recDto.getLangCode().toUpperCase().equals("EN")) {
                        item.setAddress(item.getAddressEn());
                        item.setDescription(item.getDescriptionEn());
                        item.setPosition(item.getPositionEn());
                    }

                }
            }
            pageWrapper.setDataAndCount(listRecruitment, countRecruitment);

        } catch (Exception ex) {
            ex.getMessage();
        }
        return pageWrapper;
    }

    /**
     * getRecruitmentEditdto
     *
     * @param id
     * @return Recruitment
     * @author phunghn
     */
    @Override
    public Recruitment getRecruitmentEditdto(Long id) {
        Recruitment recDto = new Recruitment();
        if (id == null) {
            return recDto;
        }
        recDto = recruitmentRepository.getRecruitmentEditdto(id);
        // set list branchfields;
        String[] arrBranch = recDto.getmBranchId().split(",");
        List<Long> listBranch = new ArrayList<Long>();
        if (arrBranch.length > 0) {
            for (String code : arrBranch) {
                listBranch.add(Long.parseLong(code));
            }
        }
        recDto.setBranchFields(listBranch);
        return recDto;
    }

    /**
     * addOrEdit
     *
     * @param recDto
     * @author phunghn
     */
    @Override
    @Transactional
    public void addOrEdit(Recruitment rec) {
        String userName = UserProfileUtils.getUserNameLogin();
        Recruitment recCheck = new Recruitment();
        recCheck = recruitmentRepository.getRecruitmentEditdto(rec.getRecruitmentId());
        // insert

        rec.setUserName(userName);
        if (recCheck == null) {
            rec.setCreatedDate(new Date());
        } else {
            rec.setUpdatedDate(new Date());
        }

        recruitmentRepository.save(rec);
    }

    /**
     * delete
     *
     * @param id
     * @author phunghn
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Recruitment rec = new Recruitment();
        rec = recruitmentRepository.findOne(id);
        if (null == rec) {
            throw new BusinessException("Not found id=" + id);
        }
        String userName = UserProfileUtils.getUserNameLogin();
        rec.setUserName(userName);
        rec.setDeletedDate(new Date());
        recruitmentRepository.save(rec);
    }

}
