package com.huaxiuchina.util;  
  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.util.ArrayList;  
import java.util.List;  
  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
/** 
 * excel读取 
 * @author Snow 
 * HSSF用于2003版即以  .xls结尾 
 * XSSF用于2007版    以   .xlsx结尾 
 */  
public class XLSReader {  
    public static List readExcelData(String url)throws Exception{  
          
        // 从XLSX/ xls文件创建的输入流  
        FileInputStream fis = new FileInputStream(url);  
        List hospitalList = new ArrayList();  
          
        // 创建工作薄Workbook  
        Workbook workBook = null;  
          
        // 读取2007版，以    .xlsx 结尾  
        if(url.toLowerCase().endsWith("xlsx")){  
            try {  
                workBook = new XSSFWorkbook(fis);  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        // 读取2003版，以   .xls 结尾  
        else if(url.toLowerCase().endsWith("xls")){  
            try {  
                workBook = new HSSFWorkbook(fis);  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        //Get the number of sheets in the xlsx file  
        int numberOfSheets = workBook.getNumberOfSheets();  
          
        // 循环 numberOfSheets  
        for(int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++){  
              
            // 得到 工作薄 的第 N个表  
            Sheet sheet = workBook.getSheetAt(sheetNum);  
            Row row;  
            String cell;  
            for(int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++){  
                // 循环行数  
                row = sheet.getRow(i);  
                for(int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++){  
                    // 循环列数  
                    cell = row.getCell(j).toString();  
                    hospitalList.add(cell);  
//                  System.out.println(cell+"\t");  
                }  
            }  
        }  
        return hospitalList;  
    }  
   /* public static void main(String[] args)throws Exception {  
        List list = readExcelData("E:/111.xls");   
        System.out.println(list);  
    }  */
}  