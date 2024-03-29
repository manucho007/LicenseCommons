package ru.rtksoftlabs.LicenseCommons.services.impl;

import ru.rtksoftlabs.LicenseCommons.services.FileService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileServiceImpl implements FileService {
    @Override
    public void save(byte[] content, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Files.write(path, content);
    }

    public byte[] load(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readAllBytes(path);
    }

    public FileInputStream loadInputStream(String fileName) throws FileNotFoundException {
        return new FileInputStream(fileName);
    }

    public FileOutputStream saveOutputStream(String fileName) throws FileNotFoundException {
        return new FileOutputStream(fileName);
    }
}
