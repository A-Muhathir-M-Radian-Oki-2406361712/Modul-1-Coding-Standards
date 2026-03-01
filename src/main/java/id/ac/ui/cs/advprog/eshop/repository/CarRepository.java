package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class CarRepository {
    static int id = 0;

    private List<Car> carData = new ArrayList<>();

    public Car createCar(Car car) {
        if (car.getCarId() == null) {
            UUID uuid = UUID.randomUUID();
            car.setCarId(uuid.toString());
        }
        carData.add(car);
        return car;
    }

    public Iterator<Car> findAll() {
        return carData.iterator();
    }

    public Car findById(String id) {
        for (Car car : carData) {
            if (car.getCarId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    public Car update(Car car) {
        for (Car car1 : carData) {
            if (car1.getCarId().equals(car.getCarId())) {
                car1.setCarId(car.getCarId());
                car1.setCarName(car.getCarName());
                car1.setCarColor(car.getCarColor());
                car.setCarQuantity(car.getCarQuantity());
                return car;
            }
        }
        return null;
    }

    public void delete(String id) { carData.removeIf(car -> car.getCarId().equals(id)); }
}

