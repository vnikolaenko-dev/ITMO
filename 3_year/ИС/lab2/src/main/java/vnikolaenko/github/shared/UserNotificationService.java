package vnikolaenko.github.shared;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import vnikolaenko.github.download.application.LogService;
import vnikolaenko.github.download.event.DownloadCompletedEvent;
import vnikolaenko.github.download.event.DownloadFiledEvent;
import vnikolaenko.github.download.domain.Log;

@Slf4j
@ApplicationScoped
public class UserNotificationService {
    @Inject
    LogService logService;

    public void createSuccessfulLog(@ObservesAsync DownloadCompletedEvent event) {
        System.out.println("Download completed");
        logService.save(new Log(true, event.getUsername(), event.getCount()));
    }

    public void createFailedLog(@ObservesAsync DownloadFiledEvent event) {
        System.out.println("Download failed");
        logService.save(new Log(false, event.getUsername(), 0));
    }
}
