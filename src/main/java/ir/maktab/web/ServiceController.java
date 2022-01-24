package ir.maktab.web;

import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.entity.Service;
import ir.maktab.service.ServiceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/service")
@Controller
public class ServiceController {

     ServiceServiceImpl serviceService;

     @Autowired
    public ServiceController(ServiceServiceImpl serviceService) {
        this.serviceService = serviceService;
    }

    @RequestMapping("/new")
    public String newService(Map<String, Object> model) {
        ServiceDto newService = new ServiceDto();
        model.put("newServiceObject", newService);
        return "saveServicePage";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveService(@ModelAttribute("newServiceObject") ServiceDto serviceDto) {
        serviceService.save(serviceDto);
        return "redirect:/";
    }


}
