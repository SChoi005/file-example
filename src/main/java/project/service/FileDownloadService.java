package project.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.net.ObjectWriter;
import project.entity.User;
import project.repository.UserRepository;

@Service
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
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=저장할 파일이름.csv");
        header.setContentLength(csvFile.length);
        
        return new ResponseEntity<byte[]>(csvFile,header,HttpStatus.OK);
    }
    
}