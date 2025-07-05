/**
 * @author TaiTM
 */
package vn.com.unit.ep2p.core.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import vn.com.unit.common.utils.CommonDateUtil;
import vn.com.unit.core.security.UserPrincipal;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaSystemConfigService;
import vn.com.unit.ep2p.core.service.BeCoreFileService;
import vn.com.unit.ep2p.core.utils.FileUtil;
import vn.com.unit.imp.excel.constant.ConstantCore;
import vn.com.unit.storage.entity.JcaRepository;
import vn.com.unit.storage.service.JcaRepositoryService;

/**
 * @author TaiTM
 *
 */
@Service("coreFileService")
public class BeCoreFileServiceImpl implements BeCoreFileService {

    @Autowired
    private JcaRepositoryService jcaRepositoryService;

    @Autowired
    @Qualifier("appSystemConfigServiceImpl")
    private JcaSystemConfigService jcaSystemConfigService;

    @Override
    public String uploadTemp(MultipartFile mpf, String destFolderName, String destSubFolderName) throws IOException {
        // source
        String source = StringUtils.EMPTY;

        // path temp
        JcaRepository repo = jcaRepositoryService.getJcaRepositoryById(getRepositoryTempId());
        String tempPath = repo.getPhysicalPath();

        if (!StringUtils.isEmpty(tempPath)) {
            // create directory if not exists
            String path = Paths.get(tempPath, destFolderName, destSubFolderName).toString();
            File destSubFolder = new File(path);
            path = destSubFolder.getPath();
            FileUtil.createDirectoryNotExists(path);

            // extensao
            String extensao = StringUtils.EMPTY;
            // file khong co extent
            if (mpf.getOriginalFilename().lastIndexOf('.') == -1) {
                source = ConstantCore.AT_FILE
                        + mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.'))
                        + ConstantCore.UNDERSCORE + (CommonDateUtil.getSystemDateTime()).getTime();
            } else {
                extensao = mpf.getOriginalFilename()
                        .substring(mpf.getOriginalFilename().lastIndexOf('.') + 1, mpf.getOriginalFilename().length())
                        .toLowerCase();
                String newName = convertNewFileName(
                        mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')));
                source = ConstantCore.AT_FILE + newName + ConstantCore.UNDERSCORE
                        + (CommonDateUtil.getSystemDateTime()).getTime() + ConstantCore.DOT + extensao;
            }
            try {
                File destFile = new File(path, source);
                FileUtil.setPermission(destFile);
                FileCopyUtils.copy(mpf.getBytes(),
                        new FileOutputStream(FilenameUtils.separatorsToSystem(destFile.getPath())));
            } catch (FileNotFoundException e) {
                // logger.error(e + ":" + e.getMessage());
            }
        } else {
            // logger.error("path not found");
        }

        return Paths.get(destSubFolderName, source).toString();
    }

    private String convertNewFileName(String input) {
        String output = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(input)) {
            output = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("đ", "d")
                    .replaceAll("[^\\p{ASCII}]", "").replaceAll("[^\\p{Alpha}\\p{Digit}]+", "");
        }
        return output;
    }

    private Long getRepositoryTempId() {
        Long companyId = Optional.ofNullable(UserProfileUtils.getUserPrincipal()).orElse(new UserPrincipal())
                .getCompanyId();
        if (companyId == null) {
            companyId = 1L;
        }
        Long id = Long.valueOf(jcaSystemConfigService.getValueByKey("REPO_UPLOADED_TEMP", companyId));

        return id;
    }
}
