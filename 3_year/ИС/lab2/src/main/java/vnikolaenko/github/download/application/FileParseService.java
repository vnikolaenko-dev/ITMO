package vnikolaenko.github.download.application;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vnikolaenko.github.car.domain.Car;
import vnikolaenko.github.car.infrastracture.entity.CarEntity;
import vnikolaenko.github.humanbeing.domen.HumanBeing;
import vnikolaenko.github.humanbeing.infrastracture.entity.HumanBeingEntity;
import vnikolaenko.github.humanbeing.valueobject.Coordinates;
import vnikolaenko.github.humanbeing.valueobject.Mood;
import vnikolaenko.github.humanbeing.valueobject.WeaponType;
import vnikolaenko.github.humanbeing.application.HumanBeingValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FileParseService {
    @Inject
    HumanBeingValidator humanBeingValidator;

    public List<HumanBeing> parseCsvFile(java.nio.file.Path filePath) throws IOException {
        List<HumanBeing> humanBeings = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            // Пропускаем заголовок, если он есть
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Создаем объект HumanBeing из данных CSV
                HumanBeing humanBeing = createHumanBeingFromCsv(values);
                humanBeingValidator.validateHumanBeing(humanBeing);
                System.out.println(humanBeing);
                humanBeings.add(humanBeing);
            }
        }

        return humanBeings;
    }

    private HumanBeing createHumanBeingFromCsv(String[] values) {
        try {
            HumanBeing humanBeing = new HumanBeing();

            // Парсим данные из CSV. Предполагаем следующий порядок колонок:
            // name, coordinateX, coordinateY, realHero, hasToothpick, carName, carCool, mood, impactSpeed, minutesOfWaiting, weaponType

            humanBeing.setName(values[0]);

            Coordinates coordinatesEntity = new Coordinates();
            coordinatesEntity.setX(Double.parseDouble(values[1]));
            coordinatesEntity.setY(Integer.parseInt(values[2]));
            humanBeing.setCoordinates(coordinatesEntity);

            humanBeing.setRealHero(Boolean.parseBoolean(values[3]));
            humanBeing.setHasToothpick(Boolean.parseBoolean(values[4]));

            Car car = new Car();
            car.setName(values[5]);
            car.setCool(Boolean.parseBoolean(values[6]));
            humanBeing.setCar(car);

            humanBeing.setMood(Mood.valueOf(values[7].toUpperCase()));
            humanBeing.setImpactSpeed(Float.parseFloat(values[8]));
            humanBeing.setMinutesOfWaiting(Integer.parseInt(values[9]));
            humanBeing.setWeaponType(WeaponType.valueOf(values[10].toUpperCase()));
            // creationDate устанавливается автоматически в конструкторе

            return humanBeing;

        } catch (Exception e) {
            System.err.println("Ошибка парсинга строки CSV: " + String.join(",", values));
            throw new RuntimeException("Ошибка парсинга строки CSV: " + String.join(",", values));
        }
    }
}
