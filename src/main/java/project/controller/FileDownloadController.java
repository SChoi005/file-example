package project.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.service.FileDownloadService;


@RestController
@RequestMapping("/download")
public class FileDownloadController{
    
    @Autowired
    FileDownloadService filedownLoadService;
    
    @GetMapping("/csv")
    public ResponseEntity<byte[]> getUserCsvFile() throws IOException{
        return filedownLoadService.userCsvDownload();
    }
    
    
    @GetMapping("/xlsx")
    public ResponseEntity<byte[]> getUserXlsxFile() throws IOException{
        return filedownLoadService.userXlsxDownload();
    }
}