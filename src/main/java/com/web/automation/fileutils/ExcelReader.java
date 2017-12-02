package com.web.automation.fileutils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Utility to read Excel files. This file makes use of apache poi for reading excel files.
 * It supports both "xls" and "xlsx" file extension.
 */
public class ExcelReader {
    public static ResultSet resultset;
    public static String TestDataSheetName = "Data";
    protected static String TestDataPath = System.getProperty("user.dir").replace("\\", "/") + "/testdata/TestData.xlsx";
    protected static String testDriver = System.getProperty("user.dir").replace("\\", "/") + "/testdata/Test_Driver.xlsx";
    private static Cell openCell;
    private static Row openRow;
    private static Sheet openSheet;
    private static Workbook openWorkbook;
    private static Map<String, List<String>> storedData = new LinkedHashMap<String, List<String>>();
    private static XSSFWorkbook workbook = null;
    private static XSSFSheet sheet = null;
    /******** :::::::: Code for Keyword Driven Framework:: Start ::::::: ******/
    public String filename = testDriver;
    public String path;
    public FileInputStream fis = null;
    public FileOutputStream fileOut = null;
    private XSSFRow row = null;
    private XSSFCell cell = null;

    /******** :::::::: Code for Keyword Driven Framework:: end ::::::: ******/

