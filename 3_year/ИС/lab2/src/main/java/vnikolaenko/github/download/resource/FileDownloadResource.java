package vnikolaenko.github.download.resource;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.multipart.FileUpload;
import vnikolaenko.github.download.application.FileParseService;
import vnikolaenko.github.download.event.DownloadCompletedEvent;
import vnikolaenko.github.download.event.DownloadFiledEvent;
import vnikolaenko.github.humanbeing.domen.HumanBeing;
import vnikolaenko.github.humanbeing.domen.HumanBeingRepository;
import vnikolaenko.github.humanbeing.infrastracture.mapper.HumanBeingMapper;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


@Path("/download")
public class FileDownloadResource {
    @Inject
    FileParseService fileParseService;
    @Inject
    JsonWebToken jwt;
    @Inject
    HumanBeingRepository humanBeingRepository;
    @Inject
    HumanBeingMapper humanBeingMapper;

    @Inject
    Event<DownloadCompletedEvent> downloadCompletedEventEvent;

    @Inject
    Event<DownloadFiledEvent> downloadFiledEventEvent;


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Response downloadFile(@FormParam("file") FileUpload fileUpload) {
        List<HumanBeing> data = null;
        boolean success = false;

        try {
            // путь, куда сохраняем файл
            java.nio.file.Path target = java.nio.file.Path.of("/tmp/uploads", fileUpload.fileName());

            // создаем директорию, если нет
            Files.createDirectories(target.getParent());

            // копируем загруженный файл
            Files.copy(fileUpload.uploadedFile().toAbsolutePath(), target, StandardCopyOption.REPLACE_EXISTING);

            data = fileParseService.parseCsvFile(target);
            humanBeingRepository.saveAll(
                    data.stream()
                            .map(humanBeingMapper::toEntity)
                            .toList());

            success = true;
            return Response.ok("Данные успешно загружены.").build();

        } catch (Exception e) {
            success = false;
            return Response.serverError().entity("Ошибка: " + e.getMessage()).build();

        } finally {
            // Отправляем события после завершения транзакции
            if (success) {
                downloadCompletedEventEvent.fireAsync(new DownloadCompletedEvent(jwt.getName(), data.size()));
            } else {
                downloadFiledEventEvent.fireAsync(new DownloadFiledEvent(jwt.getName()));
            }
        }
    }
}
