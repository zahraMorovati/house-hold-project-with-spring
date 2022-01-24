package ir.maktab.web;

import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.Service;
import ir.maktab.service.ServiceServiceImpl;
import ir.maktab.service.SubServiceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/subService")
@Controller
public class SubServiceController {

    private final SubServiceServiceImpl subServiceService;
    private final ServiceServiceImpl serviceService;

    @RequestMapping("/new")
    public String newSubService(Map<String, Object> model) {

        SubServiceDto subServiceDto = new SubServiceDto();
        model.put("newSubServiceObject", subServiceDto);

        Map<String,String> serviceList = new HashMap<>();
        serviceService.getServiceNames().forEach(i -> serviceList.put(i.getName(), i.getName()));
        model.put("services",serviceList);

        return "saveSubServicePage";
    }

    @PostMapping(value = "/save")
    public String saveSubService(@ModelAttribute("newServiceObject") SubServiceDto subServiceDto) {

        Service service = serviceService.findByName(subServiceDto.getService().getServiceName());
        subServiceDto.setService(service);
        subServiceService.save(subServiceDto);
        return "redirect:/";
    }



}