    /**
     * Creates a ExcelReader object based on filePath
     *
     * @param filePath File path of the file to be opened
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ExcelReader(String filePath) throws FileNotFoundException, IOException {
        this(new File(filePath));
        System.out.println("Spreadsheet path :- ***** " + filePath + " *****");
        this.path = filePath;
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            fis.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Creates a ExcelReader object based on <code>File</code> object passed
     *
     * @param file <code>File</code> object of the file to be opened.
     * @throws IOException
     * @throws FileNotFoundException
     */
    public ExcelReader(File file) {
        try {
            this.openFile(file, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ExcelReader(InputStream fileStream) throws IOException, FileNotFoundException {
        this.openFile(fileStream, 0);
    }

    /**
     * @param filePath
     * @param sheetNo
     * @throws IOException
     * @throws FileNotFoundException
     */
    public ExcelReader(String filePath, int sheetNo) throws IOException, FileNotFoundException {
        this.openFile(filePath, sheetNo);
    }

    public ExcelReader(InputStream fileStream, int sheetNo) throws IOException, FileNotFoundException {
        this.openFile(fileStream, sheetNo);
    }

    /**
     * @param filePath
     * @param sheetName
     * @throws IOException
     * @throws FileNotFoundException
     */
    public ExcelReader(String filePath, String sheetName) throws IOException, FileNotFoundException {
        this.openFile(filePath, sheetName);
    }

    public ExcelReader(InputStream fileStream, String sheetName) throws IOException, FileNotFoundException {
        this.openFile(fileStream, sheetName);
    }

    /**
     * @param file
     * @param sheetNo
     * @throws IOException
     * @throws FileNotFoundException
     */
    public ExcelReader(File file, int sheetNo) throws IOException, FileNotFoundException {

        try {

            this.openFile(file, sheetNo);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param file
     * @param sheetName
     * @throws IOException
     * @throws FileNotFoundException
     */
    public ExcelReader(File file, String sheetName) {
        try {

            this.openFile(file, sheetName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openWorkbook(String filePath) throws FileNotFoundException,
            IOException {
        openWorkbook(new File(filePath));
    }

    public static void openWorkbook(File file) throws FileNotFoundException,
            IOException {
        try {
            openWorkbook = WorkbookFactory.create(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void openWorkbook(InputStream fileStream) throws FileNotFoundException,
            IOException {
        try {
            openWorkbook = WorkbookFactory.create(fileStream);
            System.out.println(openWorkbook);
        } catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean openSheet(String workbookpath, String sheetName) {
        try {
            if (System.getProperty("os.name").equalsIgnoreCase("windows")) {
                TestDataPath = workbookpath;
            } else {
                TestDataPath = workbookpath;
            }
            //openWorkbook=new XSSFWorkbook(new File(TestDataPath));
            openWorkbook = new XSSFWorkbook(new File("C:/Users/IN01513/Desktop/testdata/TestData.xlsx"));
            openSheet = openWorkbook.getSheet(sheetName);
        } catch (Throwable e) {
            System.out.println(e.getStackTrace());
        }
        return openSheet.isSelected();
    }

    /**
     * Gets the data from the currently opened sheet based on row and column number
     *
     * @param row    Row no. from which the value has to be fetched
     * @param column Respective column no. in the row from which the value has to be fetched
     * @return The data present in the respective row & column. If no value is found it returns and empty String.
     */
    public static String getData(int row, int column) {
        String data = "";
        try {

            openRow = openSheet.getRow(row);
            openCell = openRow.getCell(column);
            int cellType = openCell.getCellType();
            switch (cellType) {
                case 0:
                    if (DateUtil.isCellDateFormatted(openCell)) {
                        Date dt = openCell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat(
                                "dd MM yyyy HH:mm:ss");
                        data = sdf.format(dt);
                    } else
                        data = Long.toString(Math.round(openCell
                                .getNumericCellValue()));
                    break;
                case 1:
                    data = openCell.getRichStringCellValue().getString();
                    break;
                case 2:
                    data = openCell.getRichStringCellValue().getString();
                    break;
                case 3:
                    data = openCell.getRichStringCellValue().getString();
                    break;
                case 4:
                    data = Boolean.toString(openCell.getBooleanCellValue());
                    break;
                case 5:
                    data = Byte.toString(openCell.getErrorCellValue());
                    break;
                default:
                    data = openCell.getRichStringCellValue().getString();
            }

            if (data == null) {
                data = "";
            }
            return data;
        } catch (Exception e) {
            if (openRow == null || openCell == null || data == null) {
                data = "";
                return data;
            } else {
                System.out.println(e);
                return "";
            }
        }
    }

    /**
     * Stores the whole data of the currently opened sheet in a Map containing keys
     */
    public static void storeData() {
        try {
            int rowCount = openSheet.getPhysicalNumberOfRows();
            Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
            for (int i = 0; i < rowCount; i++) {
                Row row = openSheet.getRow(i);
                String rowKey = "";
                int cellCount = row.getPhysicalNumberOfCells();
                for (int j = 0; j < cellCount; j++) {
                    String cellValue = row.getCell(j).getStringCellValue();
                    if (j == 0) {
                        rowKey = cellValue;
                        map.put(rowKey, new ArrayList<String>());
                    } else {
                        map.get(rowKey).add(cellValue);
                    }
                }
            }
            storedData = map;
        } catch (Exception ex) {
            ex.getLocalizedMessage();
        }
        ;
    }

    public static Map<String, List<String>> getStoredData() {
        storeData();
        return storedData;
    }

    public static Map<String, List<String>> getStoredData(String workbookpath, String sheetName) {
        openSheet(workbookpath, sheetName);
        storedData.clear();
        return getStoredData();
    }

    /**
     * @param fileName
     * @return
     * @author ashih jain
     */
    public static List<Map<String, String>> getStoredDataAsMap(String fileName) {
        List<Map<String, String>> listOfDataMap = new ArrayList<Map<String, String>>();

        try {
            // Create the input stream from the xlsx/xls file
            FileInputStream fis = new FileInputStream(fileName);

            // Create Workbook instance for xlsx/xls file input stream
            Workbook workbook = null;
            if (fileName.toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileName.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            }

            // Get the 1st sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);

            // every sheet has rows, iterate over them
            Iterator<Row> rowIterator = sheet.iterator();
            boolean firstRowFlag = true;
            Row firstRow = null;
            while (rowIterator.hasNext()) {
                HashMap<String, String> rowMap = new HashMap<String, String>();
                Row currentRow = (Row) rowIterator.next();
                if (firstRowFlag) {
                    //Sets to false once it founds the first row
                    firstRowFlag = false;
                    // Gets the first row and save it in a variable to use it later, which are expected to be column names
                    firstRow = currentRow;
                    continue; //Continue with next record.
                }
                //This loop executes from second row onwards, where actual test data starts
                for (int i = 0; i < currentRow.getLastCellNum(); i++) {
                    //TODO: Handle Null values, handle Cell Data Type
                    //Gets the current cell value
                    String currentCellValue = currentRow.getCell(i).getStringCellValue();

                    //Gets the appropriate column name for the current cell
                    String colName = firstRow.getCell(i).getStringCellValue();

                    //Adds to the Row Map
                    rowMap.put(colName, currentCellValue);
                }

                //Row Map adds to the List
                listOfDataMap.add(rowMap);
            }
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfDataMap;
    }

    public static ArrayList<String> getListDataFromMap(Map<String, List<String>> excelData) {
        ArrayList<String> list = new ArrayList();
        for (Map.Entry<String, List<String>> entry : excelData.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(entry.getKey())) {
                list = (ArrayList<String>) entry.getValue();
            }
        }
        return list;
    }

    /**
     * @param WbookPath
     * @param SheetName
     * @param RowNum
     * @param ColumnName
     * @return
     * @throws IOException
     * @author Ravikrishna
     */
    public static String fn_GetCellData(String WbookPath, String SheetName, int RowNum, String ColumnName)
            throws IOException {

        FileInputStream FISObj = new FileInputStream(WbookPath);
        @SuppressWarnings("resource")
        XSSFWorkbook WbookObj = new XSSFWorkbook(FISObj);
        XSSFSheet WSheetObj = WbookObj.getSheet(SheetName);
        XSSFRow RowObj = WSheetObj.getRow(RowNum);
        int columnNumber = fn_GetCellNumberByColumName(WSheetObj, ColumnName);
        XSSFCell CellObj = RowObj.getCell(columnNumber, XSSFRow.CREATE_NULL_AS_BLANK);
        int celltypenumber = CellObj.getCellType();
        String CellVal = null;
        if (celltypenumber == XSSFCell.CELL_TYPE_NUMERIC) {
            Double DblCellVal = CellObj.getNumericCellValue();
            Integer intcellval = DblCellVal.intValue();
            CellVal = intcellval.toString();
        } else if (celltypenumber == XSSFCell.CELL_TYPE_STRING) {
            CellVal = CellObj.getStringCellValue();
        }
        return CellVal;
    }

    /**
     * @param WSheetObj
     * @param arg_ColumnName
     * @return
     * @throws IOException
     * @author Ravikrishna
     */
    public static int fn_GetCellNumberByColumName(XSSFSheet WSheetObj, String ColumnName) throws IOException {

        XSSFRow FstRowObj = WSheetObj.getRow(0);
        int columnCount = FstRowObj.getLastCellNum();
        int columnNumber = 0;
        for (int i = 0; i <= columnCount - 1; i++) {
            XSSFCell cellObj = FstRowObj.getCell(i, XSSFRow.CREATE_NULL_AS_BLANK);
            String xl_ColumnName = cellObj.getStringCellValue();
            xl_ColumnName = xl_ColumnName.trim();
            ColumnName = ColumnName.trim();
            if (ColumnName.equalsIgnoreCase(xl_ColumnName) == true) {
                columnNumber = i;
                break;
            }
        }
        return columnNumber;
    }

    /**
     * @param xl_FilePath
     * @param SheetName
     * @return
     * @throws IOException
     * @author Ravi krishna
     */
    public static int fn_GetRowCount(String xl_FilePath, String SheetName) throws IOException {

        File xlFileObj = new File(xl_FilePath);
        FileInputStream FISObj = new FileInputStream(xlFileObj);
        @SuppressWarnings("resource")
        XSSFWorkbook WbookObj = new XSSFWorkbook(FISObj);
        XSSFSheet WSheetObj = WbookObj.getSheet(SheetName);
        int rowcount = WSheetObj.getLastRowNum();
        return rowcount;
    }

    /**
     * @throws IOException
     * @author Ravi krishna
     */
    public static void getAllData(String worbookPath, String sheetName) throws IOException {
        FileInputStream FISObj = new FileInputStream(worbookPath);
        @SuppressWarnings("resource")
        XSSFWorkbook WbookObj = new XSSFWorkbook(FISObj);
        XSSFSheet WSheetObj = WbookObj.getSheet(sheetName);
        int rowcount = WSheetObj.getLastRowNum();//// it returns nuberofrows-1
        for (int i = 1; i <= rowcount; i++) {
            XSSFRow RowObj = WSheetObj.getRow(i);
            int cellcount = 0;
            try {
                cellcount = RowObj.getPhysicalNumberOfCells();//// it returns
                //// number of
                //// cells only
                //// not -1
            } catch (Exception e) {

            }
            for (int j = 0; j <= cellcount - 1; j++) {
                MissingCellPolicy MCPObj = XSSFRow.CREATE_NULL_AS_BLANK;
                XSSFCell CellObj = RowObj.getCell(j, MCPObj);
                int celltypenumber = CellObj.getCellType();
                String CellVal = null;
                if (celltypenumber == XSSFCell.CELL_TYPE_NUMERIC) {
                    Double DblCellVal = CellObj.getNumericCellValue();
                    Integer intcellval = DblCellVal.intValue();
                    CellVal = intcellval.toString();
                } else if (celltypenumber == XSSFCell.CELL_TYPE_STRING) {
                    CellVal = CellObj.getStringCellValue();
                }
                System.out.print(CellVal + ",");
            }
        }
    }

    /**
     * @param startRowNumber
     * @param endRowNumber
     * @author Ravi krishna
     */
    public static void storeDatafromRow(int startRowNumber, int endRowNumber) {
        try {
            Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
            int rowCount = openSheet.getPhysicalNumberOfRows();
            if (rowCount >= endRowNumber) {
                String[] array = new String[100];
                Row row = openSheet.getRow(0);
                int cellCount = row.getPhysicalNumberOfCells();
                for (int j = 0; j < cellCount; j++) {
                    String cellValue = row.getCell(j).getStringCellValue();
                    array[j] = cellValue;
                    map.put(cellValue, new ArrayList<String>());
                }
                for (int i = startRowNumber; i < endRowNumber; i++) {
                    row = openSheet.getRow(i);
                    cellCount = row.getPhysicalNumberOfCells();
                    for (int j = 0; j < cellCount; j++) {
                        MissingCellPolicy MCPObj = XSSFRow.CREATE_NULL_AS_BLANK;
                        XSSFCell CellObj = (XSSFCell) row.getCell(j, MCPObj);
                        int celltypenumber = CellObj.getCellType();
                        String cellValue = null;
                        if (celltypenumber == XSSFCell.CELL_TYPE_NUMERIC) {
                            Double DblCellVal = CellObj.getNumericCellValue();
                            Integer intcellval = DblCellVal.intValue();
                            cellValue = intcellval.toString();
                        } else if (celltypenumber == XSSFCell.CELL_TYPE_STRING) {
                            cellValue = CellObj.getStringCellValue();
                        } else if (celltypenumber == XSSFCell.CELL_TYPE_BOOLEAN) {
                            Boolean booleanCellValue = CellObj.getBooleanCellValue();
                            cellValue = booleanCellValue.toString();
                        }
                        map.get(array[j]).add(cellValue);
                    }
                }
            }
            storedData = map;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param workBook
     * @param sheetName
     * @param startRowNumber
     * @param endRowNumber
     * @return
     * @author Ravi krishna
     */
    public static Map<String, List<String>> getStoredDataFromSelectedRows(String workBook, String sheetName,
                                                                          int startRowNumber, int endRowNumber) {
        openSheet(workBook, sheetName);
        storedData.clear();
        storeDatafromRow(startRowNumber, endRowNumber);
        return storedData;
    }

    /**
     * to read data from sheet
     *
     * @param sheet
     * @return
     * @throws IOException
     * @author Ravi krishna
     */
    public static List<String> read(XSSFSheet sheet) throws IOException {
        List<String> excelVal = new ArrayList<String>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row rovVal = (Row) rowIterator.next();
            String rowVal = "";
            for (int i = 0; i < rovVal.getLastCellNum(); i++) {
                XSSFCell cellA1 = (XSSFCell) rovVal.getCell(i);
                rowVal = rowVal + cellA1.getStringCellValue() + "~";
            }
            excelVal.add(rowVal);
        }

        return excelVal;
    }

    /**
     * @param excelFilePath
     * @param sheetName
     * @param numberOfParameters
     * @return
     * @author Ravi krishna
     */
    public static String[][] readParamsFromExcel(String excelFilePath, String sheetName, int numberOfParameters) {
        String[][] finalTestData = null;
        try {
            XSSFSheet sheet = (XSSFSheet) openWorkbook.getSheet(sheetName);
            List<String> excelVal = read(sheet);
            finalTestData = new String[excelVal.size()][numberOfParameters];
            int count = 0;
            for (String rowVal : excelVal) {
                String[] col1DatatArray = rowVal.split("~");
                for (int i = 0; i < numberOfParameters; i++) {
                    finalTestData[count] = col1DatatArray;
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalTestData;
    }

    /**
     * to read data from sheet
     *
     * @param sheet
     * @return
     * @throws IOException
     */
    public static List<String> readData(XSSFSheet sheet) throws IOException {
        List<String> excelVal = new ArrayList<String>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row rovVal = (Row) rowIterator.next();
            String rowVal = "";
            for (int i = 0; i < rovVal.getLastCellNum(); i++) {
                XSSFCell cellA1 = (XSSFCell) rovVal.getCell(i);
                rowVal = rowVal + cellA1.getStringCellValue() + "~";
            }
            excelVal.add(rowVal);
        }
        return excelVal;
    }

    // returns the row count in a sheet
    public static int getRowCount(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1)
            return 0;
        else {
            sheet = workbook.getSheetAt(index);
            int number = sheet.getLastRowNum() + 1;
            return number;
        }

    }

    /**
     * Function to get data from xls sheet in 2 dimensional array
     *
     * @param testCase - testCase name
     * @param xls      - ExcelReader Object
     * @return 2 dimensional array
     */
    public static Object[][] getDataForKeywordDriven(String testCase, ExcelReader xls, String sheetName) {

        System.out.println("******getData*******: " + testCase);
        // find the test in xls
        // find number of cols in test
        // number of rows in test
        // put the data in hashtable and put hashtable in object array
        // return object array
        int testCaseStartRowNum = 0;
        //iterate through all rows from the sheet Test Data
        for (int rNum = 1; rNum <= xls.getRowCount(sheetName); rNum++) {
            //to identify testCase starting row number
            if (testCase.equals(xls.getCellData(sheetName, 0, rNum))) {
                testCaseStartRowNum = rNum;
                break;
            }
        }

        System.out.println("Test Starts from row -> " + testCaseStartRowNum);
        // total cols
        int colStartRowNum = testCaseStartRowNum + 1;
        int cols = 0;
        //Get the total number of columns for which test data is present
        while (!xls.getCellData(sheetName, cols, colStartRowNum).equals("")) {
            cols++;
        }

        System.out.println("Total cols in test -> " + cols);
        // rows
        int rowStartRowNum = testCaseStartRowNum + 2;
        int rows = 0;
        //Get the total number of rows for which test data is present
        while (!xls.getCellData(sheetName, 0, (rowStartRowNum + rows)).equals("")) {
            rows++;
        }

        System.out.println("Total rows in test -> " + rows);
        Object[][] data = new Object[rows][1];
        Hashtable<String, String> table = null;
        // print the test data
        for (int rNum = rowStartRowNum; rNum < (rows + rowStartRowNum); rNum++) {
            table = new Hashtable<String, String>();
            for (int cNum = 0; cNum < cols; cNum++) {
                table.put(xls.getCellData(sheetName, cNum, colStartRowNum), xls.getCellData(sheetName, cNum, rNum));
                System.out.print(xls.getCellData(sheetName, cNum, rNum) + " - ");
            }
            data[rNum - rowStartRowNum][0] = table;
            System.out.println();
        }
        return data;// dummy
    }

    /**
     * @param file
     * @param sheetNo
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void openFile(File file, int sheetNo) throws IOException,
            FileNotFoundException {
        openWorkbook(file);
        openSheet = openWorkbook.getSheetAt(sheetNo);
    }

    public void openFile(InputStream fileStream, int sheetNo) throws IOException,
            FileNotFoundException {
        openWorkbook(fileStream);
        openSheet = openWorkbook.getSheetAt(sheetNo);
    }

    /**
     * @param filePath
     * @param sheetNo
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void openFile(String filePath, int sheetNo)
            throws FileNotFoundException, IOException {
        this.openFile(new File(filePath), sheetNo);
    }

    public void openFile(File file, String sheetName)
            throws FileNotFoundException, IOException {
        openWorkbook(file);
        openSheet = openWorkbook.getSheet(sheetName);
    }

    public void openFile(String filePath, String sheetName)
            throws FileNotFoundException, IOException {
        openWorkbook(filePath);
        openSheet = openWorkbook.getSheet(sheetName);
    }

    public void openFile(InputStream fileStream, String sheetName)
            throws FileNotFoundException, IOException {
        openWorkbook(fileStream);
        openSheet = openWorkbook.getSheet(sheetName);
    }

    @SuppressWarnings("unused")
    private void openSheet() {
        openSheet = openWorkbook.getSheetAt(0);
    }

    @SuppressWarnings("unused")
    private void openSheet(int sheetNo) {
        openSheet = openWorkbook.getSheetAt(sheetNo);
    }

    public Workbook getOpenWorkbook() {
        return openWorkbook;
    }

    /**
     * Gets the no. of rows in the currently opened sheet
     *
     * @return The actual no of physical rows present
     */
    public int getNoOfRows() {
        return openSheet.getPhysicalNumberOfRows();
    }

    /**
     * Gets the no. of column present in the first row of the currently opened sheet.
     *
     * @return Return the no. of column present in the first row of the currently opened sheet.
     */
    public int getNoOfColumn() {
        return this.getNoOfColumn(0);
    }

    /**
     * Gets the no. of column present in the specified row of the currently opened sheet.
     *
     * @param rowNo Row no. for which the no. of column have to evaluated.
     * @return Return the no. of column present in the specified row of the currently opened sheet.
     */
    public int getNoOfColumn(int rowNo) {
        Row rw = openSheet.getRow(rowNo);
        return rw.getPhysicalNumberOfCells();
    }

    public int getParameters() {
        Row rw = openSheet.getRow(0);
        return rw.getPhysicalNumberOfCells();
    }

    public void storeDataBkp() {
        Row rw;
        int rowCount = openSheet.getPhysicalNumberOfRows();
        storedData.clear();
        for (int i = 0; i < rowCount - 1; i++) {
            rw = openSheet.getRow(i);
            String key = getData(0, i);
            List<String> valueList = new ArrayList<String>();
            storedData.put(key, valueList);
            for (int j = 1; j < rw.getPhysicalNumberOfCells(); j++) {
                String data = getData(j, i);
                valueList.add(data);
            }
        }
    }

    /////*************************** Extra Excel Utilitties ***********************************//////

    public Map<String, List<String>> getStoredDataBkp() {
        if (storedData.isEmpty()) {
            storeData();
        }
        return storedData;
    }

    public int getNumberOfSheets() {
        return openWorkbook.getNumberOfSheets();
    }

    /******** :::::::: Code for Keyword Driven Framework:: Start ::::::: ******/

    // returns true if data is set successfully else false
    public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

            if (rowNum <= 0)
                return false;

            int index = workbook.getSheetIndex(sheetName);
            int colNum = -1;
            if (index == -1)
                return false;


            sheet = workbook.getSheetAt(index);


            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                //System.out.println(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equals(colName))
                    colNum = i;
            }
            if (colNum == -1)
                return false;

            sheet.autoSizeColumn(colNum);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);

            // cell style
            //CellStyle cs = workbook.createCellStyle();
            //cs.setWrapText(true);
            //cell.setCellStyle(cs);
            cell.setCellValue(data);

            fileOut = new FileOutputStream(path);

            workbook.write(fileOut);

            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // returns true if data is set successfully else false
    public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {
        //System.out.println("setCellData setCellData******************");
        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

            if (rowNum <= 0)
                return false;

            int index = workbook.getSheetIndex(sheetName);
            int colNum = -1;
            if (index == -1)
                return false;


            sheet = workbook.getSheetAt(index);
            //System.out.println("A");
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                //System.out.println(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
                    colNum = i;
            }

            if (colNum == -1)
                return false;
            sheet.autoSizeColumn(colNum); //ashish
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                row = sheet.createRow(rowNum - 1);

            cell = row.getCell(colNum);
            if (cell == null)
                cell = row.createCell(colNum);

            cell.setCellValue(data);
            XSSFCreationHelper createHelper = workbook.getCreationHelper();

            //cell style for hyperlinks
            //by default hypelrinks are blue and underlined
            CellStyle hlink_style = workbook.createCellStyle();
            XSSFFont hlink_font = workbook.createFont();
            hlink_font.setUnderline(XSSFFont.U_SINGLE);
            hlink_font.setColor(IndexedColors.BLUE.getIndex());
            hlink_style.setFont(hlink_font);
            //hlink_style.setWrapText(true);

            XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
            link.setAddress(url);
            cell.setHyperlink(link);
            cell.setCellStyle(hlink_style);

            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);

            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // returns true if sheet is created successfully else false
    public boolean addSheet(String sheetname) {

        FileOutputStream fileOut;
        try {
            workbook.createSheet(sheetname);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // returns true if sheet is removed successfully else false if sheet does not exist
    public boolean removeSheet(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1)
            return false;

        FileOutputStream fileOut;
        try {
            workbook.removeSheetAt(index);
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // returns true if column is created successfully
    public boolean addColumn(String sheetName, String colName) {
        //System.out.println("**************addColumn*********************");

        try {
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            int index = workbook.getSheetIndex(sheetName);
            if (index == -1)
                return false;

            XSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            sheet = workbook.getSheetAt(index);

            row = sheet.getRow(0);
            if (row == null)
                row = sheet.createRow(0);

            //cell = row.getCell();
            //if (cell == null)
            //System.out.println(row.getLastCellNum());
            if (row.getLastCellNum() == -1)
                cell = row.createCell(0);
            else
                cell = row.createCell(row.getLastCellNum());

            cell.setCellValue(colName);
            cell.setCellStyle(style);

            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // removes a column and all the contents
    public boolean removeColumn(String sheetName, int colNum) {
        try {
            if (!isSheetExist(sheetName))
                return false;
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            XSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
            XSSFCreationHelper createHelper = workbook.getCreationHelper();
            style.setFillPattern(HSSFCellStyle.NO_FILL);
            for (int i = 0; i < getRowCount(sheetName); i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    cell = row.getCell(colNum);
                    if (cell != null) {
                        cell.setCellStyle(style);
                        row.removeCell(cell);
                    }
                }
            }
            fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    // true - Y
    // false - N

    //String sheetName, String testCaseName,String keyword ,String URL,String message
    public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url, String message) {
        //System.out.println("ADDING addHyperLink******************");

        url = url.replace('\\', '/');
        if (!isSheetExist(sheetName))
            return false;

        sheet = workbook.getSheet(sheetName);

        for (int i = 2; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {
                //System.out.println("**caught "+(i+index));
                setCellData(sheetName, screenShotColName, i + index, message, url);
                break;
            }
        }
        return true;
    }

	/*public static ExcelReader xls  = new ExcelReader(new File(TestDataPath));

	synchronized public Hashtable<String, String> getData(String testCase, String sheetName){
		System.out.println("*************");
		System.out.println("*************");
		// find the test in xls
		// find number of cols in test
		// number of rows in test
		// put the data in hashtable and put hashtable in object array
		// return object array
		int testCaseStartRowNum=0;
		for(int rNum=1;rNum<=xls.getRowCount(sheetName);rNum++){
			if(testCase.equals(xls.getCellData(sheetName, 0, rNum))){
				testCaseStartRowNum = rNum;
				break;
			}
		}
		System.out.println("Test Starts from row -> "+ testCaseStartRowNum);

		int cols=xls.getColumnCount(sheetName);
		Hashtable<String,String> table=null;

		// print the test data

		table=new Hashtable<String,String>();
		for(int cNum=0;cNum<cols;cNum++){
			table.put(xls.getCellData(sheetName, cNum, 1),xls.getCellData(sheetName, cNum, testCaseStartRowNum));
			System.out.print(xls.getCellData(sheetName, cNum, testCaseStartRowNum)+" - ");
		}
		return table;// dummy
	}

	public static ExcelReader xls2 = new ExcelReader(new File(TestDataPath));
	synchronized public static Hashtable<String, String> getDataSignUp(String testCase, String sheetName){
		System.out.println("*************");
		// find the test in xls
		// find number of cols in test
		// number of rows in test
		// put the data in hashtable and put hashtable in object array
		// return object array
		int testCaseStartRowNum=0;
		for(int rNum=1;rNum<=xls2.getRowCount(sheetName);rNum++){
			if(testCase.equals(xls2.getCellData(sheetName, 0, rNum))){
				testCaseStartRowNum = rNum;
				break;
			}
		}
		//System.out.println("Test Starts from row -> "+ testCaseStartRowNum);

		int cols=xls2.getColumnCount(sheetName);
		Hashtable<String,String> table=null;

		// print the test data

		table=new Hashtable<String,String>();
		for(int cNum=0;cNum<cols;cNum++){
			table.put(xls2.getCellData(sheetName, cNum, 1),xls2.getCellData(sheetName, cNum, testCaseStartRowNum));
			//System.out.print(xls2.getCellData(sheetName, cNum, testCaseStartRowNum)+" - ");
		}
		return table;// dummy
	}
	 */
    /*public ExcelReader xls1 = new ExcelReader(new File(TestDataPath));
    synchronized public List<String> getAllRow(String sheetName){
		System.out.println("*************");
		List<String> l = new ArrayList<String>();
		// find the test in xls
		// find number of cols in test
		// number of rows in test
		// put the data in hashtable and put hashtable in object array
		// return object array
		//System.out.println(xls1.getRowCount(sheetName));

		for(int rNum=1;rNum<=xls1.getRowCount(sheetName)-1;rNum++){
			l.add(xls1.getCellData(sheetName, 0, rNum+1));
			//System.out.println(l.get(rNum-1));
		}
		return l;
	}
	 */
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Function to check whether test case can be executed or not.
     *
     * @testCase - Name of the testCase
     * @ExcelReader - ExcelReader object
     */
    public boolean isTestCaseExecutable(String testCase, ExcelReader xls) {
        /* iterate through the rows of Test Cases sheet from 2nd row till testCase
                name is equal to the value in TCID column.*/
        for (int rNum = 2; rNum <= xls.getRowCount("Test Cases"); rNum++) {
            //Checks whether testCase(passes value) name is equals to the value in TCID
            if (testCase.equals(xls.getCellData("Test Cases", "TCID", rNum))) {
                // check runmode is equals to Y/N. Returns true if Y else return false
                if (xls.getCellData("Test Cases", "Runmode", rNum).equalsIgnoreCase("Y"))
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    /**
     * Function to get start row number
     *
     * @param xls - ExcelReader Object
     * @param xls - row number
     */
    public int startRow(ExcelReader xls, int rowNum) {
        String testCaseStartRow = null;
        testCaseStartRow = xls.getCellData("Test Cases", 3, rowNum);
        int testCaseStartRowNum = Integer.parseInt(testCaseStartRow);
        return testCaseStartRowNum;
    }

    /**
     * Function to get End row number
     *
     * @param xls - ExcelReader Object
     * @param xls - row number
     */
    public int endRow(ExcelReader xls, int rowNum) {
        String testCaseEndRow = null;
        testCaseEndRow = xls.getCellData("Test Cases", 4, rowNum);
        int testCaseEndRowNum = Integer.parseInt(testCaseEndRow);
        return testCaseEndRowNum;
    }

	/*public static List<String> getClasses(String allTestsSheetName, String browser){
		List<String> classes = new ArrayList<String>();

		int startRow = 2;
		for(int i = startRow; i<xls.getRowCount(allTestsSheetName); i++){

			if((xls.getCellData(allTestsSheetName, "browserToExecute", i).contains(browser)) 
					&& (xls.getCellData(allTestsSheetName, "Runmode", i).equalsIgnoreCase("Y"))){

				String classname = xls.getCellData(allTestsSheetName, "classname", i);
				classes.add(classname);	
			}

		}

		return classes;
	}
	 */

	/*	public static List<String> getMethods(String allTestsSheetName, String className, String browser){
		List<String> methods = new ArrayList<String>();


		int startRow = 2;
		for(int i = startRow; i<xls.getRowCount(allTestsSheetName); i++){

			if((xls.getCellData(allTestsSheetName, "classname", i).equalsIgnoreCase(className)) 
					&& (xls.getCellData(allTestsSheetName, "Runmode", i).equalsIgnoreCase("Y") &&
							xls.getCellData(allTestsSheetName, "browserToExecute", i).equalsIgnoreCase(browser))){
				int methodNum = i+1;
				do{
					String methodName = xls.getCellData(allTestsSheetName, "testmethod", methodNum);
					if(!(methodName.isEmpty())&&(xls.getCellData(allTestsSheetName, "Runmode", methodNum).equalsIgnoreCase("Y"))){

						methods.add(methodName);
					}
					methodNum++;

				}while(!(xls.getCellData(allTestsSheetName, "testmethod", methodNum).isEmpty()));

			}

		}

		return methods;
	}*/


	/*public static String getuserrole(String allTestsSheetName, String className){

		String userrole = "";

		int startRow = 2;
		for(int i = startRow; i<xls.getRowCount(allTestsSheetName); i++){

			if((xls.getCellData(allTestsSheetName, "classname", i).equalsIgnoreCase(className))){
				userrole = xls.getCellData(allTestsSheetName, "userrole", i);		
			}

		}

		return userrole;
	}
	 */


	/*public static HashMap<String, String> getBrowserDetails(String browserConfigSheetName, String browser){
		HashMap<String, String> browserDetails = new HashMap<String, String>();

		int startRow = 1;
		for(int i = startRow; i<xls.getRowCount(browserConfigSheetName); i++){

			String browserShown = xls.getCellData(browserConfigSheetName, 0, i);
			if((!(browserShown.isEmpty()) && browserShown.equalsIgnoreCase(browser))){

				int colCount = xls.getColumnCount(browserConfigSheetName, i);
				for(int j = 0; j<colCount; j++){

					String key = xls.getCellData(browserConfigSheetName, j, i+1);
					//	System.out.println("key -" + key);
					String value = xls.getCellData(browserConfigSheetName, j, i+2);
					//	System.out.println("value -" + value);
					browserDetails.put(key, value);
				}

			}


		}

		return browserDetails;
	}
	 */
	/*public static String getBrowser() {

		String browser = null;
		try {

			String sheetName = "config";
			for (int rNum = 1; rNum <= xls.getRowCount(sheetName); rNum++) {
				if ("browser".equals(xls.getCellData(sheetName, 0, rNum))) {
					browser = xls.getCellData(sheetName, 1, rNum);
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return browser;
	}*/

	/*public static String getglobalUsername() {
		String browser = null;
		try {
			String sheetName = "config";
			for (int rNum = 1; rNum <= xls.getRowCount(sheetName); rNum++) {
				if ("userrole".equals(xls.getCellData(sheetName, 0, rNum))) {
					browser = xls.getCellData(sheetName, 1, rNum);
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return browser;
	}
	 */
	/*public static boolean isClassExecutable(String sheetName, int rNum) {
		boolean flag = false;

		try {
			if (xls.getCellData(sheetName, "Runmode", rNum).equalsIgnoreCase(
					"Y"))
				flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;

	}
	 */
	/*public static boolean isMethodExecutable(String sheetName, int rNum) {
		boolean flag = false;
		try {
			if (xls.getCellData(sheetName, "Runmode", rNum).equalsIgnoreCase(
					"Y"))
				flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}
	 */

    /******** :::::::: Code for Keyword Driven Framework:: End ::::::: ******/

    // returns the data from a cell
    public String getCellData(String sheetName, String colName, int rowNum) {
        try {
            if (rowNum <= 0)
                return "";
            //System.out.println("sheet Name"+sheetName);
            int index = workbook.getSheetIndex(sheetName);
            int col_Num = -1;
            if (index == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                //System.out.println(row.getCell(i).getStringCellValue().trim());
                if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num = i;
            }
            if (col_Num == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                return "";
            cell = row.getCell(col_Num);

            if (cell == null)
                return "";
            //System.out.println(cell.getCellType());
            if (cell.getCellType() == Cell.CELL_TYPE_STRING)
                return cell.getStringCellValue();
            else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

                String cellText = String.valueOf(cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    cellText =
                            (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
                            cal.get(Calendar.MONTH) + 1 + "/" +
                            cellText;

                }
                return cellText;
            } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());

        } catch (Exception e) {

            e.printStackTrace();
            return "row " + rowNum + " or column " + colName + " does not exist in xls";
        }
    }


    // returns number of columns in a sheet
    public int getColumnCount(String sheetName) {
        // check if sheet exists
        if (!isSheetExist(sheetName))
            return -1;

        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(0);

        if (row == null)
            return -1;

        return row.getLastCellNum();

    }

    public boolean isSheetExist(String sheetName) {
        int index = workbook.getSheetIndex(sheetName);
        if (index == -1) {
            index = workbook.getSheetIndex(sheetName.toUpperCase());
            if (index == -1)
                return false;
            else
                return true;
        } else
            return true;
    }

    // returns number of columns in a sheet
    public int getColumnCount(String sheetName, int rowNumber) {
        // check if sheet exists
        if (!isSheetExist(sheetName))
            return -1;

        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNumber);

        if (row == null)
            return -1;

        return row.getLastCellNum();

    }

    public int getCellRowNum(String sheetName, String colName, String cellValue) {

        for (int i = 2; i <= getRowCount(sheetName); i++) {
            if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
                return i;
            }
        }
        return -1;
    }

    // returns the data from a cell
    public String getCellData(String sheetName, int colNum, int rowNum) {
        try {
            if (rowNum <= 0)
                return "";

            int index = workbook.getSheetIndex(sheetName);

            if (index == -1)
                return "";

            sheet = workbook.getSheetAt(index);
            row = sheet.getRow(rowNum - 1);
            if (row == null)
                return "";
            cell = row.getCell(colNum);
            if (cell == null)
                return "";

            if (cell.getCellType() == Cell.CELL_TYPE_STRING)
                return cell.getStringCellValue();
            else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

                String cellText = String.valueOf(cell.getNumericCellValue());
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // format in form of M/D/YY
                    double d = cell.getNumericCellValue();

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(HSSFDateUtil.getJavaDate(d));
                    cellText =
                            (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
                    cellText = cal.get(Calendar.MONTH) + 1 + "/" +
                            cal.get(Calendar.DAY_OF_MONTH) + "/" +
                            cellText;

                }
                return cellText;
            } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
        } catch (Exception e) {

            e.printStackTrace();
            return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
        }
    }


}