package net.proselyte.web3;

import net.proselyte.web3.db.PointService;
import net.proselyte.web3.db.models.PointModel;
import org.primefaces.PrimeFaces;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@ManagedBean(name = "checkPointBean")
@ApplicationScoped
public class CheckPointBean implements Serializable {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private boolean isHit = false;
    private PointService pointService = new PointService();
    private ArrayList<PointModel> results = (ArrayList<PointModel>) pointService.findAllPoints();

    public ArrayList<PointModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<PointModel> results) {
        this.results = results;
    }

    public boolean getIsHit() {
        return isHit;
    }

    public void setIsHit(boolean hit) {
        isHit = hit;
    }

    public void check(PointBean pointBean) {
        long startTime = System.nanoTime();
        // RequestContext.getCurrentInstance().execute("clearCircles()");

        // Упрощение логики проверок
        isHit = pointHit(pointBean.getX(), pointBean.getY(), pointBean.getR());
        // pointBean.setResult(String.valueOf(isHit));
        PrimeFaces.current().executeScript("drawDuck(" + pointBean.getX() + "," + pointBean.getY() + "," + pointBean.getR() + "," + isHit + ")");

        // Создание результатов
        PointModel pointModel = new PointModel(pointBean.getX(), pointBean.getY(), pointBean.getR(), true, String.valueOf(System.nanoTime() - startTime), formatter.format(LocalDateTime.now()));
        results.add(pointModel);
        pointService.savePoint(pointModel);
    }

    public void checkGraph(String x, String y, String r) {
        long startTime = System.nanoTime();
        // RequestContext.getCurrentInstance().execute("clearCircles()");
        double xGr = Double.parseDouble(x);
        double yGr = Double.parseDouble(y);
        double rGr = Double.parseDouble(r);

        // Упрощение логики проверок
        isHit = pointHit(xGr, yGr, rGr);
        // pointBean.setResult(String.valueOf(isHit));
        PrimeFaces.current().executeScript("drawCircle(" + xGr + "," + yGr + "," + rGr + "," + isHit + ")");

        // Создание результатов
        PointModel pointModel = new PointModel(xGr, yGr, rGr, true, String.valueOf(System.nanoTime() - startTime), formatter.format(LocalDateTime.now()));
        results.add(pointModel);
        pointService.savePoint(pointModel);
    }

    public boolean pointHit(double x, double y, double r) {
        return isPointInQuarterCircle(x, y, r) || isPointInTriangle(x, y, r) ||
                (x >= 0 && x <= r) && (y >= 0 && y <= r / 2);
    }

    public boolean isPointInQuarterCircle(double x, double y, double r) {
        r = r / 2;
        double centerX = 0;
        double centerY = 0;
        // Проверяем, что точка находится в левой нижней четверти
        if (x <= centerX && y <= centerY) {
            // Вычисляем квадрат расстояния от точки до центра круга
            double distanceSquared = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
            // Проверяем, находится ли точка внутри или на границе круга
            return distanceSquared <= Math.pow(r, 2);
        }
        return false; // Если точка не в левой нижней четверти
    }


    public boolean isPointInTriangle(double x, double y, double r) {
        // Определяем вершины треугольника
        double[][] polygon = {
                {r, 0},   // Вершина A
                {0, 0},   // Вершина B
                {0, -r}   // Вершина C
        };

        int n = polygon.length;
        boolean inside = false;

        // Перебираем стороны треугольника
        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = polygon[i][0], yi = polygon[i][1]; // Текущая вершина
            double xj = polygon[j][0], yj = polygon[j][1]; // Предыдущая вершина

            // Проверяем пересечение с границей треугольника
            boolean intersect = ((yi > y) != (yj > y)) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi);
            if (intersect) {
                inside = !inside; // Меняем состояние внутри/снаружи
            }
        }

        return inside; // Возвращаем результат
    }

}
