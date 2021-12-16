package com.example.scheduler21.services;

import com.example.scheduler21.entities.File;
import com.example.scheduler21.exceptions.FileNotFoundException;
import com.example.scheduler21.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public List<File> findAll() {
        return fileRepository.findAll();
    }

    public File findById(Integer id) {
        return fileRepository.findById(id).orElseThrow(FileNotFoundException::new);
    }

    public void deleteById(Integer id) {
        fileRepository.deleteById(id);
    }

    public void save(File file) {
        fileRepository.save(file);
    }
}
