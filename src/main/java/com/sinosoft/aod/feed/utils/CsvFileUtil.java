package com.sinosoft.aod.feed.utils;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LONGLEI on 2018/1/19.
 * CSV文件上传工具类
 */
public class CsvFileUtil {

    private static final String SPECIAL_CHAR_A = "[^\",//n 　]";
    private static final String SPECIAL_CHAR_B = "[^\",//n]";

    /**
     * 私有化构造器，禁止实例化
     */
    private CsvFileUtil() {
    }

    /**
     * csv文件读取<BR/>
     * 读取绝对路径为argPath的csv文件数据，并以List返回。
     *
     * @param filePath csv文件绝对路径
     * @return csv文件数据（List<String[]>）
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<Map<String,String>> readConetntCsvFile(String filePath, String fileName) throws FileNotFoundException, IOException {
        CsvFileUtil util = new CsvFileUtil();
        File cvsFile = new File(filePath, fileName);
        List<List<String>> rows = new LinkedList<List<String>>();
        List<String> headList = new LinkedList<>();
        List<Map<String, String>> listResult = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(cvsFile);
            inputStreamReader = new InputStreamReader(fis, "GBK");
            bufferedReader = new BufferedReader(inputStreamReader);
            String regExp = util.getRegExp();
            String strLine = "";
            String str = "";
            while ((strLine = bufferedReader.readLine()) != null) {
                if (strLine.startsWith("\"") && strLine.endsWith("\"")) {
                    List<String> listCell = new LinkedList<String>();
                    Pattern pattern = Pattern.compile(regExp);
                    Matcher matcher = pattern.matcher(strLine);
                    while (matcher.find()) {
                        str = matcher.group();
                        str = str.trim();
                        if (str.endsWith(",")) {
                            str = str.substring(0, str.length() - 1);
                            str = str.trim();
                        }
                        if (str.startsWith("\"") && str.endsWith("\"")) {
                            str = str.substring(1, str.length() - 1);
                            if (util.isExisted("\"\"", str)) {
                                str = str.replaceAll("\"\"", "\"");
                            }
                        }
                        if (str != null) {
                            listCell.add(str);
                        }
                    }
                    rows.add(listCell);
                } else {
                    if (!CommonUtil.isEmptyStr(strLine.trim())) {
                        List<String> listCell = new LinkedList<String>();
                        String[] strArray = strLine.split(",", -1);
                        for (String s : strArray) {
                            if (s != null) listCell.add(s);
                        }
                        rows.add(listCell);
                    }
                }

            }
            if (rows.size() > 0) {
                headList = rows.get(0);
                rows.remove(0);
            }
            listResult = new LinkedList<Map<String, String>>();
            for (int i = 0; i < rows.size(); i++) {
                List<String> row = rows.get(i);
                if (row != null) {
                    Map<String, String> map = new LinkedHashMap<String, String>();
                    for (int k = 0; k < headList.size(); k++) {
                        String title = headList.get(k).trim();
                        String value = row.get(k).trim();
                        //System.out.println("转化前：title---------->"+title+" value---------->"+value);
                        title = transferKeyName(title);
                        //System.out.println("转化后：title---------->"+title+" value---------->"+value);
                        if (value.equals("") || value == null) {
                            value = "";
                        }
                        map.put(title, value);
                    }
                    listResult.add(map);
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return listResult;
    }

    /**
     * csv文件读取<BR/>
     * 读取绝对路径为argPath的csv文件数据，并以List返回。
     *
     * @param argPath csv文件绝对路径
     * @return csv文件数据（List<String[]>）
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List readHeadCsvFile(String argPath) throws FileNotFoundException, IOException {
        CsvFileUtil util = new CsvFileUtil();
        File cvsFile = new File(argPath);
        List<String> list = new LinkedList<String>();
        List<Map<String, Object>> listmap = new LinkedList<Map<String, Object>>();
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(cvsFile);
            inputStreamReader = new InputStreamReader(fis, "GBK");
            bufferedReader = new BufferedReader(inputStreamReader);
            String regExp = util.getRegExp();
            String strLine = "";
            String str = "";
            while ((strLine = bufferedReader.readLine()) != null) {
                if (strLine.startsWith("\"") && strLine.endsWith("\"")) {
                    Pattern pattern = Pattern.compile(regExp);
                    Matcher matcher = pattern.matcher(strLine);
                    while (matcher.find()) {
                        str = matcher.group();
                        str = str.trim();
                        if (str.endsWith(",")) {
                            str = str.substring(0, str.length() - 1);
                            str = str.trim();
                        }
                        if (str.startsWith("\"") && str.endsWith("\"")) {
                            str = str.substring(1, str.length() - 1);
                            if (util.isExisted("\"\"", str)) {
                                str = str.replaceAll("\"\"", "\"");
                            }
                        }
                        if (!"".equals(str)) {
                            list.add(str);
                        }
                    }
                    if (list.size() > 0) {
                        break;
                    }
                } else {
                    String[] strArray = strLine.split(",");
                    for (String s : strArray) {
                        list.add(s);
                    }
                    if (list.size() > 0) {
                        break;
                    }
                }
            }
            for (int i = 0; i < list.size(); i++) {
                //System.out.println("sn---------->"+i+" title---------->"+list.get(i));
                Map<String, Object> title = new LinkedHashMap<String, Object>();
                title.put("sn", i);
                title.put("title", list.get(i));
                listmap.add(title);
            }
        } catch (FileNotFoundException e) {
            System.out.print("文件读取异常" + e.getMessage());
            throw e;
        } catch (IOException e) {
            System.out.print("IO异常" + e.getMessage());
            throw e;
        } finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                System.out.print("IO异常" + e.getMessage());
                throw e;
            }
        }
        return listmap;
    }

    /**
     * csv文件做成<BR/>
     * 将argList写入argPath路径下的argFileName文件里。
     *
     * @param argList     要写入csv文件的数据（List<String[]>）
     * @param argPath     csv文件路径
     * @param argFileName csv文件名
     * @param isNewFile   是否覆盖原有文件
     * @throws IOException
     * @throws Exception
     */
    public static void writeCsvFile(List argList, String argPath, String argFileName, boolean isNewFile)
            throws IOException, Exception {
        CsvFileUtil util = new CsvFileUtil();
        // 数据check
        if (argList == null || argList.size() == 0) {
            throw new Exception("没有数据");
        }
        for (int i = 0; i < argList.size(); i++) {
            if (!(argList.get(i) instanceof String[])) {
                throw new Exception("数据格式不对");
            }
        }
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        String strFullFileName = argPath;
        if (strFullFileName.lastIndexOf("//") == (strFullFileName.length() - 1)) {
            strFullFileName += argFileName;
        } else {
            strFullFileName += "//" + argFileName;
        }
        File file = new File(strFullFileName);
        // 文件路径check
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (isNewFile) {
                // 覆盖原有文件
                fileWriter = new FileWriter(file);
            } else {
                // 在原有文件上追加数据
                fileWriter = new FileWriter(file, true);
            }
            bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < argList.size(); i++) {
                String[] strTemp = (String[]) argList.get(i);
                for (int j = 0; j < strTemp.length; j++) {
                    if (util.isExisted("\"", strTemp[j])) {
                        strTemp[j] = strTemp[j].replaceAll("\"", "\"\"");
                        bufferedWriter.write("\"" + strTemp[j] + "\"");
                    } else if (util.isExisted(",", strTemp[j])
                            || util.isExisted("/n", strTemp[j])
                            || util.isExisted(" ", strTemp[j])
                            || util.isExisted("��", strTemp[j])) {
                        bufferedWriter.write("\"" + strTemp[j] + "\"");
                    } else {
                        bufferedWriter.write(strTemp[j]);
                    }
                    if (j < strTemp.length - 1) {
                        bufferedWriter.write(",");
                    }
                }
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * @param argChar
     * @param argStr
     * @return
     */
    private boolean isExisted(String argChar, String argStr) {

        boolean blnReturnValue = false;
        if ((argStr.indexOf(argChar) >= 0)
                && (argStr.indexOf(argChar) <= argStr.length())) {
            blnReturnValue = true;
        }
        return blnReturnValue;
    }

    /**
     * 将数据库字段转换为实体属性
     * @param fieldName
     * @return
     */
    public static String transferKeyName(String fieldName) {
        //System.out.println("in is pre= " + fieldName);
        fieldName = fieldName.toLowerCase();
        //System.out.println("in is suf= " + fieldName);
        StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile("_[a-z|A-Z]");
        Matcher m = p.matcher(fieldName);
        while (m.find()) {
            m.appendReplacement(sb, "_" + m.group().toUpperCase());
            //System.out.println("m.group() is= " + m.group());
        }
        m.appendTail(sb);
        fieldName = sb.toString().replaceAll("_", "");
        //System.out.println("sb is= " + fieldName);
        return fieldName;
    }

    /**
     * 正则表达式。
     *
     * @return 匹配csv文件里最小单位的正则表达式。
     */
    private String getRegExp() {
        String strRegExp = "";

        strRegExp = "\"((" + SPECIAL_CHAR_A + "*[,//n 　])*(" + SPECIAL_CHAR_A + "*\"{2})*)*" + SPECIAL_CHAR_A + "*\"[ 　]*,[ 　]*"
                        + "|" + SPECIAL_CHAR_B + "*[ 　]*,[ 　]*"
                        + "|\"((" + SPECIAL_CHAR_A + "*[,//n 　])*(" + SPECIAL_CHAR_A + "*\"{2})*)*" + SPECIAL_CHAR_A + "*\"[ 　]*"
                        + "|" + SPECIAL_CHAR_B + "*[ 　]*";
        return strRegExp;
    }

    public static void main(String[] args) {
        try {
            //readHeadCsvFile("d://uploadFile/test1.csv");
            Long a = System.currentTimeMillis();
            List<Map<String, String>> contentList = readConetntCsvFile("d://uploadFile/", "测试名单2.csv");
            Long b = System.currentTimeMillis();
            System.out.println("读取文件耗时---------->"+(b-a)+"(毫秒)");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CsvFileUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CsvFileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
//        String file = "D://uploadFile/20180123.csv";
//        String[] files = file.split("/");
//        String name = files[files.length-1].trim();
//        System.out.print(name);
    }


}
