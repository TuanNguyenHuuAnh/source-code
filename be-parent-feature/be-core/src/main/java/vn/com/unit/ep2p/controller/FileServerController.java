package vn.com.unit.ep2p.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.com.unit.common.exception.SystemException;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.service.CommonService;
import vn.com.unit.core.service.JRepositoryService;
import vn.com.unit.ep2p.admin.constant.ConstantCore;
import vn.com.unit.ep2p.admin.constant.Utils;
import vn.com.unit.ep2p.dialect.FileMeta;
import vn.com.unit.ep2p.utils.FileUtil;
import vn.com.unit.storage.dto.FileDownloadParam;
import vn.com.unit.storage.dto.FileDownloadResult;
import vn.com.unit.storage.entity.JcaRepository;
import vn.com.unit.storage.service.FileStorageService;

@Controller
@RequestMapping("/ajax-file")
public class FileServerController {

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private JRepositoryService jRepositoryService;
	
	@Autowired
    private CommonService comService;

	@Autowired
	private FileStorageService fileStorageService;
	
	/** files */
	private LinkedList<FileMeta> files = new LinkedList<FileMeta>();

	/** fileMeta */
	private FileMeta fileMeta = null;

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(FileServerController.class);

	/** UNDERSCORE */
	private static final String UNDERSCORE = "__";

	/** EXTENSION OF IMAGE FILES */
	private static final String EXTENSION_IMAGE = "jpg-jpeg-bmp-png-tiff-pnj";

	/** EXTENSION OF DOCUMENT FILES */
	private static final String EXTENSION_DOCUMENT = "doc-docx-xls-xlsx-pdf";

	/** MESSAGE FOR FILE EXTENSION NOT SUPPORT */
	private static final String MSG_ERROR_EXTENSION_INVALID = "File format is not supported";

