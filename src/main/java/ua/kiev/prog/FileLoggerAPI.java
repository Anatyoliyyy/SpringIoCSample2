package ua.kiev.prog;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class FileLoggerAPI extends LoggerAPI {
    private FileWriter fw;
    private String file;

    public FileLoggerAPI(String logFilePath) {
        this.file = logFilePath;
    }

    @Override
    @PostConstruct
    public void open() throws IOException {
        System.out.println("Open file");
        fw = new FileWriter(file);
    }

    @Override
    protected void doLog(String msg) throws IOException {
        System.out.println("Writing to file: " + msg);
        fw.write(msg + "\r\n");
    }

    @Override
    @PreDestroy
    public void close() {
        System.out.println("Close file");
        if (fw != null) {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
