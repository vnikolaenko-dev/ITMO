package vnikolaenko.github.download.infrastracture;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import vnikolaenko.github.download.domain.Log;


@ApplicationScoped
public class LogRepository implements PanacheRepository<Log> {
    @Transactional
    public void save(Log log) {
        persist(log);
    }

    public PanacheQuery<Log> findByUsername(String username){
        return find("username", username);
    }
}
