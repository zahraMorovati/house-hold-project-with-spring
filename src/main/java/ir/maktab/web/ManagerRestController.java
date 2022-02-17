package ir.maktab.web;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SpecialistDto;
import ir.maktab.service.CustomerServiceImpl;
import ir.maktab.service.OrderServiceImpl;
import ir.maktab.service.SpecialistServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest")
public class ManagerRestController {

    private final CustomerServiceImpl customerService;
    private final SpecialistServiceImpl specialistService;
    private final OrderServiceImpl orderService;

    @GetMapping("/listCustomer")
    public List<CustomerDto> getListCustomer() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/listSpecialist")
    public List<SpecialistDto> getListSpecialist() {
        return specialistService.getAllSpecialists();
    }


    @GetMapping(value = "/advancedFilterCustomer")
    public List<CustomerDto> advancedFilterCustomer(@RequestParam(value = "name", required = false) String name,
                                                    @RequestParam(value = "family", required = false) String family,
                                                    @RequestParam(value = "email", required = false) String email,
                                                    @RequestParam(value = "startDate", required = false) String startDate,
                                                    @RequestParam(value = "endDate", required = false) String endDate,
                                                    @RequestParam(value = "minOrderNumber", required = false) Integer minOrderNumber,
                                                    @RequestParam(value = "maxOrderNumber", required = false) Integer maxOrderNumber) throws ParseException {


        return customerService.advancedFilterCustomers(name, family, email, startDate, endDate, minOrderNumber, maxOrderNumber);
    }

    @GetMapping(value = "/advancedFilterSpecialist")
    public List<SpecialistDto> advancedFilterSpecialist(@RequestParam(value = "name", required = false) String name,
                                                        @RequestParam(value = "family", required = false) String family,
                                                        @RequestParam(value = "email", required = false) String email,
                                                        @RequestParam(value = "startDate", required = false) String startDate,
                                                        @RequestParam(value = "endDate", required = false) String endDate,
                                                        @RequestParam(value = "minOrderNumber", required = false) Integer minOrderNumber,
                                                        @RequestParam(value = "maxOrderNumber", required = false) Integer maxOrderNumber) throws ParseException {

        return specialistService.advancedFilterSpecialists(name, family, email, startDate, endDate, minOrderNumber, maxOrderNumber);
    }

    @GetMapping("/advancedFilterOrders")
    public List<OrderDto> advancedFilterOrder(@RequestParam(value = "startDate", required = false) String startDate,
                                              @RequestParam(value = "endDate", required = false) String endDate,
                                              @RequestParam(value = "orderState", required = false) String orderState,
                                              @RequestParam(value = "service", required = false) String service,
                                              @RequestParam(value = "subService", required = false) String subService) {

        return orderService.orderAdvancedFilter(startDate, endDate, orderState, service, subService);

    }
}
