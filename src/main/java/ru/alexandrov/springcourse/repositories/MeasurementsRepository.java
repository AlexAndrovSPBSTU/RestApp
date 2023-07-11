package ru.alexandrov.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexandrov.springcourse.models.Measurement;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
}
