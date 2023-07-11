package ru.alexandrov.springcourse.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexandrov.springcourse.models.Sensor;
import ru.alexandrov.springcourse.repositories.SensorsRepository;

import java.util.Optional;

@Service
@Transactional
public class SensorsService {
    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public void persist(Sensor sensor) {
        sensorsRepository.save(sensor);
    }

    public Optional<Sensor> findByName(String name) {
        return sensorsRepository.findByName(name);
    }


}
