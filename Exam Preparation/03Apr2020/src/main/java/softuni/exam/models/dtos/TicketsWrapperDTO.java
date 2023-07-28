package softuni.exam.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketsWrapperDTO {
    @XmlElement(name = "ticket")
    private List<TicketImportDTO> tickets;

    public TicketsWrapperDTO() {
    }

    public List<TicketImportDTO> getTickets() {
        return tickets;
    }

    public TicketsWrapperDTO setTickets(List<TicketImportDTO> tickets) {
        this.tickets = tickets;
        return this;
    }
}
