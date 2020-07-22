package com.ivs.util;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Iterator;


import static com.ivs.util.Constant.inputFolderName;

public class ExcelUtil {


    public static void main(String[] args)
    {


//        ExcelUtil rc=new ExcelUtil();   //object of the class
////
////        //reading the value of 2nd row and 2nd column
////        Object [][] arrInputParams = rc.GetParameters("D:\\New Folder\\InputParameters.xlsx","Input1",14);
//        Object [][] arrInputParams = rc.GetParameters("TransferToMyselfMobileInputParameters.xlsx","Input1",9);  //C:\Appium\TransferToMyselfMobileInputParameters.xlsx
//        arrInputParams[0][7] = "NOK";
//        //arrInputParams[0][6] = "11";
//        //arrInputParams[0][7] = "12";
//        arrInputParams[0][8] = "Napomena 1";
//        arrInputParams[3][7] = "OK";
//        //arrInputParams[3][6] = "110";
//        //arrInputParams[3][7] = "120";
//        arrInputParams[3][8] = "Napomena 4";
////
////        rc.SaveParametersOld("D:\\New Folder\\InputParameters.xlsx","Input1",arrInputParams);
//        rc.SaveParameters("TransferToMyselfMobileInputParameters.xlsx","Input1",arrInputParams,9,2,"android");
////        System.out.println(arrInputParams.toString());

    }

