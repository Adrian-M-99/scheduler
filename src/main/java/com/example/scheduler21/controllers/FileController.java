package com.example.scheduler21.controllers;

import com.example.scheduler21.entities.Department;
import com.example.scheduler21.entities.Doctor;
import com.example.scheduler21.entities.File;
import com.example.scheduler21.entities.Patient;
import com.example.scheduler21.services.DoctorService;
import com.example.scheduler21.services.FileService;
import com.example.scheduler21.services.PatientService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("files")
public class FileController {

    private FileService fileService;
    private DoctorService doctorService;
    private PatientService patientService;

    public FileController(FileService fileService, DoctorService doctorService, PatientService patientService) {
        this.fileService = fileService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @GetMapping
    public String displayAllFiles(Model model) {
        List<File> files = fileService.findAll();
        model.addAttribute("files", files);

        return "files/files";
    }


//    @PostMapping("/upload")
//    public String uploadFile(@RequestParam("file") File file) {
//        fileService.save(file);
//
//        return "redirect:/files";
//    }

    @GetMapping("/upload")
    public String displayUploadForm(Model model) {
        List<Doctor> doctors = doctorService.findAll();
        List<Patient> patients = patientService.findAll();

        model.addAttribute("doctors", doctors);
        model.addAttribute("patients", patients);

        return "files/upload";
    }

    @PostMapping("/save")
    public String saveFile(File file) {
        fileService.save(file);

        return "redirect:/files";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
        File file = fileService.findById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+file.getName()+"\"")
                .body(new ByteArrayResource(file.getData()));
    }

//    @GetMapping("/update")
//    public String displayUpdateFileForm(@RequestParam("id") Integer id, Model model){
//        File file = fileService.findById(id);
//
//        model.addAttribute("file", file);
//
//        return "doctors/update-file";
//    }
}
