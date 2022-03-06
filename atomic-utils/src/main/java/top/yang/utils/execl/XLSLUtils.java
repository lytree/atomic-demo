package top.yang.utils.execl;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;

public class XLSLUtils {

    public static void main(String[] args) {
        try {
            XSSFWorkbook sheets = new XSSFWorkbookFactory().create(new FileInputStream("D:\\1.xlsx"));
            SXSSFWorkbook sheets1 = new SXSSFWorkbook(sheets);
            SXSSFSheet sheetAt = sheets1.getSheetAt(0);
            int lastRowNum = sheetAt.getLastFlushedRowNum();
            System.out.println(lastRowNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