	/**
	 * upload to folder temp
	 *
	 * @param request
	 * @param request2
	 * @param model
	 * @param response
	 * @return String
	 * @author hand
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadFileTemp", method = RequestMethod.POST)
	public @ResponseBody String uploadTemp(MultipartHttpServletRequest request, HttpServletRequest request2,
			Model model, HttpServletResponse response) throws IOException {

		// build an iterator
		Iterator<String> itr = request.getFileNames();

		// MultipartFile
		MultipartFile mpf = null;

		// source
		String source = StringUtils.EMPTY;

		// path temp
		String path = jRepositoryService.getPathByRepository(SystemConfig.REPO_UPLOADED_TEMP);

		// check connect to repository
		boolean isConnected = jRepositoryService.checkConnectToSystemSettingRepository();
		if (!isConnected) {
			String msgError = "Could not connect to repository";
			throw new SystemException(msgError);

		}

		if (!StringUtils.isEmpty(path)) {
			// create directory if not exists
			FileUtil.createDirectoryNotExists(path);

			// loop iterator filenames
			while (itr.hasNext()) {
				// get next MultipartFile
				mpf = request.getFile(itr.next());

				// extensao
				String extensao = StringUtils.EMPTY;

				int extentionIndex = mpf.getOriginalFilename().lastIndexOf('.');

				// file khong co extent
				if (extentionIndex == -1) {
					logger.error(MSG_ERROR_EXTENSION_INVALID);
					throw new IOException(MSG_ERROR_EXTENSION_INVALID);
				} else {
					extensao = mpf.getOriginalFilename()
							.substring(extentionIndex + 1, mpf.getOriginalFilename().length()).toLowerCase();
					if (!EXTENSION_DOCUMENT.contains(extensao) && !EXTENSION_IMAGE.contains(extensao)) {
						logger.error(MSG_ERROR_EXTENSION_INVALID);
						throw new IOException(MSG_ERROR_EXTENSION_INVALID);
					}
					// remove dau, ky tu dac biet
					String newName = Utils.convertNewFileName(mpf.getOriginalFilename().substring(0, extentionIndex));
					source = ConstantCore.AT_FILE + newName + UNDERSCORE + (comService.getSystemDateTime()).getTime() + ConstantCore.DOT
							+ extensao;
					if ("gifimgjpejpgejpgpngtiff".contains(extensao)) {
						String subPath = Paths.get(path, "account_avatar").toString();
						File file = new File(subPath);
						if (!file.exists()) {
							try {
								file.mkdir();
							} catch (Exception e) {
								logger.error("Error: " + e);
							}
						}
						source = "account_avatar/" + source;
					}
				}

				try {
					// save to folder temp
					FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(Paths.get(path, source).toString()));
				} catch (FileNotFoundException e) {
					// log file not found
					logger.error(e + ":" + e.getMessage());
					throw new SystemException(e.getMessage());
				} catch (IOException e) {
					// log IOException
					logger.error(e + ":" + e.getMessage());
					throw new SystemException(e.getMessage());
				}
			}

		} else {
			logger.error("path not found");
		}

		return source;
	}

    /**
     * download
     * 
     * @param filePath
     * @param fileName
     * @param repositoryId
     * @param request
     * @param response
     * @author HungHT
     */
    @RequestMapping(value = "/download", method = { RequestMethod.GET, RequestMethod.POST })
    public void download(@RequestParam(required = true, value = "filePath") String filePath,
            @RequestParam(required = false, value = "fileName") String fileName,
            @RequestParam(required = true, value = "repositoryId") Long repositoryId, HttpServletRequest request,
            HttpServletResponse response) {

        if (StringUtils.isEmpty(filePath)) {
            return;
        }

        // Get folder repository
        JcaRepository repo = systemConfig.getRepoById(repositoryId, null);
        if (null == repo) {
            return;
        }

        try {
//            int numRead = -1;
            FileDownloadParam fileDownloadParam = new FileDownloadParam();
            fileDownloadParam.setFilePath(filePath);
            fileDownloadParam.setRepositoryId(repositoryId);

            FileDownloadResult fileDownloadResult = fileStorageService.download(fileDownloadParam);
            byte[] bpmnContent = fileDownloadResult.getFileByteArray();
            String fileNameResult = fileDownloadResult.getFileName();
            MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
            String mimeType = fileTypeMap.getContentType(fileNameResult);
            
            response.setContentLength(bpmnContent.length);
            response.setContentType(mimeType);
            response.addHeader("Content-Disposition",
                    "inline; filename=\"" + (StringUtils.isNotBlank(fileName) ? fileName : fileNameResult) + "\"");
            response.getOutputStream().write(bpmnContent, 0, bpmnContent.length);
            
//            Path path = Paths.get(repo.getPhysicalPath(), filePath);
//            String url = StringUtils.EMPTY;
//            String domain = StringUtils.EMPTY;
//            byte[] arr = new byte[1024];
//            int numRead = -1;
//            if (StringUtils.isNotEmpty(request.getParameter("isDownload"))
//                    && request.getParameter("isDownload").equalsIgnoreCase("download")) {
//                response.setContentType("application/octet-stream");
//            } else {
//                response.setContentType(FileUtil.getContentType(filePath));
//            }
//            if (FileProtocol.LOCAL.getValue()!=repo.getFileProtocol()) {
//                url = jRepositoryService.generateSmbPath(path.toString());
//                NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, repo.getUsername(), repo.getPassword());
//                SmbFile sFile = new SmbFile(url, auth);
//                SmbFileInputStream sfis = new SmbFileInputStream(sFile);
//                response.addHeader("Content-Length", Long.toString(sFile.length()));
//                response.addHeader("Content-Disposition",
//                        "inline; filename=\"" + (StringUtils.isNotBlank(fileName) ? fileName : sFile.getName()) + "\"");
//                while ((numRead = sfis.read(arr)) != -1) {
//                    response.getOutputStream().write(arr, 0, numRead);
//                }
//                sfis.close();
//            } else {
//                // path main
//                String pathMain = path.toString();
//
//                File fileTempCheck = new File(Paths.get(domain, pathMain).toAbsolutePath().toString());
//
//                if (!fileTempCheck.exists() && filePath.contains(ConstantCore.AT_FILE)) {
//                    // path temp
//                    url = jRepositoryService.getPathByRepository(filePath, AppSystemConfig.REPO_UPLOADED_TEMP);
//                } else {
//                    url = pathMain;
//                }
//                File fileTemp = new File(FilenameUtils.separatorsToSystem(Paths.get(domain, url).toString()));
//
//                FileInputStream fileInputStream = new FileInputStream(fileTemp);
//
//                response.addHeader("Content-Length", Long.toString(fileTemp.length()));
//
//                response.addHeader("Content-Disposition",
//                        "inline; filename=\"" + (StringUtils.isNotBlank(fileName) ? fileName : fileTemp.getName()) + "\"");
//                while ((numRead = fileInputStream.read(arr)) != -1) {
//                    response.getOutputStream().write(arr, 0, numRead);
//                }
//                fileInputStream.close();
//            }
        } catch (Exception e) {
            logger.error(e + ":" + e.getMessage());
        }
    }

