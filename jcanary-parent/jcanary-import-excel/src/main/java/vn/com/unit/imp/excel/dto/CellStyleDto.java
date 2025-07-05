/*******************************************************************************
 * Class        ：CellStyleDto
 * Created date ：2020/04/06
 * Lasted date  ：2020/04/06
 * Author       ：HaND
 * Change log   ：2020/04/06：01-00 HaND create a new
 ******************************************************************************/
package vn.com.unit.imp.excel.dto;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import vn.com.unit.imp.excel.utils.ExportExcelUtil;

/**
 * CellStyleDto
 * 
 * @version 01-00
 * @since 01-00
 * @author HaND
 */
public class CellStyleDto {
    
    private String datePattern;
    
    private CellStyle cellStyleTitle1;

    private CellStyle cellStyleTitle2;

    private CellStyle cellStyleTitle3;

    private CellStyle cellStyleTitle4;

    private CellStyle cellStyleTitle5;
    
    private CellStyle cellStyleCenter;
    
    private CellStyle cellStyleRight;
    
    private CellStyle cellStyleLeft;
    
    private CellStyle cellStyleDateCenter;
    
    private CellStyle cellStyleRightDouble1;
    
    private CellStyle cellStyleRightDouble2;
    
    private CellStyle cellStyleRightDouble3;
    
    private CellStyle cellStyleRightDouble1WithPercent;
    
    private CellStyle cellStyleRightDouble2WithPercent;
    
    private CellStyle cellStyleRightBigDecimal1;
    
    private CellStyle cellStyleRightBigDecimal2;
    
    private CellStyle cellStyleRightBigDecimal3;

    
    private CellStyle cellStyleFinanceFormat;
    
    private CellStyle cellStyleBoder;
    
    private CellStyle cellStyleMessageError;
    
    private CellStyle cellStyleMessageWarning;
    
    private CellStyle cellStyleDescription;
    
    private SXSSFWorkbook sxssfWorkbook;
    
    private XSSFColor xssfColor;
    
    private int isFormatFinance;
    
    private boolean isAllBorder;
    
    private String  fontName;
    
    public CellStyleDto() {}

