package vnikolaenko.github.download.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vnikolaenko.github.download.domain.Log;
import vnikolaenko.github.download.infrastracture.LogRepository;

import java.util.List;

@ApplicationScoped
public class LogService {
    @Inject
    LogRepository logRepository;

    public void save(Log log) {
        logRepository.save(log);
    }

    public List<Log> getLogs() {
        return logRepository.listAll().reversed();
    }

    public List<Log> getDownloadsByUsername(String username) {
        return logRepository.findByUsername(username).list();
    }
}
