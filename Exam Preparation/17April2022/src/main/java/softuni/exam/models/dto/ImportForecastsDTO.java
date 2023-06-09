package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportForecastsDTO {
    @XmlElement(name = "forecast")
    private List<ImportForecastDTO> forecasts;

    public ImportForecastsDTO(List<ImportForecastDTO> forecasts) {
        this.forecasts = forecasts;
    }

    public ImportForecastsDTO() {
    }

    public List<ImportForecastDTO> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ImportForecastDTO> forecasts) {
        this.forecasts = forecasts;
    }
}
