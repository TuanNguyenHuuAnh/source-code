/*******************************************************************************
 * Class        ：UserGuideServiceImpl
 * Created date ：2019/11/12
 * Lasted date  ：2019/11/12
 * Author       ：taitt
 * Change log   ：2019/11/12：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.common.exception.AppException;
import vn.com.unit.common.service.JCommonService;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.dto.JcaUserGuideDto;
import vn.com.unit.core.entity.JcaUserGuide;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.impl.JcaUserGuideServiceImpl;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.DirectoryConstant;
import vn.com.unit.ep2p.admin.controller.UserGuideController;
import vn.com.unit.core.dto.FileResultDto;
import vn.com.unit.ep2p.admin.repository.UserGuideRepository;
import vn.com.unit.ep2p.admin.service.CompanyService;
import vn.com.unit.ep2p.admin.service.BeAdminFileService;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.ep2p.admin.service.UserGuideService;
import vn.com.unit.ep2p.utils.FileUtil;
import vn.com.unit.storage.dto.JcaRepositoryDto;

/**
 * UserGuideServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserGuideServiceImpl extends JcaUserGuideServiceImpl implements UserGuideService {

    @Autowired
    private UserGuideRepository userGuideRepository;
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private JRepositoryService jRepositoryService;
    
    @Autowired
    private JRepositoryService repositoryService;
    
    @Autowired
    private BeAdminFileService fileService;
    
    @Autowired
    private JCommonService commonService;
    
    // Model mapper
    ModelMapper modelMapper = new ModelMapper();
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(UserGuideController.class);

    @Override
    public ResponseEntity<byte[]> getUserGuideFileById(Long id) {
        ResponseEntity<byte[]> response = null;
        try{
            
            JcaUserGuideDto userGuideDto = userGuideRepository.getUserGuideDtoByIdAndLanguage(id);
            if (null == userGuideDto){
                throw new AppException("userGuide is not value.", "D001", null);
            }
            
            String pathFile = userGuideDto.getFilePath();
            byte[] contentBytes = null;
            
            String ecmSite = null;
            Long ecmRepositoryId = userGuideDto.getFileRepoId();
            String fileNameUserGuide = userGuideDto.getFileName();
            
            JcaRepositoryDto repoDto  = repositoryService.getRepositoryDtoById(ecmRepositoryId);
            if (null != repoDto){
                InputStream is =  fileService.getInputStreamByRepositoryIdAndFilePath(ecmRepositoryId, pathFile);
                if (is == null) {
                    logger.error("ECM - Get file not found with formFileName=" + pathFile + ", ecmSite=" + ecmSite);
                    throw new AppException("B103",null);
                }
                contentBytes = IOUtils.toByteArray(is);
            }else {
                throw new AppException("B103", null);
            }

           // Create header
           HttpHeaders header = new HttpHeaders();
           header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileNameUserGuide + "\"");
           header.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
           header.add(HttpHeaders.CONTENT_TYPE, FileUtil.getContentType(fileNameUserGuide));
           header.add(HttpHeaders.PRAGMA, "no-cache");
           header.add(HttpHeaders.EXPIRES, "0");
           response = new ResponseEntity<>(contentBytes, header, HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
        }
       
        return response;
    }

    /* (non-Javadoc)
     * @see vn.com.unit.ppl.service.UserGuideService#getById(java.lang.Long)
     */
    @Override
    public JcaUserGuideDto getById(Long id) {
        
        return userGuideRepository.getUserGuideDtoByIdAndLanguage(id);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.ppl.service.UserGuideService#getListUserGuideDtoByCompanyIdAndLocale(java.lang.Long, java.util.List)
     */
    @Override
    public List<JcaUserGuideDto> getListUserGuideDtoByCompanyIdAndLocale(Long companyId, List<String> Listlang,String appCode ) {
        
        return userGuideRepository.getListUserGuideDtoByCompanyIdAndLanguage(companyId, Listlang,appCode);
    }

    /* (non-Javadoc)
     * @see vn.com.unit.ppl.service.UserGuideService#getListUserGuideDto()
     */
    @Override
    public List<JcaUserGuideDto> getListUserGuideDto() {
       
        return userGuideRepository.getUserGuideDto();
    }

    /* (non-Javadoc)
     * @see vn.com.unit.ppl.service.UserGuideService#saveUserGuide(vn.com.unit.ppl.dto.UserGuideDto)
     */
    @Override
    public void saveUserGuide(JcaUserGuideDto userGuideDto,Locale locale) throws Exception {
        try{
          
            // save file to ecm
            saveFileLocal(userGuideDto,locale);
 
        }catch(Exception ex){
            throw new Exception();
        }

    }
    
    public JcaUserGuideDto saveFileLocal(JcaUserGuideDto userGuideDto,Locale locale) throws Exception{
        Long companyId = userGuideDto.getCompanyId();
        String appCode = userGuideDto.getAppCode();
        String userName = UserProfileUtils.getUserNameLogin();
        Date dateRule = commonService.getSystemDate();
        
        List<JcaUserGuideDto> listUserGuideDto = new ArrayList<>();
        listUserGuideDto = userGuideDto.getListUserGuilde();
        if (listUserGuideDto.size() > 0 && null != listUserGuideDto){
            for (JcaUserGuideDto dto: listUserGuideDto){
                if (null != dto.getFileUserGuide()){
                    // save file server 
                    MultipartFile filedatas = dto.getFileUserGuide();
                    String name =filedatas.getOriginalFilename();
                    String docNameRepo = CommonStringUtil.removeAccent(name);
                    docNameRepo = docNameRepo.trim().replaceAll(ConstantCore.SPACE,ConstantCore.UNDERSCORE);
                    
                    String systemCode = companyService.getSystemCodeByCompanyId(companyId);
                    String subFilePath = DirectoryConstant.USER_GUIDE_FOLDER.concat(systemCode);
                    FileResultDto repoResultDto = jRepositoryService.uploadFileBySettingKey(filedatas,
                            userName, SystemConfig.REPO_UPLOADED_MAIN, 2, dateRule, subFilePath, companyId, locale);
                    if (repoResultDto.isStatus()) {
                        dto.setFilePath(repoResultDto.getFilePath());
                        dto.setFileRepoId(repoResultDto.getRepositoryId());
                    } else {
                        throw new AppException(repoResultDto.getMessage(), null, null);
                    }

                    dto.setFileName(docNameRepo);
                    dto.setCompanyId(companyId);
                    dto.setAppCode(appCode);
                    /** save data */
                    saveUserGuideEntity(dto);
                }
            }
        }
        
        
        
   
        return userGuideDto;
    }
    
    public boolean saveUserGuideEntity(JcaUserGuideDto userGuideDto){
        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getAccountId();
        JcaUserGuide objectSave = modelMapper.map(userGuideDto, JcaUserGuide.class);
        
        if (objectSave != null) {            
            if (objectSave.getId() == null) {
                objectSave.setCreatedId(userId);
                objectSave.setCreatedDate(sysDate);
                objectSave.setUpdatedId(userId);
                objectSave.setUpdatedDate(sysDate);
                
                objectSave.setLangCode(userGuideDto.getLangCode());
                userGuideRepository.create(objectSave);
            } else {
                JcaUserGuide objectCurrent = userGuideRepository.findOne(userGuideDto.getId());
                // Keep value not change
                objectSave.setCreatedId(objectCurrent.getCreatedId());
                objectSave.setCreatedDate(objectCurrent.getCreatedDate());
                objectSave.setUpdatedId(userId);
                objectSave.setUpdatedDate(sysDate);
                //objectSave.setActionType(objectCurrent.getActionType());
                
                objectSave.setLangCode(userGuideDto.getLangCode());
                userGuideRepository.update(objectSave);
            }
//            userGuideRepository.save(objectSave);
            userGuideDto.setId(objectSave.getId());
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see vn.com.unit.ppl.service.UserGuideService#deleteUserGuide(java.lang.Long)
     */
    @Override
    public void deleteUserGuide(Long id) {
        Date sysDate = commonService.getSystemDate();
        Long userId = UserProfileUtils.getAccountId();
        JcaUserGuide objectCurrent = userGuideRepository.findOne(id);
        objectCurrent.setDeletedId(userId);
        objectCurrent.setDeletedDate(sysDate);
        userGuideRepository.update(objectCurrent);
    }

}
