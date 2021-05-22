package dummy;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;
@Component
public class MyExcelWriter implements ItemWriter<Student> {  
	
	private HSSFWorkbook wb;
    public WritableResource resource;
    private int row;
    
    private ExecutionContext context;

	@Override
	public void write(List<? extends Student> items) throws Exception { 
		open(context); 
		createData(items);
		createFooterRow();
		
        try (FileOutputStream outputStream = new FileOutputStream("output.xlsx")) {
            wb.write(outputStream);
        }
	}
	
	public void open(ExecutionContext executionContext) {
        wb = new HSSFWorkbook();
        HSSFPalette palette = wb.getCustomPalette();
        HSSFSheet s = wb.createSheet();
        row = 0;
        createTitleRow(s, palette);
        createHeaderRow(s);
   
    }
	
	private void createTitleRow(HSSFSheet s, HSSFPalette palette) {
        HSSFColor redish = palette.findSimilarColor((byte) 0xE6, (byte) 0x50, (byte) 0x32);
        palette.setColorAtIndex(redish.getIndex(), (byte) 0xE6, (byte) 0x50, (byte) 0x32);

        CellStyle headerStyle = wb.createCellStyle();
        headerStyle.setWrapText(true);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(redish.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        HSSFRow r = s.createRow(row);

        Cell c = r.createCell(0);
        c.setCellValue("Internal Use Only");
        r.createCell(1).setCellStyle(headerStyle);
        r.createCell(2).setCellStyle(headerStyle);
        s.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        c.setCellStyle(headerStyle);

        CellUtil.setAlignment(c, HorizontalAlignment.CENTER);

        row++;
    }
	
	private void createHeaderRow(HSSFSheet s) {
        CellStyle cs = wb.createCellStyle();
        cs.setWrapText(true);
        cs.setAlignment(HorizontalAlignment.LEFT);

        HSSFRow r = s.createRow(row);
        r.setRowStyle(cs);

        Cell c = r.createCell(0);
        c.setCellValue("NAME");
        s.setColumnWidth(0, poiWidth(18.0));
        c = r.createCell(1);
        c.setCellValue("EMAIL_ADDRESS");
        s.setColumnWidth(1, poiWidth(24.0));
        c = r.createCell(2);
        c.setCellValue("PURCHASED_PACKAGE");
        s.setColumnWidth(2, poiWidth(18.0));

        row++;
    }
	
	public void createData(List<? extends Student> students) {
		HSSFSheet s = wb.getSheetAt(0);

        for (Student st : students) {
            HSSFRow r = s.createRow(row++);
            Cell c = r.createCell(0);
            c.setCellValue(st.getName());

            c = r.createCell(1);
            c.setCellValue(st.getEmail_address());
 
            c = r.createCell(2);
            c.setCellValue(st.getPurchased_package());
        }
	}
	
	private int poiWidth(double width) {
        return (int) Math.round(width * 256 + 200);
    }
	
	private void createFooterRow() {
        HSSFSheet s = wb.getSheetAt(0);
        HSSFRow r = s.createRow(row);
        Cell c = r.createCell(3);
        c.setCellType(CellType.FORMULA);
        c.setCellFormula(String.format("SUM(D3:D%d)", row));
        row++;

    }
}