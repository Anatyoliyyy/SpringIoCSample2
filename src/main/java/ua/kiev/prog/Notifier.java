package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Notifier {
    private LoggerAPI loggerAPI;

    @Autowired
    public Notifier(LoggerAPI loggerAPI) {
       this.loggerAPI = loggerAPI;
    }

    public void sendSms() {
        try {
            loggerAPI.log("Sending sms...");
            // emulate some job
            Thread.sleep(3000);
            // done
            loggerAPI.log("Done!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
