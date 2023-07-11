package ru.alexandrov.springcourse.controllesrs;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alexandrov.springcourse.dto.MeasurementDTO;
import ru.alexandrov.springcourse.models.Measurement;
import ru.alexandrov.springcourse.services.MeasurementsService;
import ru.alexandrov.springcourse.util.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, MeasurementValidator measurementValidator,
                                  ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        System.out.println(measurementDTO);
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        System.out.println(measurement);
        measurementValidator.validate(measurement, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append(";\n");
            }
            throw new MeasurementHasNotBeenAddedException(errors.toString());
        }
        measurementsService.persist(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<Measurement> getMeasurements() {
        return measurementsService.findAll();
    }

    @GetMapping("/rainyDaysCount")
    public long getRainyDaysCount() {
        return measurementsService.findAll().stream().filter(Measurement::getRaining).count();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleSensorHasNotBeenRegisteredException(MeasurementHasNotBeenAddedException exception) {
        ErrorResponse response = new ErrorResponse(
                exception.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
