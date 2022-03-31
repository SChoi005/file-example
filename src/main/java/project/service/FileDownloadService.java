package project.service;

import java.util.List;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.net.ObjectWriter;
import project.entity.User;
import project.repository.UserRepository;

@Service
public class FileDownloadService{

    @Autowired
    UserRepository userRepository;
    
    public ResponseEntity<byte[]> userCsvDownload(){
        List<User> users = userRepository.findAll();
        
        // header
        byte[] csvFile = null;
        String[] cols = {"사용자ID","이름","성","메일주소","전화번호","우편번호","주소"};
        
        return null;
    }
    
}