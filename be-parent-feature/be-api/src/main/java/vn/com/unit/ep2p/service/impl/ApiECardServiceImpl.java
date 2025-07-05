/*******************************************************************************
 * Class        ：AccountAppServiceImpl
 * Created date ：2021/03/11
 * Lasted date  ：2021/03/11
 * Author       ：Tan Tai
 * Change log   ：2021/03/11：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.ep2p.service.impl;

import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import vn.com.unit.cms.core.module.ecard.dto.ECardReqDto;
import vn.com.unit.cms.core.module.ecard.dto.ECardResDto;
import vn.com.unit.cms.core.module.ecard.repository.ECardRepository;
import vn.com.unit.core.config.SystemConfig;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.repository.JcaConstantRepository;
import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.core.service.JcaAccountService;
import vn.com.unit.ep2p.admin.service.Db2ApiService;
import vn.com.unit.ep2p.core.dto.ECardPdfDto;
import vn.com.unit.ep2p.core.utils.FileUtil;
import vn.com.unit.ep2p.service.ApiECardService;
import vn.com.unit.imp.excel.constant.CommonConstant;

/**
 * AccountAppServiceImpl
 * 
 * @version 01-00
 * @since 01-00
 * @author Tan Tai
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ApiECardServiceImpl implements ApiECardService {

	@Autowired
	ECardRepository eCardRepository;
	
	@Autowired
	JcaConstantRepository jcaConstantRepository;
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	JcaAccountService jcaAccountService;
	
	@Autowired
	Db2ApiService db2ApiService;
	
    private Logger logger = LoggerFactory.getLogger(getClass());

	/** SystemConfigure */
	@Autowired
	SystemConfig appSystemConfig;

	public enum FieldMappingEnum {
		AGENTNAME(""), AGENTTYPE(""), OFFICE(""), PHONE(""), ZALO(""), FACEBOOK(""), EMAIL("");

		private String value;

		FieldMappingEnum(String value) {
			this.value = value;
		}

		public String toString() {
			return value;
		}
	};

	/**
	 * @author: TriNT
	 * @since: 29/09/2021 2:09 CH
	 * @description: gereate image file & pdf file
	 * @update:
	 *
	 */
	@Override
	public List<ECardResDto> geranateECard(ECardReqDto eCardReqDto) throws IllegalAccessException {
    	String agentCode  = UserProfileUtils.getFaceMask();
    	List<JcaAccount> user = jcaAccountService.getListByUserName(agentCode);
		if(ObjectUtils.isNotEmpty(user)) {
			eCardReqDto.setAvatar(user.get(0).getAvatar());
			eCardReqDto.setZalo(user.get(0).getUrlZalo());
			eCardReqDto.setFacebook(user.get(0).getUrlFacebook());
			eCardReqDto.setEmail(user.get(0).getEmail());
			eCardReqDto.setPhone(user.get(0).getPhone());
		}
		
		List<ECardResDto> datas = new ArrayList<>();
		List<ECardPdfDto> lstResult = eCardRepository.findAllECard(UserProfileUtils.getChannel());
		String templatePath = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_PDF) + File.separator;
		String repo = appSystemConfig.getConfig(SystemConfig.REPO_UPLOADED_MAIN);
		String rootFolder = appSystemConfig.getPhysicalPathById(repo, null);