    public CellStyleDto(SXSSFWorkbook sxssfWorkbook, String fontName, boolean isAllBorder, int isFormatFinancet, String datePattern) {
        this.sxssfWorkbook = sxssfWorkbook;
        
        this.datePattern = datePattern;
        
        // createHelper
        CreationHelper createHelper = sxssfWorkbook.getCreationHelper();
        // dataFormat
        DataFormat dataFormat = createHelper.createDataFormat();
        
        Font cellFont = sxssfWorkbook.createFont();
        cellFont.setFontName(fontName);
        
        this.fontName = fontName;
        
        // style date-center number-right text-left
        this.cellStyleCenter = sxssfWorkbook.createCellStyle();
        this.cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
        this.cellStyleCenter.setWrapText(false);
        this.cellStyleCenter.setFont(cellFont);

        this.cellStyleRight = sxssfWorkbook.createCellStyle();
        this.cellStyleRight.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRight.setWrapText(false);
        this.cellStyleRight.setFont(cellFont);

        this.cellStyleLeft = sxssfWorkbook.createCellStyle();
        this.cellStyleLeft.setAlignment(HorizontalAlignment.LEFT);
        this.cellStyleLeft.setWrapText(false);
        this.cellStyleLeft.setFont(cellFont);

        // cellStyleDateCenter
        this.cellStyleDateCenter = sxssfWorkbook.createCellStyle();
        this.cellStyleDateCenter.setAlignment(HorizontalAlignment.CENTER);
        // See class org.apache.poi.ss.usermodel.BuiltinFormats for more details about format
        this.cellStyleDateCenter.setFont(cellFont);
        this.cellStyleDateCenter.setDataFormat(dataFormat.getFormat(datePattern));

        // cellStyleRight for Double "0.00"
        this.cellStyleRightDouble1 = sxssfWorkbook.createCellStyle();
        this.cellStyleRightDouble1.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightDouble1.setFont(cellFont);
        this.cellStyleRightDouble1.setDataFormat(dataFormat.getFormat("0.00"));

        // cellStyleRight for Double "0"
        this.cellStyleRightDouble2 = sxssfWorkbook.createCellStyle();
        this.cellStyleRightDouble2.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightDouble2.setFont(cellFont);
        this.cellStyleRightDouble2.setDataFormat(dataFormat.getFormat("0"));
        
        // cellStyleRight for Double "0.0#################"
        this.cellStyleRightDouble3 = sxssfWorkbook.createCellStyle();
        this.cellStyleRightDouble3.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightDouble3.setFont(cellFont);
        this.cellStyleRightDouble3.setDataFormat(dataFormat.getFormat("0.0#################"));

        // cellStyleRight for Double "0.0#################%"
        this.cellStyleRightDouble1WithPercent = sxssfWorkbook.createCellStyle();
        this.cellStyleRightDouble1WithPercent.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightDouble1WithPercent.setFont(cellFont);
        this.cellStyleRightDouble1WithPercent.setDataFormat(dataFormat.getFormat("0.0#################%"));

        // cellStyleRight for Double "0%"
        this.cellStyleRightDouble2WithPercent = sxssfWorkbook.createCellStyle();
        this.cellStyleRightDouble2WithPercent.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightDouble2WithPercent.setFont(cellFont);
        this.cellStyleRightDouble2WithPercent.setDataFormat(dataFormat.getFormat("0%"));

        // cellStyleRight for BigDecimal "#,##0.00"
        this.cellStyleRightBigDecimal1 = sxssfWorkbook.createCellStyle();
        this.cellStyleRightBigDecimal1.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightBigDecimal1.setFont(cellFont);
        this.cellStyleRightBigDecimal1.setDataFormat(dataFormat.getFormat("#,##0.00"));

        // cellStyleRight for BigDecimal "#,##0"
        this.cellStyleRightBigDecimal2 = sxssfWorkbook.createCellStyle();
        this.cellStyleRightBigDecimal2.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightBigDecimal2.setFont(cellFont);
        this.cellStyleRightBigDecimal2.setDataFormat(dataFormat.getFormat("#,##0"));

        // cellStyle for BigDecimal with Finance money format
        this.cellStyleFinanceFormat = sxssfWorkbook.createCellStyle();
        this.cellStyleFinanceFormat.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleFinanceFormat.setFont(cellFont);
        this.cellStyleFinanceFormat.setDataFormat(dataFormat.getFormat("_(* #,##0_);_(* (#,##0);_(* \"-\"??_);_(@_)"));
        
        this.cellStyleBoder = sxssfWorkbook.createCellStyle();
        
        // isAllBorder is true
        if (isAllBorder) {
            ExportExcelUtil.setAllBorder(this.cellStyleCenter);
            ExportExcelUtil.setAllBorder(this.cellStyleRight);
            ExportExcelUtil.setAllBorder(this.cellStyleLeft);
            ExportExcelUtil.setAllBorder(this.cellStyleDateCenter);
            ExportExcelUtil.setAllBorder(this.cellStyleRightDouble1);
            ExportExcelUtil.setAllBorder(this.cellStyleRightDouble2);
            ExportExcelUtil.setAllBorder(this.cellStyleRightDouble3);
            ExportExcelUtil.setAllBorder(this.cellStyleRightDouble1WithPercent);
            ExportExcelUtil.setAllBorder(this.cellStyleRightDouble2WithPercent);
            ExportExcelUtil.setAllBorder(this.cellStyleRightBigDecimal1);
            ExportExcelUtil.setAllBorder(this.cellStyleRightBigDecimal2);
            ExportExcelUtil.setAllBorder(this.cellStyleFinanceFormat);
            ExportExcelUtil.setAllBorder(this.cellStyleBoder);         

        }
    }
    
    public CellStyleDto(CellStyleDto dto, XSSFColor colorToTal) {
        
        this.xssfColor = colorToTal;
        
        this.datePattern = dto.getDatePattern();
        
        Font cellFont = dto.getSxssfWorkbook().createFont();
        cellFont.setFontName(dto.getFontName());
        cellFont.setBold(true);
        
        this.cellStyleCenter = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleCenter.cloneStyleFrom(dto.getCellStyleCenter());
        ((XSSFCellStyle) this.cellStyleCenter).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleCenter).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleCenter.setFont(cellFont);
        

