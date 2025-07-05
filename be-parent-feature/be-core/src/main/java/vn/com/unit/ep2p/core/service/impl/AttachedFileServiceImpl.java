/*******************************************************************************
 * Class        ：AttachedFileServiceImpl
 * Created date ：2019/02/19
 * Lasted date  ：2019/02/19
 * Author       ：VinhLT
 * Change log   ：2019/02/19：01-00 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.ep2p.core.dto.AttachedFileDto;
import vn.com.unit.ep2p.core.entity.AttachedFile;
import vn.com.unit.ep2p.core.repository.AssetAttachedFileRepository;
import vn.com.unit.ep2p.core.service.AttachedFileService;
import vn.com.unit.ep2p.core.service.RepositoryService;

/**
 * AttachedFileServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author VinhLT
 */
@Service
@Transactional(readOnly = true)
public class AttachedFileServiceImpl implements AttachedFileService {

    @Autowired
    private AssetAttachedFileRepository attachedFileRepository;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ServletContext context;

    private static final String REPO_ATTACH_FILE = "REPO_ATTACH_FILE";

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.jcanary.service.AttachedFileService#initComponent(org.
     * springframework.web.servlet.ModelAndView, java.lang.String, java.lang.String)
     */
    @Override
    public void initComponent(ModelAndView mav, String businessCode, String refAttachment, boolean isShowAction,
            String itemPermission) {
        List<AttachedFile> lstAttachedFile = new ArrayList<AttachedFile>();
        if (StringUtils.isEmpty(refAttachment)) {
            refAttachment = UUID.randomUUID().toString();
        } else {
            lstAttachedFile = attachedFileRepository.findListAttachedFileByRef(refAttachment);
        }

        mav.addObject("refAttachment", refAttachment);
        mav.addObject("lstAttachedFile", lstAttachedFile);
        mav.addObject("businessCode", businessCode);
        mav.addObject("isShowActionAttachedFile", isShowAction);
        mav.addObject("attachedFileDto", new AttachedFileDto());
        mav.addObject("itemPermission", itemPermission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<AttachedFile> processAttachedFile(AttachedFileDto attachedFileDto)
            throws IllegalStateException, IOException {
        List<AttachedFile> lstAttachedFile = new ArrayList<AttachedFile>();
        String repositoryFolder = repositoryService.getPathByRepository(REPO_ATTACH_FILE);
        String folder = "";
        if (attachedFileDto.getBusinessCode() != null && !attachedFileDto.getBusinessCode().isEmpty()) {
            folder = attachedFileDto.getBusinessCode();
        } else {
            folder = "" + Calendar.getInstance().get(Calendar.YEAR)
                    + String.format("%02d", Calendar.getInstance().get(Calendar.MONTH));
        }

        String addressSource = repositoryFolder + File.separatorChar + folder;

        File theDir = new File(addressSource);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        String tempFolder = repositoryService.getPathByRepository(SystemConfig.REPO_UPLOADED_TEMP);

        File tempDir = new File(tempFolder);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        if (attachedFileDto != null) {
            if (attachedFileDto.getUploadingFiles() != null && attachedFileDto.getUploadingFiles().length > 0) {
                for (MultipartFile file : attachedFileDto.getUploadingFiles()) {
                    String randomName = UUID.randomUUID().toString();
                    String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
                    String uniqueName = randomName + (StringUtils.isBlank(fileType) ? "" : ("." + fileType));
                    AttachedFile attachedFile = new AttachedFile();
                    attachedFile.setBusinessCode(attachedFileDto.getBusinessCode());
                    attachedFile.setCreatedBy(UserProfileUtils.getUserNameLogin());
                    attachedFile.setCreatedDate(new Date());
                    attachedFile.setFileType(fileType);
                    attachedFile.setFileName(file.getOriginalFilename());
                    attachedFile.setReference(attachedFileDto.getReference());
                    attachedFile.setFileSize(String.valueOf(file.getSize()) + " bytes");
                    attachedFile.setUniqueFileName(uniqueName);
                    attachedFile.setTitle(attachedFileDto.getTitle());
                    attachedFile.setNotes(attachedFileDto.getNotes());

                    // systemConfig.getConfig(AppSystemConfig.TEMP_FOLDER)
                    File sourceFile = new File(tempFolder + System.getProperty("file.separator")
                            + file.getOriginalFilename() + CommonDateUtil.getSystemDateTime().getTime());

                    file.transferTo(sourceFile);

                    String desFile = addressSource + File.separatorChar + uniqueName;
                    try (FileInputStream is = new FileInputStream(sourceFile);) {
                        FileChannel source = is.getChannel();
                        try (FileOutputStream os = new FileOutputStream(new File(desFile));) {
                            FileChannel destination = os.getChannel();
                            destination.transferFrom(source, 0, source.size());
                        }
                    }
                    sourceFile.delete();
                    attachedFile.setLocatedFolder(folder);

                    attachedFileRepository.save(attachedFile);

                    lstAttachedFile.add(attachedFile);
                }

            }
        }
        return attachedFileRepository.findListAttachedFileByRef(attachedFileDto.getReference());
    }

    @Override
    public void downloadAttachedFile(Long id, HttpServletResponse response) throws Exception {
        String repositoryFolder = repositoryService.getPathByRepository(REPO_ATTACH_FILE);
        AttachedFile attachedFile = attachedFileRepository.findOne(id);
        String fileName = attachedFile.getFileName();
        String sourcePath = repositoryFolder + File.separatorChar + attachedFile.getLocatedFolder() + File.separatorChar
                + attachedFile.getUniqueFileName();
        File sourceFile = new File(sourcePath);

        if (sourceFile.exists()) {
            String mimeType = context.getMimeType(sourceFile.getPath());

            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.addHeader("Content-Disposition",
                    "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8").replace("+", " ") + "\"");
            response.setContentLength((int) sourceFile.length());

            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(sourceFile);
            byte[] buffer = new byte[4096];
            int b = -1;

            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }

            fis.close();
            os.close();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * vn.com.unit.jcanary.service.AttachedFileService#deleteFileById(java.lang.
     * Long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFileById(Long id) {
        AttachedFile attachedFile = attachedFileRepository.findOne(id);
        attachedFileRepository.delete(id);
        String sourcePath = attachedFile.getLocatedFolder() + File.separatorChar + attachedFile.getUniqueFileName();
        File sourceFile = new File(sourcePath);
        sourceFile.delete();
    }
}