//		List<JcaConstantDto> lstConstant = jcaConstantRepository.getListJcaConstantByGroupCodeAndKind("E-CARD", "TEMPLATE", "vi");
		Map<String, String> mapTemplate = new HashMap<String, String>();
		if ("AD".equals(UserProfileUtils.getChannel())) {
			mapTemplate.put("AD-e-card-tempalte-1", "AD-background-1.png");
		} else {
			mapTemplate.put("e-card-tempalte-1", "background-1.png");
			mapTemplate.put("e-card-tempalte-2", "background-2.png");
			mapTemplate.put("e-card-tempalte-3", "background-3.png");
		}
		// Directories
		String directories = Paths.get(rootFolder).toString();

		// set folderPath
		String folderECard = "E-CARD";
		String pathOut = Paths.get(directories, folderECard).toString();
		Map<String, Object> params = new HashMap<>();
		params.put("pathFile", templatePath);
		int i=0;
		if (!lstResult.isEmpty()) {
			for (ECardPdfDto e : lstResult) {
				Map<Integer, String> mapPosition = new HashMap<>();
				mapPosition.put(e.getAgentName(), eCardReqDto.getAgentName());
				mapPosition.put(e.getAgentType(), eCardReqDto.getAgentType());
				mapPosition.put(e.getOffice(), eCardReqDto.getOffice());
				mapPosition.put(e.getFacebook(), StringUtils.isNotEmpty(eCardReqDto.getFacebook())?"Facebook: "+eCardReqDto.getFacebook():null);
				mapPosition.put(e.getEmail(), StringUtils.isNotEmpty(eCardReqDto.getEmail())?"Email: "+eCardReqDto.getEmail():null);
				mapPosition.put(e.getZalo(), StringUtils.isNotEmpty(eCardReqDto.getZalo())?"Zalo: "+eCardReqDto.getZalo():null);
				if ("AD".equals(UserProfileUtils.getChannel())) {
					mapPosition.put(e.getPhone(), StringUtils.isNotEmpty(eCardReqDto.getPhone())?"ĐTDĐ: "+eCardReqDto.getPhone():null);
				} else {
					mapPosition.put(e.getPhone(), StringUtils.isNotEmpty(eCardReqDto.getPhone())?"Phone: "+eCardReqDto.getPhone():null);
				
					if(StringUtils.isNotEmpty(eCardReqDto.getTaskingWinner())) {
						String label[] = eCardReqDto.getTaskingWinner().split(", ");
						for(String winner: label) {
							e.setLabel2(9);
							e.setLabel3(10);
							e.setLabel4(11);
							e.setLabel5(12);
							if(winner.toString().startsWith("SAMURAI")) { 
								mapPosition.put(e.getLabel(), winner.toString());
							} else if(winner.toString().startsWith("MDRTUUTU")) {
								mapPosition.put(e.getLabel5(), winner.toString());
				            } else if(winner.toString().startsWith("TOT") || winner.toString().startsWith("COT") || winner.toString().startsWith("MDRT")) {
								mapPosition.put(e.getLabel2(), winner.toString());
				            } else if(winner.toString().startsWith("STARTRIP5S") || winner.toString().startsWith("STARTRIP3S")) {
								mapPosition.put(e.getLabel3(), winner.toString());
						    } else if(winner.toString().startsWith("STARNIGHT")) {
								mapPosition.put(e.getLabel4(), winner.toString());
				            } 
						}
					}
					e.setIcon1(setIconSuitable(mapPosition.get(1), templatePath, e.getType()));
	                e.setIcon2(setIconSuitable(mapPosition.get(2), templatePath, e.getType()));
	                e.setIcon3(setIconSuitable(mapPosition.get(3), templatePath, e.getType()));
	                e.setIcon4(setIconSuitable(mapPosition.get(4), templatePath, e.getType()));
	                e.setIcon5(setIconSuitable(mapPosition.get(5), templatePath, e.getType()));
	                e.setIcon6(setIconSuitable(mapPosition.get(6), templatePath, e.getType()));
	                e.setIcon7(setIconSuitable(mapPosition.get(7), templatePath, e.getType()));	
                
	                List<String> sortWinenr = new ArrayList<String>();
	                if(StringUtils.isNotBlank(e.getIcon8())) {
	                sortWinenr.add(e.getIcon8());
	                }  if(StringUtils.isNotBlank(e.getIcon9())) {
	                sortWinenr.add(e.getIcon9());
	                }  if(StringUtils.isNotBlank(e.getIcon10())) {
	                sortWinenr.add(e.getIcon10());
	                }  if(StringUtils.isNotBlank(e.getIcon11())) {
	                sortWinenr.add(e.getIcon11());
	                }  if(StringUtils.isNotBlank(e.getIcon12())) {
	                sortWinenr.add(e.getIcon12());
	                }
	                
	                sortWinenr.forEach(x ->{               	
	                	if(StringUtils.isBlank(e.getIcon8())) {
	                        e.setIcon8(x);
	                    } else if(StringUtils.isBlank(e.getIcon9())) {
	                        e.setIcon9(x);
	                    } else if(StringUtils.isBlank(e.getIcon10())) {
	                        e.setIcon10(x);
	                    } else if(StringUtils.isBlank(e.getIcon11())) {
	                        e.setIcon11(x);
	                    } else if(StringUtils.isBlank(e.getIcon12())) {
	                        e.setIcon12(x);
	                    }
	                });
	                   
	                mapPosition.put(e.getEmail(), StringUtils.isNotEmpty(eCardReqDto.getEmail())?""+eCardReqDto.getEmail():null);
	                mapPosition.put(e.getPhone(), StringUtils.isNotEmpty(eCardReqDto.getPhone())?""+eCardReqDto.getPhone():null);
				}
				e.setPos1(mapPosition.get(1));
				e.setPos2(mapPosition.get(2));
				e.setPos3(mapPosition.get(3));
				e.setPos4(mapPosition.get(4));
				e.setPos5(mapPosition.get(5));
				e.setPos6(mapPosition.get(6));
				e.setPos7(mapPosition.get(7));
				if ("AD".equals(UserProfileUtils.getChannel())) {
					String officeName = db2ApiService.getOfficeName(UserProfileUtils.getFaceMask());
					if (StringUtils.isNotEmpty(officeName)) {
						// Thiết lập tên Văn phòng
						String arrOff[] = officeName.split(": ");
						if (arrOff.length == 2) {
							e.setPos5(arrOff[0]);
							e.setPos6(arrOff[1]);
						}
					}
					e.setPos7(mapPosition.get(7) + " | MST: 030 185 1276");	
				}
				
				String pathServletContext = servletContext.getRealPath(CommonConstant.REAL_PATH_TEMPLATE_PDF);
				String backgroundDefault = pathServletContext + "/" +mapTemplate.get(e.getType());
				String avatarDefault = pathServletContext + "/" + "avata.194f30b8.png";
				String background = StringUtils.isNotEmpty(e.getBackground())?Paths.get(rootFolder, e.getBackground()).toString():"";
                backgroundDefault = FileUtil.isFileExisted(background) ? background : backgroundDefault;
                e.setBackground(backgroundDefault);
				String avatar = eCardReqDto.getAvatar();
				String pathAvatar = StringUtils.isNotEmpty(avatar)? Paths.get(rootFolder, avatar).toString() : avatarDefault;
				e.setAvatar(FileUtil.isFileExisted(pathAvatar) ? pathAvatar : null);
				// set param
				try {

					// String jasperFile = templatePath.concat(e.getType().concat(".jasper"));
					String jasperDesignF = templatePath.concat(e.getType().concat(".jrxml"));

					JasperDesign jasperDesign = JRXmlLoader.load(jasperDesignF);
					// InputStream jasperStream = new FileInputStream(jasperFile);
					JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
					// JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

					String fileOutName = null;
					if ("AD".equals(UserProfileUtils.getChannel())) {
						fileOutName = Optional.ofNullable(UserProfileUtils.getFaceMask()).orElse("").concat("_")
								.concat(Optional.ofNullable(e.getType()).orElse("")+"_"+(i+1));
					} else {
						fileOutName = Optional.ofNullable(eCardReqDto.getAgentName()).orElse("").concat("_")
								.concat(Optional.ofNullable(eCardReqDto.getAgentType()).orElse("")).concat("_")
								.concat(Optional.ofNullable(eCardReqDto.getPhone()).orElse("")).concat("_")
								.concat(Optional.ofNullable(e.getType()).orElse("")+"_"+(i+1));
					}

					JRBeanArrayDataSource beanArrDataSource = new JRBeanArrayDataSource(new ECardPdfDto[] { e });
					JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanArrDataSource);

					// write file pdf
					String pdfDesFile = Paths.get(pathOut, fileOutName).toString().concat(".pdf");
					pdfDesFile = FileUtil.deAccent(pdfDesFile);
					pdfDesFile = StringUtils.replace(pdfDesFile, " ", "");
					// Xuất ra file
					File dir = new File(pathOut);
					if (!dir.exists()) {
						dir.mkdirs();
					}

					File file = new File(pdfDesFile);
					if (!file.exists()) {
						JasperExportManager.exportReportToPdfFile(jasperPrint, pdfDesFile);
					} else if (file.delete()) {
						JasperExportManager.exportReportToPdfFile(jasperPrint, pdfDesFile);
					}

					// jasperStream.close();
					// covert pdf-> image
					String imageDesFile = Paths.get(pathOut, fileOutName).toString().concat("_image.png");
					imageDesFile = FileUtil.deAccent(imageDesFile); 
					imageDesFile = StringUtils.replace(imageDesFile, " ", "");
					convertPdftoImage(pdfDesFile, imageDesFile);
					//return data
					ECardResDto res = new ECardResDto();
					res.setId(String.valueOf(i));
					res.setName(e.getTitle());
					res.setSort(String.valueOf(e.getSort()));
					String returnPath = folderECard + "//" + fileOutName;

					res.setUrlPdf(StringUtils.replace(FileUtil.deAccent(returnPath).concat(".pdf"), " ", ""));
					res.setUrlImage(StringUtils.replace(FileUtil.deAccent(returnPath).concat("_image.png"), " ", ""));
					datas.add(res);
					i++;
				} catch (Exception ex) {
				    logger.error("###geranateECard###: ", ex);
				}

			}
		}
		return datas;
	}

	@SuppressWarnings("unused")
	private void convertPdftoImage(String inputFile, String outputFile) {
		try {
			PDDocument pdDocument = new PDDocument();
			PDDocument oDocument = PDDocument.load(new File(inputFile));
			PDFRenderer pdfRenderer = new PDFRenderer(oDocument);
			int numberOfPages = oDocument.getNumberOfPages();
			PDPage page = null;

			for (int i = 0; i < numberOfPages; i++) {
				File outPutFile = new File(outputFile);
				BufferedImage bImage = pdfRenderer.renderImageWithDPI(i, 180, ImageType.RGB);
				ImageIO.write(bImage, "png", outPutFile);
			}
			oDocument.close();
			pdDocument.close();

		} catch (IOException e) {
			logger.error("Exception ", e);
		}
	}

	
    private String setIconSuitable(String content, String templatePath, String type ) {
	    try {
	        if(content != null) {
	            String icon="";
	            if(content.startsWith("Facebook")) { icon="facebook.png";}
	            else if(content.startsWith("Zalo")) {icon="zalo.png"; }
	            else if(content.startsWith("Email")) {  icon="mail.png";}
	            else if(content.startsWith("Phone")) { icon="call.png"; }

				// set icon background color red
	            else if(content.startsWith("SAMURAI") && type.equals("e-card-tempalte-1")) {icon="LAB_SamuraiMDRT_RED.png";}
	            else if(content.startsWith("TOT") && type.equals("e-card-tempalte-1")) {icon="LAB_TOTMDRT_RED.png"; }
	            else if(content.startsWith("COT") && type.equals("e-card-tempalte-1")) {icon="LAB_COT_RED.png";}
	            else if(content.startsWith("MDRTUUTU") && type.equals("e-card-tempalte-1")) {icon="LAB_MDRT_RED.png"; }
	            else if(content.startsWith("MDRT") && type.equals("e-card-tempalte-1")) { icon="LAB_MDRT_RED.png";}
	            else if(content.startsWith("STARTRIP5S")&& type.equals("e-card-tempalte-1")) {icon="LAB_StarTrip5sMDRT_RED.png";}
	            else if(content.startsWith("STARTRIP3S")&& type.equals("e-card-tempalte-1")) { icon="LAB_StarTrip3sMDRT_RED.png"; }
	            else if(content.startsWith("STARNIGHT")&& type.equals("e-card-tempalte-1")) {icon="LAB_StarNightMDRT_RED.png"; }

				// set icon background color white
				else if((type.equals("e-card-tempalte-2") || type.equals("e-card-tempalte-3")) && content.startsWith("SAMURAI")) {icon="LAB_SamuraiMDRT.png";}
	            else if((type.equals("e-card-tempalte-2") || type.equals("e-card-tempalte-3")) &&content.startsWith("TOT")) {icon="LAB_TOTMDRT.png"; }
	            else if((type.equals("e-card-tempalte-2") || type.equals("e-card-tempalte-3")) &&content.startsWith("COT")) {icon="LAB_COT.png";}
	            else if((type.equals("e-card-tempalte-2") || type.equals("e-card-tempalte-3")) &&content.startsWith("MDRTUUTU")) {icon="LAB_MDRT.png"; }
	            else if((type.equals("e-card-tempalte-2") || type.equals("e-card-tempalte-3")) &&content.startsWith("MDRT")) { icon="LAB_MDRT.png";}
	            else if((type.equals("e-card-tempalte-2") || type.equals("e-card-tempalte-3")) &&content.startsWith("STARTRIP5S")) {icon="LAB_StarTrip5sMDRT.png";}
	            else if((type.equals("e-card-tempalte-2") || type.equals("e-card-tempalte-3")) &&content.startsWith("STARTRIP3S")) { icon="LAB_StarTrip3sMDRT.png"; }
	            else if((type.equals("e-card-tempalte-2") || type.equals("e-card-tempalte-3")) &&content.startsWith("STARNIGHT")) {icon="LAB_StarNightMDRT.png"; }

	            if(StringUtils.isNotEmpty(icon)) {
	                String path = getFileAutoRotate(templatePath, icon);
	                return path;
	            }
	            
	            
	            
	        }
	    }
	    catch (Exception e) {
            logger.error("###setIconSuitable###: "+e);
        }
        return null;
	}	
	
    public static String getFileAutoRotate(String subFolder, String pathFile) {
        String result = "";

        String path = pathFile;
        if (StringUtils.isNotBlank(subFolder)) {
            path = subFolder + "/" + pathFile;
        }

        try {
            if (StringUtils.isNotBlank(path)) {
                File file = new File(path);
                if (file.exists()) {
                    result = pathFile;
                    //create rotate file
                    String pathFileOut = pathFile.replace(".jpeg", "") + "_rotate" + ".jpeg";
                    String pathOut = subFolder + "/" + pathFileOut;
                    BufferedImage originalImage = ImageIO.read(file);
                    originalImage.getHeight();
                    // Metadata metadata = ImageMetadataReader.readMetadata(file);
                    // Collection<ExifIFD0Directory> exifIFD0DirectoryList = metadata.getDirectoriesOfType(ExifIFD0Directory.class);
                    // Collection<JpegDirectory> jpegDirectoryList = metadata.getDirectoriesOfType(JpegDirectory.class);
                    AffineTransformOp affineTransformOp = null;
                    int orientation = 1;
                    if (orientation == 0 || orientation == 1) {
                        result = pathFile;
                    } else {
                        BufferedImage destinationImage = new BufferedImage(originalImage.getHeight(),
                                originalImage.getWidth(), originalImage.getType());
                        if (affineTransformOp != null) {
                            destinationImage = affineTransformOp.filter(originalImage, destinationImage);
                        }
                        ImageIO.write(destinationImage, "jpeg", new File(pathOut));
                        result = pathFileOut;
                    }
                }
            }
        } catch (Exception e) {
        }

        return result;
    }
}
