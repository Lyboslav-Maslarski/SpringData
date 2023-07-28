package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TaskImportDTO;
import softuni.exam.models.dto.TaskWrapperDTO;
import softuni.exam.models.entity.*;
import softuni.exam.repository.CarsRepository;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TasksServiceImpl implements TasksService {
    public static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";
    private final TasksRepository tasksRepository;
    private final CarsRepository carsRepository;
    private final MechanicsRepository mechanicsRepository;
    private final PartsRepository partsRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public TasksServiceImpl(TasksRepository tasksRepository, CarsRepository carsRepository,
                            MechanicsRepository mechanicsRepository, PartsRepository partsRepository,
                            XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.tasksRepository = tasksRepository;
        this.carsRepository = carsRepository;
        this.mechanicsRepository = mechanicsRepository;
        this.partsRepository = partsRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return tasksRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(Path.of(TASKS_FILE_PATH));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        TaskWrapperDTO taskWrapperDTO = xmlParser.parseXml(TaskWrapperDTO.class, TASKS_FILE_PATH);

        return taskWrapperDTO.getTasks()
                .stream()
                .map(this::importTask)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importTask(TaskImportDTO taskImportDTO) {
        if (!validationUtil.isValid(taskImportDTO)) {
            return "Invalid task";
        }

        Optional<Car> car = carsRepository.findById(taskImportDTO.getCar().getId());
        if (car.isEmpty()) {
            return "Invalid task";
        }

        Optional<Part> part = partsRepository.findById(taskImportDTO.getPart().getId());
        if (part.isEmpty()) {
            return "Invalid task";
        }

        Optional<Mechanic> mechanic = mechanicsRepository.findByFirstName(taskImportDTO.getMechanic().getFirstName());
        if (mechanic.isEmpty()) {
            return "Invalid task";
        }

        Task task = modelMapper.map(taskImportDTO, Task.class);
        task.setDate(LocalDateTime.parse(taskImportDTO.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        task.setCar(car.get());
        task.setPart(part.get());
        task.setMechanic(mechanic.get());
        tasksRepository.save(task);

        return String.format("Successfully imported task %.2f", task.getPrice());
    }

    @Override

    public String getCoupeCarTasksOrderByPrice() {
        return tasksRepository.findAllByCarCarTypeOrderByPriceDesc(CarType.coupe)
                .stream()
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
