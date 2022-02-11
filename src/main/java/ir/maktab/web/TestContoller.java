package ir.maktab.web;

import com.google.gson.Gson;
import ir.maktab.service.ServiceServiceImpl;
import ir.maktab.service.SubServiceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@RequiredArgsConstructor
@Controller
public class TestContoller {

    private final ServiceServiceImpl serviceService;
    private final SubServiceServiceImpl subServiceService;

    @RequestMapping(value ="/loadService" ,method = RequestMethod.GET)
    public String getTest(ModelMap modelMap) {
        modelMap.put("services", serviceService.getServiceNames());
        return "test";
    }

    @ResponseBody
    @RequestMapping(value = "/loadService/{serviceName}", method = RequestMethod.GET)
    public String loadSubServiceByService(@PathVariable("serviceName") String serviceName) {
        Gson gson = new Gson();
        return gson.toJson(subServiceService.findSubServiceByServiceName(serviceName));
    }
}
