package controller;

import lombok.Getter;
import lombok.Setter;

import dao.ServiceDAO;
import models.Service;

@Getter
@Setter
public class ServiceController {
    private ServiceDAO serviceDAO;

    public ServiceController() {
        serviceDAO = new ServiceDAO();
    }

    public boolean deleteService(String serviceName) {
        serviceDAO.deleteService(serviceName);

        Service service = serviceDAO.getServiceByName(serviceName);

        return service == null;
    }
}
