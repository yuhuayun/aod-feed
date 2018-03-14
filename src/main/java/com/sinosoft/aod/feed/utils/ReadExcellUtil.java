package com.sinosoft.aod.feed.utils;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取Excel工具类
 */
public class ReadExcellUtil {


    /**
     * xls文件读取
     *
     * @param filePath 文件绝对路径
     * @param fileName 文件名
     * @return 读取文件数据，并以List返回
     */
    public static List readExcel(String filePath, String fileName) {
        //获取扩展名
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
//使用xls方式读取
        if ("xls".equals(ext)) {
            return readExcel_xls(filePath, fileName);
//使用xlsx方式读取
        } else if ("xlsx".equals(ext)) {
            return readExcel_xlsx(filePath, fileName);
        }
        return null;
    }

    public static List readExcel_xls(String filePath, String fileName) {
        List list = new ArrayList();
        try {
            File file = new File(filePath, fileName);
            InputStream inputStream = new FileInputStream(file);
            byte[] buf = IOUtils.toByteArray(inputStream);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
            Workbook wb = new HSSFWorkbook(byteArrayInputStream);

            //第一个工作表  ，第二个则为1，以此类推...
            Sheet sheet = wb.getSheetAt(0);
            int firstRowIndex = sheet.getFirstRowNum();
            int lastRowIndex = sheet.getLastRowNum();
            for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                Row row = sheet.getRow(rIndex);
                if (row != null) {
                    int firstCellIndex = row.getFirstCellNum();
                    int lastCellIndex = row.getLastCellNum();
                    //此处参数cIndex决定可以取到excel的列数。
                    for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
                        Cell cell = row.getCell(cIndex);
                        String value = null;
                        if (cell != null) {
                            value = cell.toString();
                            list.add(value);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List readExcel_xlsx(String filePath, String fileName) {
        List list = new ArrayList();

        File file = new File(filePath, fileName);

        XSSFWorkbook xwb;
        XSSFSheet sheet;
        Object value;
        XSSFRow row;
        XSSFCell cell;

        // 构造 XSSFWorkbook 对象，strPath 传入文件路径
        try {
            xwb = new XSSFWorkbook(new FileInputStream(file));
            // 读取第一章表格内容
            sheet = xwb.getSheetAt(0);
            value = null;
            row = null;
            cell = null;
            System.out.println("读取office 2007 excel内容如下：");
            for (int i = sheet.getFirstRowNum(); i <= sheet
                    .getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    DecimalFormat df = new DecimalFormat("0");// 格式化 number String
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
                    DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字

                    switch (cell.getCellType()) {
                        case XSSFCell.CELL_TYPE_STRING:
                            // System.out.println(i + "行" + j + " 列 is String type");
                            value = cell.getStringCellValue();
                            System.out.print("  " + value + "  ");
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:

                            if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                value = df.format(cell.getNumericCellValue());

                            } else if ("General".equals(cell.getCellStyle()
                                    .getDataFormatString())) {
                                value = nf.format(cell.getNumericCellValue());
                            } else {
                                value = sdf.format(HSSFDateUtil.getJavaDate(cell
                                        .getNumericCellValue()));
                            }
                            System.out.print("  " + value + "  ");
                            break;
                        case XSSFCell.CELL_TYPE_BOOLEAN:

                            value = cell.getBooleanCellValue();
                            System.out.print("  " + value + "  ");
                            break;
                        case XSSFCell.CELL_TYPE_BLANK:

                            value = "";

                            break;
                        default:

                            value = cell.toString();
                            System.out.print("  " + value + "  ");
                    }
                    if (value == null || "".equals(value)) {
                        continue;
                    }
                    list.add(value);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return list;
    }


/*public static void main(String[] args) {

        List list = readExcel("C:\\wenjian","file2.xlsx");
        for (Object tel : list
                ) {
            String tele = (String) tel;
            System.out.println(tele);

        }

    }*/
}