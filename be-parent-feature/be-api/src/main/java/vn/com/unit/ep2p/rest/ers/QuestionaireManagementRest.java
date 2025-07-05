// 2021-04-12 LocLT Task #41067

package vn.com.unit.ep2p.rest.ers;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vn.com.unit.common.utils.CommonStringUtil;
import vn.com.unit.core.res.ObjectDataRes;
import vn.com.unit.dts.web.rest.common.DtsApiResponse;
import vn.com.unit.ep2p.constant.ErsApiConstant;
import vn.com.unit.ep2p.core.constant.AppApiConstant;
import vn.com.unit.ep2p.core.ers.dto.QuestionaireManagementDto;
import vn.com.unit.ep2p.core.ers.dto.QuestionaireManagementSearchDto;
import vn.com.unit.ep2p.core.ers.service.QuestionaireManagementService;
import vn.com.unit.ep2p.enumdef.QuestionaireManagementExportEnum;
import vn.com.unit.ep2p.rest.AbstractRest;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import springfox.documentation.annotations.ApiIgnore;
//import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.imp.excel.dto.ItemColsExcelDto;
import vn.com.unit.imp.excel.utils.ExportExcelUtil;
import vn.com.unit.imp.excel.utils.ImportExcelUtil;

@Api(tags = { ErsApiConstant.QUESTIONAIRE_MANAGEMENT_TAG })
@RestController
@RequestMapping(AppApiConstant.API_V1 + ErsApiConstant.QUESTIONAIRE_MANAGEMENT)
public class QuestionaireManagementRest extends AbstractRest {

	@Autowired
	QuestionaireManagementService questionaireManagementService;

	@Autowired
	ServletContext servletContext;

	// list/detail
	private boolean hasRoleList() {
		// TODO return false if no has role
		return true;
	}

	// create/update
	private boolean hasRoleEdit() {
		// TODO return false if no has role
		return hasRoleList() && true;
	}

	@GetMapping("")
	public DtsApiResponse list(QuestionaireManagementSearchDto searchDto,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
//		if (!hasRoleList()) {
//			return this.errorHandler.handlerException(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.toString());
//		}

		long start = System.currentTimeMillis();

		try {
			searchDto.setDeletedFlag(0);

			List<QuestionaireManagementDto> datas = new ArrayList<>();
			int count = questionaireManagementService.countByCondition(searchDto);
			if (count > 0) {
				datas = questionaireManagementService.searchByCondition(searchDto, page, size);
			}
			ObjectDataRes<QuestionaireManagementDto> resObj = new ObjectDataRes<>(count, datas);
			return this.successHandler.handlerSuccess(resObj, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

	@ApiOperation("get table header")
	@GetMapping("/table-header")
	public DtsApiResponse tableHeader(@RequestParam(defaultValue = "en") String language) {
//		if (!hasRoleList()) {
//			return this.errorHandler.handlerException(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.toString());
//		}

		long start = System.currentTimeMillis();
		try {
			return this.successHandler.handlerSuccess(getTableHeader(QuestionaireManagementDto.class, language), start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

	@GetMapping("/{id}")
	public DtsApiResponse detail(@PathVariable Long id) {
//		if (!hasRoleList()) {
//			return this.errorHandler.handlerException(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.toString());
//		}

		long start = System.currentTimeMillis();

		try {
			QuestionaireManagementDto resObj = questionaireManagementService.findById(id);
			return this.successHandler.handlerSuccess(resObj, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

	@PostMapping("/")
	public DtsApiResponse create(@RequestBody QuestionaireManagementDto dto) {
//		if (!hasRoleEdit()) {
//			return this.errorHandler.handlerException(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.toString());
//		}

		long start = System.currentTimeMillis();
		try {
			dto.init();
			
			if (CommonStringUtil.isBlank(dto.getApplyForPosition())) {
				dto.setApplyForPosition("");
			}
			QuestionaireManagementDto resObj = questionaireManagementService.save(dto);
			return this.successHandler.handlerSuccess(resObj, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

	@ApiOperation("Edit questionaire")
	@PutMapping("/{id}")
	public DtsApiResponse update(@RequestBody QuestionaireManagementDto dto, @PathVariable Long id) {
//		if (!hasRoleEdit()) {
//			return this.errorHandler.handlerException(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.toString());
//		}

		long start = System.currentTimeMillis();

		try {
			dto.setId(id);
			
			if (CommonStringUtil.isBlank(dto.getApplyForPosition())) {
				dto.setApplyForPosition("");
			}
			questionaireManagementService.save(dto);
			return this.successHandler.handlerSuccess(null, start, String.valueOf(id), null);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start, String.valueOf(id), null);
		}
	}

	@DeleteMapping("/{id}")
	public DtsApiResponse delete(@PathVariable Long id) {
//		if (!hasRoleEdit()) {
//			return this.errorHandler.handlerException(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.toString());
//		}

		long start = System.currentTimeMillis();

		try {
			questionaireManagementService.delele(id);
			return this.successHandler.handlerSuccess(null, start);
		} catch (Exception ex) {
			return this.errorHandler.handlerException(ex, start);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/export")
	public ResponseEntity<Resource> export(QuestionaireManagementSearchDto searchDto) throws Exception {
//		if (!hasRoleList()) {
//			return this.errorHandler.handlerException(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.toString());
//		}

		searchDto.setDeletedFlag(0);

		List<QuestionaireManagementDto> data = new ArrayList<>();

		int total = questionaireManagementService.countByCondition(searchDto);

		if (total > 0) {
			data = questionaireManagementService.searchByCondition(searchDto, null, null);
		}

		String datePattern = "dd/MM/yyyy";
		String template = "QUESTIONAIRE_MANAGEMENT_EXPORT.xlsx";
		String templatePath = servletContext.getRealPath("/WEB-INF/excel_template" + "/" + template);
		String startRow = "A5";
		ResponseEntity res = null;

		try {

			List<ItemColsExcelDto> cols = new ArrayList<>();
			ImportExcelUtil.setListColumnExcel(QuestionaireManagementExportEnum.class, cols);
			ExportExcelUtil exportExcel = new ExportExcelUtil<>();
			Map<String, String> mapColFormat = null; // setMapColFormat();
			Map<String, Object> setMapColDefaultValue = null; // setMapColDefaultValue();

			Map<String, CellStyle> mapColStyle = null; // setMapColStyle(xssfWorkbook);

			// try (XSSFWorkbook xssfWorkbook = exportExcel.getXSSFWorkbookRest(template);)
			File file = new File(templatePath);
			FileInputStream fileInputStream = new FileInputStream(file);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);

			res = exportExcel.doExportExcelHeaderWithColFormatRest(xssfWorkbook, 0, null, data,
					QuestionaireManagementDto.class, cols, datePattern, startRow, mapColFormat, mapColStyle,
					setMapColDefaultValue, null, true, template, true);
		} catch (Exception e) {
			throw e;
		}
		return res;
	}

}
