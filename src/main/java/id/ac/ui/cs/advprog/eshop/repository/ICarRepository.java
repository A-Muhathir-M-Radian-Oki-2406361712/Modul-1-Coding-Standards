package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.Iterator;

public interface ICarRepository {
    Iterator<Car> findAll();
    Car findById(String id);
    Car create(Car car);
    Car update(String id, Car car);
    void delete(String id);
}
