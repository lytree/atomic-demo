package top.yang.utils.execl;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;

public class XLSUtils {

    public static void main(String[] args) {
        try {
            XSSFWorkbook sheets = new XSSFWorkbookFactory().create(new FileInputStream("D:\\1.xlsx"));
            XSSFSheet sheetAt = sheets.getSheetAt(0);
            int lastRowNum = sheetAt.getLastRowNum();
            System.out.println(lastRowNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
