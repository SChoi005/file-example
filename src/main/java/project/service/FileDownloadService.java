package project.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.net.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import project.entity.User;
import project.repository.UserRepository;

@Service
@Slf4j
public class FileDownloadService{

    @Autowired
    UserRepository userRepository;
    
    public ResponseEntity<byte[]> userCsvDownload() throws IOException{
        List<User> users = userRepository.findAll();
        
        // header
        byte[] csvFile = null;
        String[] cols = {"사용자ID","이름","성","메일주소","전화번호","우편번호","주소"};
        CSVPrinter csvPrinter = null;
        StringWriter sw = new StringWriter();
        
        try{
            csvPrinter = new CSVPrinter(sw, CSVFormat.DEFAULT.withHeader(cols));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        
        // insert data        
        for(User user : users){
            List<String> data = Arrays.asList(
                String.valueOf(user.getId()),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getTel(),
                user.getZip(),
                user.getAddress()
            );
            csvPrinter.printRecord(data);
        }
        
        sw.flush();
        csvFile = sw.toString().getBytes("UTF-8");
        
        // csv return
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf("plain/text"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=testCSV.csv");
        header.setContentLength(csvFile.length);
        
        return new ResponseEntity<byte[]>(csvFile,header,HttpStatus.OK);
    }
    
    public ResponseEntity<byte[]> userXlsxDownload() throws IOException{
        
        List<User> users = userRepository.findAll();
        String[] column = {"사용자ID","이름","성","메일주소","전화번호","우편번호","주소"};
        
        try{
            
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("userExcelDownload");
            Row row;
            Cell cell;
            int rowNum = 0;

            int headerSize = column.length;
            // header style setting
            CellStyle headStyle = workbook.createCellStyle();
            headStyle.setBorderTop(BorderStyle.THICK);
            headStyle.setBorderBottom(BorderStyle.THICK);
            headStyle.setBorderLeft(BorderStyle.THICK);
            headStyle.setBorderRight(BorderStyle.THICK);
            headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headStyle.setAlignment(HorizontalAlignment.CENTER);
            // create header
            row = sheet.createRow(rowNum++);
            for(int i=0; i<headerSize; i++){
                cell = row.createCell(i);
                cell.setCellStyle(headStyle);
                cell.setCellValue(column[i]);
            }

            // create body
            for(User user: users){
                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(user.getId());
                dataRow.createCell(1).setCellValue(user.getFirstName());
                dataRow.createCell(2).setCellValue(user.getLastName());
                dataRow.createCell(3).setCellValue(user.getEmail());
                dataRow.createCell(4).setCellValue(user.getTel());
                dataRow.createCell(5).setCellValue(user.getZip());
                dataRow.createCell(6).setCellValue(user.getAddress());
            }

            // Making size of colum auto resize to fit with data
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);

            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            outputStream.close();
            
            // excel return
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.valueOf("application/vnd.ms-excel"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=testExcel.xlsx");
            
            return new ResponseEntity<byte[]>(outputStream.toByteArray(), header,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}