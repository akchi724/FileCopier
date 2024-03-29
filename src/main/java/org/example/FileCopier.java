package org.example;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileCopier {

    private final static String ITERATOR = "%iterator%";

    public static void main(String[] args) {
        String sourceFolderPath = "/Users/akchi724/Work/ПВВ/Гостиниц тест/исходники";
        String destinationFolderPath = "/Users/akchi724/Work/ПВВ/Гостиниц тест/копии";
        int numCopies = 600;

        try {
            List<String> files = listFiles(sourceFolderPath);
            for (String file : files) {
                copyFiles(file, destinationFolderPath, numCopies);
            }
            System.out.println("Копии файлов успешно созданы.");
        } catch (IOException e) {
            System.err.println("Ошибка при копировании файлов: " + e.getMessage());
        }
    }

    public static void copyFiles(String sourceFilePath, String destinationFolderPath, int numCopies) throws IOException {
        Path sourcePath = Paths.get(sourceFilePath);
        Path destinationPath = Paths.get(destinationFolderPath);

        String fileNameTemplate = getFileNameTemplate(sourcePath);
        for (int i = 1; i <= numCopies; i++) {
            String fileName = fileNameTemplate.replace(ITERATOR, Integer.toString(i));
            Path destinationFile = destinationPath.resolve(fileName);
            Files.copy(sourcePath, destinationFile);
        }
    }

    public static List<String> listFiles(String directoryPath) throws IOException {
        Path dir = Paths.get(directoryPath);

        return Files.walk(dir)
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    public static String getFileNameTemplate(Path filePath) {
        String fileName = filePath.getFileName().toString();
        String extension = "";

        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1);
            fileName = fileName.substring(0, dotIndex);
        }
        return fileName + "(" + ITERATOR + ")." + extension;
    }
}