    @RequestMapping(value = "/download-login", method = RequestMethod.GET)
    public void downloadBypassAuthentication(@RequestParam(required = true, value = "filePath") String filePath,
            @RequestParam(required = false, value = "fileName") String fileName,
            @RequestParam(required = true, value = "repositoryId") Long repositoryId, HttpServletRequest request,
            HttpServletResponse response) {
        download(filePath, fileName, repositoryId, request, response);
    }

	/***************************************************
	 * URL: /rest/controller/upload upload(): receives files
	 * 
	 * @param request
	 *            : MultipartHttpServletRequest auto passed
	 * @param response
	 *            : HttpServletResponse auto passed
	 * @return LinkedList<FileMeta> as json format
	 ****************************************************/
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> upload(MultipartHttpServletRequest request,
			HttpServletResponse response) {

		// Build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		// 2. get each file
		while (itr.hasNext()) {

			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());

			// 2.2 if files > 10 remove the first from the list
			if (files.size() >= 10)
				files.pop();

			// 2.3 create new fileMeta
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			fileMeta.setFileSize(mpf.getSize());
			fileMeta.setFileType(mpf.getContentType());
			fileMeta.setPhysicalFileName(mpf.getOriginalFilename() + (comService.getSystemDateTime()).getTime());

			try {
				fileMeta.setBytes(mpf.getBytes());
				logger.debug("temp path: {}", systemConfig.getConfig(SystemConfig.TEMP_FOLDER)
						+ System.getProperty("file.separator") + mpf.getOriginalFilename());
				// copy file to local disk (make sure the path
				// "e.g. D:/temp/files" exists)
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(systemConfig.getConfig(SystemConfig.TEMP_FOLDER)
						+ System.getProperty("file.separator") + fileMeta.getPhysicalFileName()));

			} catch (IOException e) {
				logger.error(e + ":" + e.getMessage());
			}
			// 2.4 add to files
			files.add(fileMeta);

		}

		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8
		// Kb","fileType":"image/png"},...]
		return files;

	}

	@RequestMapping(value = "/uploadDoc", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	// public @ResponseBody FileMeta uploadDocument(MultipartHttpServletRequest
	// request, HttpServletResponse response) {
	public @ResponseBody String uploadDocument(MultipartHttpServletRequest request, HttpServletResponse response) {
		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		// 2. get each file
		if (itr.hasNext()) {
			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());

			// 2.2 if files > 10 remove the first from the list
			if (files.size() >= 10)
				files.pop();

			// 2.3 create new fileMeta
			String fileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf('.') + 1);
			if (!EXTENSION_DOCUMENT.contains(fileExtension)) {
				logger.error(MSG_ERROR_EXTENSION_INVALID);
				return StringUtils.EMPTY;
			}
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			fileMeta.setFileSize(mpf.getSize());
			fileMeta.setFileType(fileExtension);
			fileMeta.setPhysicalFileName(Utils.convertNewFileName(
					mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')))
					+ (comService.getSystemDateTime()).getTime() + ConstantCore.DOT + fileMeta.getFileType());
			try {
				fileMeta.setBytes(mpf.getBytes());
				String temp_folder = systemConfig.getConfig(SystemConfig.TEMP_FOLDER);
				temp_folder = temp_folder.endsWith(System.getProperty("file.separator")) == true ? temp_folder
						: temp_folder + System.getProperty("file.separator");
				logger.debug("temp path: {}", temp_folder + fileMeta.getPhysicalFileName());
				// copy file to local disk (make sure the path
				// "e.g. D:/temp/files" exists)
				File f = new File(temp_folder + fileMeta.getPhysicalFileName());
				if (!f.exists())
					FileUtil.setPermission(f);
				f.createNewFile();
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(f));

			} catch (IOException e) {
				logger.error(e + ":" + e.getMessage());
			}
			// 2.4 add to files

		}
		response.setContentType("text/plain");
		response.setHeader("Content-Type", "text/plain");
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8
		// Kb","fileType":"image/png"},...]
		ObjectMapper mapper = new ObjectMapper();
		String result = StringUtils.EMPTY;
		try {
			result = mapper.writeValueAsString(fileMeta);
		} catch (JsonGenerationException e) {
			logger.error(e + ":" + e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e + ":" + e.getMessage());
		} catch (IOException e) {
			logger.error(e + ":" + e.getMessage());
		}
		// return fileMeta;
		return result;
	}

	/***************************************************
	 * URL: /rest/controller/get/{value} get(): get file as an attachment
	 * 
	 * @param response
	 *            : passed by the server
	 * @param value
	 *            : value from the URL
	 * @return void
	 ****************************************************/
	@RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
	public void get(HttpServletResponse response, @PathVariable String value) {
		FileMeta getFile = files.get(Integer.parseInt(value));
		try {
			response.setContentType(getFile.getFileType());
			response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getFileName() + "\"");
			FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
		} catch (IOException e) {
			logger.error(e + ":" + e.getMessage());
		}
	}

	@RequestMapping(value = "/showVideo_PopupHomepage", method = RequestMethod.GET)
	public String showVideo_PopupHomepage(Model model, HttpServletRequest request, Locale locale) throws Exception {

		String fileName = request.getParameter("fileName");
		String language = request.getParameter("language");
		model.addAttribute("fileName", fileName);
		model.addAttribute("language", language);

		return "master_data.popup_homepage.video";
	}

	@RequestMapping(value = "/preview", method = RequestMethod.GET)
	public void preview(@RequestParam(required = true) String fileName,
			@RequestParam(required = false) String downloadViewID, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			File fileTemp = new File(servletContext.getRealPath("/WEB-INF/temp_folder")
					+ System.getProperty("file.separator") + fileName);

			FileInputStream fileInputStream = new FileInputStream(fileTemp);
			byte[] arr = new byte[1024];
			int numRead = -1;

			response.addHeader("Content-Length", Long.toString(fileTemp.length()));
			response.setContentType("application/pdf");

			response.addHeader("Content-Disposition", "attachment; filename=\"" + fileTemp.getName() + "\"");
			while ((numRead = fileInputStream.read(arr)) != -1) {
				response.getOutputStream().write(arr, 0, numRead);
			}
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/uploadFileChat", method = RequestMethod.POST)
	public @ResponseBody String uploadFileChat(MultipartHttpServletRequest request, HttpServletRequest request2,
			Model model, HttpServletResponse response) throws IOException {

		// build an iterator
		Iterator<String> itr = request.getFileNames();

		// MultipartFile
		MultipartFile mpf = null;

		// source
		String source = StringUtils.EMPTY;

		// path temp
		String folderName = "chat/";
		String path = jRepositoryService.getPathByRepository(SystemConfig.REPO_UPLOADED_MAIN);

		if (!StringUtils.isEmpty(path)) {
			// create directory if not exists
			FileUtil.createDirectoryNotExists(path);

			// loop iterator filenames
			while (itr.hasNext()) {
				// get next MultipartFile
				mpf = request.getFile(itr.next());

				// extensao
				String extensao = StringUtils.EMPTY;

				// file khong co extent
				if (mpf.getOriginalFilename().lastIndexOf('.') == -1) {
					source = ConstantCore.AT_FILE
							+ mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.'))
							+ UNDERSCORE + (comService.getSystemDateTime()).getTime();
				} else {
					extensao = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf('.') + 1,
							mpf.getOriginalFilename().length()).toLowerCase();

					// remove dau, ky tu dac biet
					String newName = Utils.convertNewFileName(
							mpf.getOriginalFilename().substring(0, mpf.getOriginalFilename().lastIndexOf('.')));
					source = newName + UNDERSCORE + (comService.getSystemDateTime()).getTime() + ConstantCore.DOT + extensao;
				}

				try {
					// save to folder temp
					FileCopyUtils.copy(mpf.getBytes(),
							new FileOutputStream(FilenameUtils.separatorsToSystem(path + source)));
				} catch (FileNotFoundException e) {
				} catch (IOException e) {
				}
			}

		} else {
		}

		return (folderName + source);
	}
}
