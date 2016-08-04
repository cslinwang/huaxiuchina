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
 * excel��ȡ 
 * @author Snow 
 * HSSF����2003�漴��  .xls��β 
 * XSSF����2007��    ��   .xlsx��β 
 */  
public class XLSReader {  
    public static List readExcelData(String url)throws Exception{  
          
        // ��XLSX/ xls�ļ�������������  
        FileInputStream fis = new FileInputStream(url);  
        List hospitalList = new ArrayList();  
          
        // ����������Workbook  
        Workbook workBook = null;  
          
        // ��ȡ2007�棬��    .xlsx ��β  
        if(url.toLowerCase().endsWith("xlsx")){  
            try {  
                workBook = new XSSFWorkbook(fis);  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        // ��ȡ2003�棬��   .xls ��β  
        else if(url.toLowerCase().endsWith("xls")){  
            try {  
                workBook = new HSSFWorkbook(fis);  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        //Get the number of sheets in the xlsx file  
        int numberOfSheets = workBook.getNumberOfSheets();  
          
        // ѭ�� numberOfSheets  
        for(int sheetNum = 0; sheetNum < numberOfSheets; sheetNum++){  
              
            // �õ� ������ �ĵ� N����  
            Sheet sheet = workBook.getSheetAt(sheetNum);  
            Row row;  
            String cell;  
            for(int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++){  
                // ѭ������  
                row = sheet.getRow(i);  
                for(int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++){  
                    // ѭ������  
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