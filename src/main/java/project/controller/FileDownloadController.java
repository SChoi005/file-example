package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.entity.User;
import project.service.FileDownloadService;


@RestController
@RequestMapping("/download")
public class FileDownloadController{
    
    @Autowired
    FileDownloadService filedownLoadService;
    
    @GetMapping("/csv")
    public ResponseEntity<byte[]> getUserCsvFile(){
        return filedownLoadService.userCsvDownload();
    }
    
    
    //@GetMapping("/xlsx")
}