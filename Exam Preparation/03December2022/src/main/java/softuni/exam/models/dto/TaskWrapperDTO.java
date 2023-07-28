package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskWrapperDTO {
    @XmlElement(name = "task")
    private List<TaskImportDTO> tasks;

    public TaskWrapperDTO() {
    }

    public List<TaskImportDTO> getTasks() {
        return tasks;
    }

    public TaskWrapperDTO setTasks(List<TaskImportDTO> tasks) {
        this.tasks = tasks;
        return this;
    }
}