    public void SaveParameters(String fileName, String dataSheetName, Object [][] arrInputParams, int intColumns, int outputColumns, String mobileOS ){

        Workbook wb = null;           //initialize Workbook null
        int TotalRows = 0;
        int intRow = 1;         // ne čitamo nazive stupaca
        int startColumn;

        String fileExtension = ".xlsx";
        String inputFilePathAndLocation = ConstantA.inputFileFolder + fileName;

        int extensionLastIndex = fileName.lastIndexOf(fileExtension);
        //int backslashLastIndex = fileName.lastIndexOf('\\');
        //String pathToTheFile = fileName.substring(0, backslashLastIndex+1);
        String cleanFileName = fileName.substring(0,extensionLastIndex);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String dateTimeTimeStamp = sdf.format(timestamp);

        String destination = (ConstantA.outputFileFolder + cleanFileName + "-" + dateTimeTimeStamp + "-" + mobileOS + fileExtension);

        File sourceFile = new File(inputFilePathAndLocation);
        File destinationFile = new File(destination);

        try {
            FileUtils.copyFile(sourceFile, destinationFile);    //stvaramo kopiju datoteke u output folder koji se kreira ako ne postoji
            FileInputStream fis = new FileInputStream(sourceFile);
            wb = new XSSFWorkbook(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Sheet sheet = wb.getSheet(dataSheetName);

        startColumn = intColumns-outputColumns;       // -1 ako se makne browser element koji se dodaje na kraj

        TotalRows = sheet.getLastRowNum()-1;        //jer preskačemo 0. redak - nazive polja


        for (Object[] inputLine : arrInputParams) {
            Row row=sheet.getRow(intRow);
            //popunjavamo output parametre, ako je outputColumns=4 = status, broj autorizacije, referencu i napomenu
            for (int i = startColumn; i < intColumns; i++) {
                Cell cell = row.getCell(i);
                if (cell == null) {
                    cell = row.createCell(i);
                }
                cell.setCellValue(arrInputParams[intRow - 1][i].toString());
            }

            intRow++;
        }

        try{
            FileOutputStream outputStream = new FileOutputStream(destination);
            wb.write(outputStream);
            wb.close();
            outputStream.close();
        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }

    }

//    public void SaveParametersOld(String FileName, String DataSheetName, Object [][] arrInputParams){
//
//        Workbook wb = null;           //initialize Workbook null
//        int TotalRows = 0;
//        int intRow = 1;         // ne čitamo nazive stupaca
//        try {
//            FileInputStream fis = new FileInputStream(FileName);
//            wb = new XSSFWorkbook(fis);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//
//        Sheet sheet = wb.getSheet(DataSheetName);
//
//        TotalRows = sheet.getLastRowNum()-1;        //jer preskačemo 0. redak - nazive polja
//
//
//        for (Object[] inputLine : arrInputParams) {
//            Row row=sheet.getRow(intRow);
//            //popunjavamo status, broj autorizacije, referencu i napomenu
//            for (int i = 12; i < 16; i++) {
//                Cell cell = row.getCell(i);
//                if (cell == null) {
//                    cell = row.createCell(i);
//                }
//                cell.setCellValue(arrInputParams[intRow - 1][i].toString());
//                System.out.println(arrInputParams[intRow - 1][i].toString());
//            }
//
//            intRow++;
//        }
//
//        try{
//            FileOutputStream outputStream = new FileOutputStream(FileName);
//            wb.write(outputStream);
//            wb.close();
//            outputStream.close();
//        } catch (IOException | EncryptedDocumentException ex) {
//            ex.printStackTrace();
//        }
//
//    }


    //////////////////////////////////////////////////////////----------------////////////////////////////////////////

//    public void SaveCurrencyConversionParameters(String FileName, String DataSheetName, Object [][] arrInputParams){
//
//        Workbook wb = null;           //initialize Workbook null
//        int TotalRows = 0;
//        int intRow = 1;         // ne čitamo nazive stupaca
//        try {
//            FileInputStream fis = new FileInputStream(FileName);
//            wb = new XSSFWorkbook(fis);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//
//        Sheet sheet = wb.getSheet(DataSheetName);
//
//        TotalRows = sheet.getLastRowNum()-1;        //jer preskačemo 0. redak - nazive polja
//
//
//        for (Object[] inputLine : arrInputParams) {
//            Row row=sheet.getRow(intRow);
//            //popunjavamo status, broj autorizacije, referencu i napomenu
//            for (int i = 8; i < 12; i++) {
//                Cell cell = row.getCell(i);
//                if (cell == null) {
//                    cell = row.createCell(i);
//                }
//                cell.setCellValue(arrInputParams[intRow - 1][i].toString());
//            }
//
//            intRow++;
//        }
//
//        try{
//            FileOutputStream outputStream = new FileOutputStream(FileName);
//            wb.write(outputStream);
//            wb.close();
//            outputStream.close();
//        } catch (IOException | EncryptedDocumentException ex) {
//            ex.printStackTrace();
//        }
//
//    }

    ///////////////////////////////////////////////////////---------------------------///////////////////////////////////////////////



    public Object[][] GetParameters(String fileName, String DataSheetName, int intColumns) {
        Workbook wb = null;           //initialize Workbook null
        int TotalRows = 0;
        int intRow = 0;         // ne čitamo nazive stupaca
        try {
            FileInputStream fis =
                    new FileInputStream(System.getProperty("user.dir") + inputFolderName + fileName);
            wb = new XSSFWorkbook(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Sheet sheet = wb.getSheet(DataSheetName);

        TotalRows = sheet.getLastRowNum()-1;        //jer preskačemo 0. redak - nazive polja

        //x stupaca i TotalRows redaka
        Object[][] arrParams = new Object[TotalRows+1][intColumns];
        //for (int i = 0; i < TotalRows; i++) {
        //}

        Iterator<Row> itr = sheet.iterator();    //iterating over excel file

        itr.next();   //preskačemo nazive stupaca (0. red)

        while (itr.hasNext()) {
            Row row = itr.next();
            Cell cell;

            CellType cellType;
            for (int i = 0; i < intColumns; i++) {
                cell = row.getCell(i);

                if (cell == null) {
                    arrParams[intRow][i] = "";
                }else{
                    cellType = cell.getCellType();
                    if (cellType==CellType.STRING) {
                        arrParams[intRow][i] = cell.getStringCellValue();
                    } else if(cellType==CellType.BLANK){
                        arrParams[intRow][i] = "";
                    } else if(cellType==CellType.NUMERIC){
                        if (DateUtil.isCellDateFormatted(cell)) {
                            try {
                                LocalDate date = cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                                arrParams[intRow][i] = formatter.format(cell.getDateCellValue());

                            } catch (final NullPointerException e) {
                                arrParams[intRow][i] = String.valueOf(cell.getNumericCellValue());
                                String strValue = arrParams[intRow][i].toString();
                                strValue = strValue.replace(".",",");
                                arrParams[intRow][i] = strValue;
                            }
                        } else {
                            arrParams[intRow][i] = String.valueOf(cell.getNumericCellValue());
                            String strValue = arrParams[intRow][i].toString();
                            strValue = strValue.replace(".",",");
                            arrParams[intRow][i] = strValue;
                        }

                    } else{
                        //nepodražni tip podatka!
                        System.out.println("****************** nepodržani tip podataka! *********************************");
                    }
                }
            }

            intRow++;
        }

        return arrParams;
    }



//    public Object[][] GetCurrencyConversionParameters(String FileName, String DataSheetName, int intColumns) {
//        Workbook wb = null;           //initialize Workbook null
//        int TotalRows = 0;
//        int intRow = 0;         // ne čitamo nazive stupaca
//        try {
//            FileInputStream fis = new FileInputStream(FileName);
//            wb = new XSSFWorkbook(fis);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//
//        Sheet sheet = wb.getSheet(DataSheetName);
//
//        TotalRows = sheet.getLastRowNum()-1;        //jer preskačemo 0. redak - nazive polja
//
//        //x stupaca i TotalRows redaka
//        Object[][] arrParams = new Object[TotalRows+1][intColumns];
//        //for (int i = 0; i < TotalRows; i++) {
//        //}
//
//        Iterator<Row> itr = sheet.iterator();    //iterating over excel file
//
//        itr.next();   //preskačemo nazive stupaca (0. red)
//
//        while (itr.hasNext()) {
//            Row row = itr.next();
//
//            Cell cell = row.getCell(0); // Test koji je
//            arrParams[intRow][0] = cell.getStringCellValue();
//
//            cell = row.getCell(1); //Rola (Master/Operater)
//            arrParams[intRow][1] = cell.getStringCellValue();
//
//            cell = row.getCell(2); //Poslovni subjekt
//            arrParams[intRow][2] = cell.getStringCellValue();
//
//            cell = row.getCell(3);  //valuta platitelja
//            arrParams[intRow][3] = cell.getStringCellValue();
//
//            cell = row.getCell(4);  //IBAN platitelja
//            arrParams[intRow][4] = cell.getStringCellValue();
//
//            cell = row.getCell(5);  //valuta primatelja
//            arrParams[intRow][5] = cell.getStringCellValue();
//
//            cell = row.getCell(6);  //IBAN primatelja
//            arrParams[intRow][6] = cell.getStringCellValue();
//
//            cell = row.getCell(7);  //Iznos
//            arrParams[intRow][7] = String.valueOf(cell.getNumericCellValue());
//            String strValue = arrParams[intRow][7].toString();
//            strValue = strValue.replace(".",",");
//            arrParams[intRow][7] = strValue;
//
//
//            /*
//            cell = row.getCell(6);  //Datum plaćanja
//            //Date date = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
//            //String strDate = formatter.format(date);
//            arrParams[intRow][6] = formatter.format(cell.getDateCellValue());
//
//            for (int i = 7; i < intColumns; i++) {
//                cell = row.getCell(i);
//                if (cell != null) {
//                    arrParams[intRow][i] = cell.getStringCellValue();
//                } else {
//                    arrParams[intRow][i] = "";
//                }
//            }
//
//            */
//
////            }
//
//            intRow++;
//        }
//
//        return arrParams;
//    }





//    public String ReadCellData(int vRow, int vColumn){
//        String value=null;          //variable for storing the cell value
//        Workbook wb=null;           //initialize Workbook null
//        try
//        {
////reading data from a file in the form of bytes
//            FileInputStream fis=new FileInputStream("C:\\4Appium\\InputParameters.xlsx");
////constructs an XSSFWorkbook object, by buffering the whole stream into the memory
//            wb=new XSSFWorkbook(fis);
//        }
//        catch(FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//        catch(IOException e1)
//        {
//            e1.printStackTrace();
//        }
//        Sheet sheet=wb.getSheetAt(0);   //getting the XSSFSheet object at given index
//        Row row=sheet.getRow(vRow); //returns the logical row
//        Cell cell=row.getCell(vColumn); //getting the cell representing the given column
//        value=cell.getStringCellValue();    //getting cell value
//        return value;               //returns the cell value
//    }



    public Object[][] GetLoginValues(String FileName, String DataSheetName) {
        Workbook wb = null;           //initialize Workbook null
        int TotalRows = 0;
        int intRow = 0;         // ne čitamo nazive stupaca
        String filePathAndName = System.getProperty("user.dir") + inputFolderName + FileName;
        try {
            FileInputStream fis = new FileInputStream(filePathAndName);
            wb = new XSSFWorkbook(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Object[][] arrParams = new Object[1][3];

        Sheet sheet = wb.getSheet(DataSheetName);
        Iterator<Row> itr = sheet.iterator();    //iterating over excel file
        itr.next();   //preskačemo nazive stupaca (0. red)
        Row row = itr.next();

        Cell cell = row.getCell(0); //PIN
        if (cell == null) {
            arrParams[0][0] = "";}
        else {
            arrParams[0][0] = cell.getStringCellValue();
        }

        cell = row.getCell(1); //Appium local name
        if (cell == null) {
            arrParams[0][1] = "";}
        else {
            arrParams[0][1] = cell.getStringCellValue();
        }

        cell = row.getCell(2); //Appium server name
        if (cell == null) {
            arrParams[0][2] = "";}
        else {
            arrParams[0][2] = cell.getStringCellValue();
        }
//
//        cell = row.getCell(2); //OperaterUsername
//        arrParams[0][2] = cell.getStringCellValue();
//
//        cell = row.getCell(3); //OperaterUsername
//        arrParams[0][3] = cell.getStringCellValue();
//
//        cell = row.getCell(4); //Application
//        arrParams[0][4] = cell.getStringCellValue();

        return arrParams;
    }

}