        this.cellStyleRight = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleRight.cloneStyleFrom(dto.getCellStyleRight());
        ((XSSFCellStyle) this.cellStyleRight).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleRight).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleRight.setFont(cellFont);

        this.cellStyleLeft = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleLeft.cloneStyleFrom(dto.getCellStyleLeft());
        ((XSSFCellStyle) this.cellStyleLeft).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleLeft).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleLeft.setFont(cellFont);

        this.cellStyleDateCenter = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleDateCenter.cloneStyleFrom(dto.getCellStyleDateCenter());
        ((XSSFCellStyle) this.cellStyleDateCenter).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleDateCenter).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleDateCenter.setFont(cellFont);

        this.cellStyleRightDouble1 = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleRightDouble1.cloneStyleFrom(dto.getCellStyleRightDouble1());
        ((XSSFCellStyle) this.cellStyleRightDouble1).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleRightDouble1).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleRightDouble1.setFont(cellFont);

        this.cellStyleRightDouble2 = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleRightDouble2.cloneStyleFrom(dto.getCellStyleRightDouble2());
        ((XSSFCellStyle) this.cellStyleRightDouble2).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleRightDouble2).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleRightDouble2.setFont(cellFont);
        
        this.cellStyleRightDouble3 = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleRightDouble3.cloneStyleFrom(dto.getCellStyleRightDouble2());
        ((XSSFCellStyle) this.cellStyleRightDouble3).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleRightDouble3).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleRightDouble3.setFont(cellFont);

        this.cellStyleRightDouble1WithPercent = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleRightDouble1WithPercent.cloneStyleFrom(dto.getCellStyleRightDouble1WithPercent());
        ((XSSFCellStyle) this.cellStyleRightDouble1WithPercent).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleRightDouble1WithPercent).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleRightDouble1WithPercent.setFont(cellFont);

        this.cellStyleRightDouble2WithPercent = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleRightDouble2WithPercent.cloneStyleFrom(dto.getCellStyleRightDouble2WithPercent());
        ((XSSFCellStyle) this.cellStyleRightDouble2WithPercent).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleRightDouble2WithPercent).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleRightDouble2WithPercent.setFont(cellFont);

        this.cellStyleRightBigDecimal1 = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleRightBigDecimal1.cloneStyleFrom(dto.getCellStyleRightBigDecimal1());
        ((XSSFCellStyle) this.cellStyleRightBigDecimal1).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleRightBigDecimal1).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleRightBigDecimal1.setFont(cellFont);

        this.cellStyleRightBigDecimal2 = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleRightBigDecimal2.cloneStyleFrom(dto.getCellStyleRightBigDecimal2());
        ((XSSFCellStyle) this.cellStyleRightBigDecimal2).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleRightBigDecimal2).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleRightBigDecimal2.setFont(cellFont);

        this.cellStyleFinanceFormat = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleFinanceFormat.cloneStyleFrom(dto.getCellStyleFinanceFormat());
        ((XSSFCellStyle) this.cellStyleFinanceFormat).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleFinanceFormat).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleFinanceFormat.setFont(cellFont);

        this.cellStyleBoder = dto.getSxssfWorkbook().createCellStyle();
        this.cellStyleBoder.cloneStyleFrom(dto.getCellStyleBoder());
        ((XSSFCellStyle) this.cellStyleBoder).setFillForegroundColor(colorToTal);
        ((XSSFCellStyle) this.cellStyleBoder).setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleBoder.setFont(cellFont);
    }
    
    public CellStyleDto(XSSFWorkbook xssfWorkbook, String fontName, boolean isAllBorder, String datePattern) {
    	
        this.sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook, 1000);
        
        this.datePattern = datePattern;
        
        // createHelper
        CreationHelper createHelper = xssfWorkbook.getCreationHelper();
        // dataFormat
        DataFormat dataFormat = createHelper.createDataFormat();
        
        Font cellFont = xssfWorkbook.createFont();
        cellFont.setFontName(fontName);
        
        this.fontName = fontName;

        //style title
        Font cellFontTitle = sxssfWorkbook.createFont();
        cellFontTitle.setFontName(fontName);
        cellFontTitle.setBold(true);
        cellFontTitle.setFontHeightInPoints((short) 14);

        this.cellStyleTitle1 = sxssfWorkbook.createCellStyle();
        this.cellStyleTitle1.setAlignment(HorizontalAlignment.LEFT);
        this.cellStyleTitle1.setBorderTop(BorderStyle.THIN);
        this.cellStyleTitle1.setWrapText(false);
        this.cellStyleTitle1.setFont(cellFontTitle);

        this.cellStyleTitle2 = sxssfWorkbook.createCellStyle();
        this.cellStyleTitle2.setFont(cellFont);
        
        Font cellFont3 = sxssfWorkbook.createFont();
        cellFont3.setFontName(fontName);
        cellFont3.setBold(true);
        cellFont3.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        cellFont3.setFontHeightInPoints((short) 14);

        this.cellStyleTitle3 = sxssfWorkbook.createCellStyle();
        this.cellStyleTitle3.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleTitle3.setBorderTop(BorderStyle.THIN);
        this.cellStyleTitle3.setWrapText(false);
        this.cellStyleTitle3.setDataFormat(dataFormat.getFormat("#,##0"));
        this.cellStyleTitle3.setFont(cellFont3);
        
        
        Font cellFont4 = sxssfWorkbook.createFont();
        cellFont4.setFontName(fontName);
        cellFont4.setBold(true);
        cellFont4.setFontHeightInPoints((short) 14);

        this.cellStyleTitle4 = sxssfWorkbook.createCellStyle();
        this.cellStyleTitle4.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleTitle4.setWrapText(false);
        this.cellStyleTitle4.setDataFormat(dataFormat.getFormat("#,##0"));
        this.cellStyleTitle4.setFont(cellFont4);
        
        Font cellFont5 = sxssfWorkbook.createFont();
        cellFont5.setFontName(fontName);
        cellFont5.setBold(true);
        cellFont5.setFontHeightInPoints((short) 14);

        this.cellStyleTitle5 = sxssfWorkbook.createCellStyle();
        this.cellStyleTitle5.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleTitle5.setBorderTop(BorderStyle.THIN);
        this.cellStyleTitle5.setWrapText(false);
        this.cellStyleTitle5.setDataFormat(dataFormat.getFormat("#,##0"));
        this.cellStyleTitle5.setFont(cellFont5);
        
        //end
        
        // style date-center number-right text-left
        this.cellStyleCenter = xssfWorkbook.createCellStyle();
        this.cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
        this.cellStyleCenter.setWrapText(false);
        this.cellStyleCenter.setFont(cellFont);
        this.cellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);

        this.cellStyleRight = xssfWorkbook.createCellStyle();
        this.cellStyleRight.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRight.setWrapText(false);
        this.cellStyleRight.setFont(cellFont);
        this.cellStyleRight.setVerticalAlignment(VerticalAlignment.CENTER);

        this.cellStyleLeft = xssfWorkbook.createCellStyle();
        this.cellStyleLeft.setAlignment(HorizontalAlignment.LEFT);
        this.cellStyleLeft.setWrapText(false);
        this.cellStyleLeft.setFont(cellFont);
        this.cellStyleLeft.setVerticalAlignment(VerticalAlignment.CENTER);

        // cellStyleDateCenter
        this.cellStyleDateCenter = xssfWorkbook.createCellStyle();
        this.cellStyleDateCenter.setAlignment(HorizontalAlignment.CENTER);
        // See class org.apache.poi.ss.usermodel.BuiltinFormats for more details about format
        this.cellStyleDateCenter.setFont(cellFont);
        this.cellStyleDateCenter.setDataFormat(dataFormat.getFormat(datePattern));
        this.cellStyleDateCenter.setVerticalAlignment(VerticalAlignment.CENTER);

        // cellStyleRight for Double "0.00"
        this.cellStyleRightDouble1 = xssfWorkbook.createCellStyle();
        this.cellStyleRightDouble1.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightDouble1.setFont(cellFont);
        this.cellStyleRightDouble1.setDataFormat(dataFormat.getFormat("0.00"));
        this.cellStyleRightDouble1.setVerticalAlignment(VerticalAlignment.CENTER);

        // cellStyleRight for Double "0"
        this.cellStyleRightDouble2 = xssfWorkbook.createCellStyle();
        this.cellStyleRightDouble2.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightDouble2.setFont(cellFont);
        this.cellStyleRightDouble2.setDataFormat(dataFormat.getFormat("0"));
        this.cellStyleRightDouble2.setVerticalAlignment(VerticalAlignment.CENTER);
        
        // cellStyleRight for Double "0.0#################"
        this.cellStyleRightDouble3 = xssfWorkbook.createCellStyle();
        this.cellStyleRightDouble3.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightDouble3.setFont(cellFont);
        this.cellStyleRightDouble3.setDataFormat(dataFormat.getFormat("0.0#################"));
        this.cellStyleRightDouble3.setVerticalAlignment(VerticalAlignment.CENTER);

        // cellStyleRight for Double "0.0#################%"
        this.cellStyleRightDouble1WithPercent = xssfWorkbook.createCellStyle();
        this.cellStyleRightDouble1WithPercent.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightDouble1WithPercent.setFont(cellFont);
        this.cellStyleRightDouble1WithPercent.setDataFormat(dataFormat.getFormat("0.0#################%"));
        this.cellStyleRightDouble1WithPercent.setVerticalAlignment(VerticalAlignment.CENTER);

        // cellStyleRight for Double "0%"
        this.cellStyleRightDouble2WithPercent = xssfWorkbook.createCellStyle();
        this.cellStyleRightDouble2WithPercent.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightDouble2WithPercent.setFont(cellFont);
        this.cellStyleRightDouble2WithPercent.setDataFormat(dataFormat.getFormat("0%"));
        this.cellStyleRightDouble2WithPercent.setVerticalAlignment(VerticalAlignment.CENTER);

        // cellStyleRight for BigDecimal "$#,##0.00"
        this.cellStyleRightBigDecimal3 = xssfWorkbook.createCellStyle();
        this.cellStyleRightBigDecimal3.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightBigDecimal3.setFont(cellFont);
        this.cellStyleRightBigDecimal3.setDataFormat(dataFormat.getFormat("_(* #,##0_);_(* (#,##0);_(* \"-\"_);_(@_)"));
        this.cellStyleRightBigDecimal3.setVerticalAlignment(VerticalAlignment.CENTER);
        
        // cellStyleRight for BigDecimal "#,##0.00"
        this.cellStyleRightBigDecimal1 = xssfWorkbook.createCellStyle();
        this.cellStyleRightBigDecimal1.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightBigDecimal1.setFont(cellFont);
        this.cellStyleRightBigDecimal1.setDataFormat(dataFormat.getFormat("#,##0.00"));
        this.cellStyleRightBigDecimal1.setVerticalAlignment(VerticalAlignment.CENTER);

        // cellStyleRight for BigDecimal "#,##0"
        this.cellStyleRightBigDecimal2 = xssfWorkbook.createCellStyle();
        this.cellStyleRightBigDecimal2.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleRightBigDecimal2.setFont(cellFont);
        this.cellStyleRightBigDecimal2.setDataFormat(dataFormat.getFormat("#,##0"));
        this.cellStyleRightBigDecimal2.setVerticalAlignment(VerticalAlignment.CENTER);

        // cellStyle for BigDecimal with Finance money format
        this.cellStyleFinanceFormat = xssfWorkbook.createCellStyle();
        this.cellStyleFinanceFormat.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyleFinanceFormat.setFont(cellFont);
        this.cellStyleFinanceFormat.setDataFormat(dataFormat.getFormat("_(* #,##0_);_(* (#,##0);_(* \"-\"??_);_(@_)"));
        this.cellStyleFinanceFormat.setVerticalAlignment(VerticalAlignment.CENTER);
        
        this.cellStyleBoder = xssfWorkbook.createCellStyle();
        
        this.cellStyleMessageError = xssfWorkbook.createCellStyle();
        this.cellStyleMessageError.setAlignment(HorizontalAlignment.LEFT);
        this.cellStyleMessageError.setVerticalAlignment(VerticalAlignment.CENTER);
        
        Font fontError = xssfWorkbook.createFont();
        fontError.setColor(IndexedColors.RED.getIndex());
        this.cellStyleMessageError.setFont(fontError);
        
        this.cellStyleMessageWarning = xssfWorkbook.createCellStyle();
        this.cellStyleMessageWarning.setAlignment(HorizontalAlignment.LEFT);
        this.cellStyleMessageWarning.setVerticalAlignment(VerticalAlignment.CENTER);
        
        Font fontWarning = xssfWorkbook.createFont();
        fontWarning.setColor(IndexedColors.ORANGE.getIndex());
        this.cellStyleMessageWarning.setFont(fontWarning);
        
        this.cellStyleDescription = xssfWorkbook.createCellStyle();
        this.cellStyleDescription.setAlignment(HorizontalAlignment.LEFT);
        this.cellStyleDescription.setWrapText(true);
        this.cellStyleDescription.setFont(cellFont);
        this.cellStyleDescription.setVerticalAlignment(VerticalAlignment.CENTER);
        
        // isAllBorder is true
        if (isAllBorder) {
            ExportExcelUtil.setAllBorder(this.cellStyleCenter);
            ExportExcelUtil.setAllBorder(this.cellStyleRight);
            ExportExcelUtil.setAllBorder(this.cellStyleLeft);
            ExportExcelUtil.setAllBorder(this.cellStyleDateCenter);
            ExportExcelUtil.setAllBorder(this.cellStyleRightDouble1);
            ExportExcelUtil.setAllBorder(this.cellStyleRightDouble2);
            ExportExcelUtil.setAllBorder(this.cellStyleRightDouble3);
            ExportExcelUtil.setAllBorder(this.cellStyleRightDouble1WithPercent);
            ExportExcelUtil.setAllBorder(this.cellStyleRightDouble2WithPercent);
            ExportExcelUtil.setAllBorder(this.cellStyleRightBigDecimal1);
            ExportExcelUtil.setAllBorder(this.cellStyleRightBigDecimal2);
            ExportExcelUtil.setAllBorder(this.cellStyleRightBigDecimal3);
            ExportExcelUtil.setAllBorder(this.cellStyleFinanceFormat);
            ExportExcelUtil.setAllBorder(this.cellStyleBoder);
            ExportExcelUtil.setAllBorder(this.cellStyleMessageError);
            ExportExcelUtil.setAllBorder(this.cellStyleMessageWarning);
            ExportExcelUtil.setAllBorder(this.cellStyleDescription);
            
            ExportExcelUtil.setAllBorder(this.cellStyleTitle1);
            ExportExcelUtil.setAllBorder(this.cellStyleTitle2);
            ExportExcelUtil.setAllBorder(this.cellStyleTitle3);
            ExportExcelUtil.setAllBorder(this.cellStyleTitle4);
            ExportExcelUtil.setAllBorder(this.cellStyleTitle5);

        }
    }

    /**
     * Get cellStyleCenter
     * @return CellStyle
     * @author HaND
     */
    public CellStyle getCellStyleCenter() {
        return cellStyleCenter;
    }

    /**
     * Set cellStyleCenter
     * @param   cellStyleCenter
     *          type CellStyle
     * @return
     * @author  HaND
     */
    public void setCellStyleCenter(CellStyle cellStyleCenter) {
        
        this.cellStyleCenter = cellStyleCenter;
    }

    /**
     * Get cellStyleRight
     * @return CellStyle
     * @author HaND
     */
    public CellStyle getCellStyleRight() {
        return cellStyleRight;
    }

    /**
     * Set cellStyleRight
     * @param   cellStyleRight
     *          type CellStyle
     * @return
     * @author  HaND
     */
    public void setCellStyleRight(CellStyle cellStyleRight) {
        this.cellStyleRight = cellStyleRight;
    }

    /**
     * Get cellStyleLeft
     * @return CellStyle
     * @author HaND
     */
    public CellStyle getCellStyleLeft() {
        return cellStyleLeft;
    }

    public CellStyle getCellStyleTitle1() {
		return cellStyleTitle1;
	}

	public void setCellStyleTitle1(CellStyle cellStyleTitle1) {
		this.cellStyleTitle1 = cellStyleTitle1;
	}

	public CellStyle getCellStyleTitle2() {
		return cellStyleTitle2;
	}

	public void setCellStyleTitle2(CellStyle cellStyleTitle2) {
		this.cellStyleTitle2 = cellStyleTitle2;
	}

	public CellStyle getCellStyleTitle3() {
		return cellStyleTitle3;
	}

	public void setCellStyleTitle3(CellStyle cellStyleTitle3) {
		this.cellStyleTitle3 = cellStyleTitle3;
	}

	public CellStyle getCellStyleTitle4() {
		return cellStyleTitle4;
	}

	public void setCellStyleTitle4(CellStyle cellStyleTitle4) {
		this.cellStyleTitle4 = cellStyleTitle4;
	}

	/**
     * Set cellStyleLeft
     * @param   cellStyleLeft
     *          type CellStyle
     * @return
     * @author  HaND
     */
    public void setCellStyleLeft(CellStyle cellStyleLeft) {
        this.cellStyleLeft = cellStyleLeft;
    }

    /**
     * Get cellStyleDateCenter
     * @return CellStyle
     * @author HaND
     */
    public CellStyle getCellStyleDateCenter() {
        return cellStyleDateCenter;
    }

    /**
     * Set cellStyleDateCenter
     * @param   cellStyleDateCenter
     *          type CellStyle
     * @return
     * @author  HaND
     */
    public void setCellStyleDateCenter(CellStyle cellStyleDateCenter) {
        this.cellStyleDateCenter = cellStyleDateCenter;
    }

    /**
     * Get cellStyleRightDouble1
     * @return CellStyle
     * @author HaND
     */
    public CellStyle getCellStyleRightDouble1() {
        return cellStyleRightDouble1;
    }

    /**
     * Set cellStyleRightDouble1
     * @param   cellStyleRightDouble1
     *          type CellStyle
     * @return
     * @author  HaND
     */
    public void setCellStyleRightDouble1(CellStyle cellStyleRightDouble1) {
        this.cellStyleRightDouble1 = cellStyleRightDouble1;
    }

    /**
     * Get cellStyleRightDouble2
     * @return CellStyle
     * @author HaND
     */
    public CellStyle getCellStyleRightDouble2() {
        return cellStyleRightDouble2;
    }

    /**
     * Set cellStyleRightDouble2
     * @param   cellStyleRightDouble2
     *          type CellStyle
     * @return
     * @author  HaND
     */
    public void setCellStyleRightDouble2(CellStyle cellStyleRightDouble2) {
        this.cellStyleRightDouble2 = cellStyleRightDouble2;
    }

    public CellStyle getCellStyleTitle5() {
		return cellStyleTitle5;
	}

	public void setCellStyleTitle5(CellStyle cellStyleTitle5) {
		this.cellStyleTitle5 = cellStyleTitle5;
	}

	public CellStyle getCellStyleRightDouble3() {
		return cellStyleRightDouble3;
	}

	public void setCellStyleRightDouble3(CellStyle cellStyleRightDouble3) {
		this.cellStyleRightDouble3 = cellStyleRightDouble3;
	}

	/**
     * Get cellStyleRightDouble1WithPercent
     * @return CellStyle
     * @author HaND
     */
    public CellStyle getCellStyleRightDouble1WithPercent() {
        return cellStyleRightDouble1WithPercent;
    }

    /**
     * Set cellStyleRightDouble1WithPercent
     * @param   cellStyleRightDouble1WithPercent
     *          type CellStyle
     * @return
     * @author  HaND
     */
    public void setCellStyleRightDouble1WithPercent(CellStyle cellStyleRightDouble1WithPercent) {
        this.cellStyleRightDouble1WithPercent = cellStyleRightDouble1WithPercent;
    }

    /**
     * Get cellStyleRightDouble2WithPercent
     * @return CellStyle
     * @author HaND
     */
    public CellStyle getCellStyleRightDouble2WithPercent() {
        return cellStyleRightDouble2WithPercent;
    }

    /**
     * Set cellStyleRightDouble2WithPercent
     * @param   cellStyleRightDouble2WithPercent
     *          type CellStyle
     * @return
     * @author  HaND
     */
    public void setCellStyleRightDouble2WithPercent(CellStyle cellStyleRightDouble2WithPercent) {
        this.cellStyleRightDouble2WithPercent = cellStyleRightDouble2WithPercent;
    }

    /**
     * Get cellStyleRightBigDecimal1
     * @return CellStyle
     * @author HaND
     */
    public CellStyle getCellStyleRightBigDecimal1() {
        return cellStyleRightBigDecimal1;
    }

    /**
     * Set cellStyleRightBigDecimal1
     * @param   cellStyleRightBigDecimal1
     *          type CellStyle
     * @return
     * @author  HaND
     */
    public void setCellStyleRightBigDecimal1(CellStyle cellStyleRightBigDecimal1) {
        this.cellStyleRightBigDecimal1 = cellStyleRightBigDecimal1;
    }

    public CellStyle getCellStyleRightBigDecimal3() {
        return cellStyleRightBigDecimal3;
    }

    public void setCellStyleRightBigDecimal3(CellStyle cellStyleRightBigDecimal3) {
        this.cellStyleRightBigDecimal3 = cellStyleRightBigDecimal3;
    }
    
    /**
     * Get cellStyleRightBigDecimal2
     * @return CellStyle
     * @author HaND
     */
    public CellStyle getCellStyleRightBigDecimal2() {
        return cellStyleRightBigDecimal2;
    }

    /**
     * Set cellStyleRightBigDecimal2
     * @param   cellStyleRightBigDecimal2
     *          type CellStyle
     * @return
     * @author  HaND
     */
    public void setCellStyleRightBigDecimal2(CellStyle cellStyleRightBigDecimal2) {
        this.cellStyleRightBigDecimal2 = cellStyleRightBigDecimal2;
    }

    /**
     * Get cellStyleFinanceFormat
     * @return CellStyle
     * @author HaND
     */
    public CellStyle getCellStyleFinanceFormat() {
        return cellStyleFinanceFormat;
    }


    /**
     * Set cellStyleFinanceFormat
     * @param   cellStyleFinanceFormat
     *          type CellStyle
     * @return
     * @author  HaND
     */
    public void setCellStyleFinanceFormat(CellStyle cellStyleFinanceFormat) {
        this.cellStyleFinanceFormat = cellStyleFinanceFormat;
    }

    /**
     * Get cellStyleBoder
     * @return CellStyle
     * @author HaND
     */
    public CellStyle getCellStyleBoder() {
        return cellStyleBoder;
    }

    /**
     * Set cellStyleBoder
     * @param   cellStyleBoder
     *          type CellStyle
     * @return
     * @author  HaND
     */
    public void setCellStyleBoder(CellStyle cellStyleBoder) {
        this.cellStyleBoder = cellStyleBoder;
    }

    /**
     * Get datePattern
     * @return String
     * @author HaND
     */
    public String getDatePattern() {
        return datePattern;
    }

    /**
     * Set datePattern
     * @param   datePattern
     *          type String
     * @return
     * @author  HaND
     */
    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    /**
     * Get isFormatFinance
     * @return int
     * @author HaND
     */
    public int getIsFormatFinance() {
        return isFormatFinance;
    }

    /**
     * Set isFormatFinance
     * @param   isFormatFinance
     *          type int
     * @return
     * @author  HaND
     */
    public void setIsFormatFinance(int isFormatFinance) {
        this.isFormatFinance = isFormatFinance;
    }

    /**
     * Get isAllBorder
     * @return boolean
     * @author HaND
     */
    public boolean isAllBorder() {
        return isAllBorder;
    }

    /**
     * Set isAllBorder
     * @param   isAllBorder
     *          type boolean
     * @return
     * @author  HaND
     */
    public void setAllBorder(boolean isAllBorder) {
        this.isAllBorder = isAllBorder;
    }

    /**
     * Get xssfColor
     * @return XSSFColor
     * @author HaND
     */
    public XSSFColor getXssfColor() {
        return xssfColor;
    }

    /**
     * Set xssfColor
     * @param   xssfColor
     *          type XSSFColor
     * @return
     * @author  HaND
     */
    public void setXssfColor(XSSFColor xssfColor) {
        this.xssfColor = xssfColor;
    }

    /**
     * Get fontName
     * @return String
     * @author HaND
     */
    public String getFontName() {
        return fontName;
    }

    /**
     * Set fontName
     * @param   fontName
     *          type String
     * @return
     * @author  HaND
     */
    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    /**
     * Get sxssfWorkbook
     * @return SXSSFWorkbook
     * @author HaND
     */
    public SXSSFWorkbook getSxssfWorkbook() {
        return sxssfWorkbook;
    }

    /**
     * Set sxssfWorkbook
     * @param   sxssfWorkbook
     *          type SXSSFWorkbook
     * @return
     * @author  HaND
     */
    public void setSxssfWorkbook(SXSSFWorkbook sxssfWorkbook) {
        this.sxssfWorkbook = sxssfWorkbook;
    }

    /**
     * @return the cellStyleMessageError
     * @author taitm
     * @date   Jun 17, 2020
     */
    public CellStyle getCellStyleMessageError() {
        return cellStyleMessageError;
    }

    /**
     * @param cellStyleMessageError the cellStyleMessageError to set
     * @author taitm
     * @date Jun 17, 2020
     */
    public void setCellStyleMessageError(CellStyle cellStyleMessageError) {
        this.cellStyleMessageError = cellStyleMessageError;
    }

    /**
     * @return the cellStyleMessageWarning
     */
    public CellStyle getCellStyleMessageWarning() {
        return cellStyleMessageWarning;
    }

    /**
     * @param cellStyleMessageWarning the cellStyleMessageWarning to set
     */
    public void setCellStyleMessageWarning(CellStyle cellStyleMessageWarning) {
        this.cellStyleMessageWarning = cellStyleMessageWarning;
    }

    /**
     * @return the cellStyleDescription
     */
    public CellStyle getCellStyleDescription() {
        return cellStyleDescription;
    }

    /**
     * @param cellStyleDescription the cellStyleDescription to set
     */
    public void setCellStyleDescription(CellStyle cellStyleDescription) {
        this.cellStyleDescription = cellStyleDescription;
    }
}
