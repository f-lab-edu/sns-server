package me.liiot.snsserver.service;

import me.liiot.snsserver.exception.FileUploadException;
import me.liiot.snsserver.model.FileInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface FileService {

    FileInfo uploadFile(MultipartFile file, String userId) throws FileUploadException;

    List<FileInfo> uploadFiles(List<MultipartFile> files, String userId) throws FileUploadException;

    void deleteFile(String filePath);

    void deleteDirectory(String userId);

}
