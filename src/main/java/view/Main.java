package view;

import config.CustomerConfig;
import config.HomeServiceConfig;
import config.SpecialistConfig;
import dto.UserDto;
import model.entity.Customer;
import model.entity.HomeService;
import model.entity.Specialist;
import model.enums.UserType;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.CustomerService;
import service.HomeServiceService;
import service.SpecialistService;

import static view.GetValidData.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu(){

        AnnotationConfigApplicationContext specialistContext = new AnnotationConfigApplicationContext(SpecialistConfig.class);
        AnnotationConfigApplicationContext homeServiceContext = new AnnotationConfigApplicationContext(HomeServiceConfig.class);
        AnnotationConfigApplicationContext customerContext = new AnnotationConfigApplicationContext(CustomerConfig.class);
        SpecialistService specialistService = (SpecialistService) specialistContext.getBean("specialistService");
        HomeServiceService homeServiceService = (HomeServiceService) homeServiceContext.getBean("homeServiceService");
        CustomerService customerService = (CustomerService) customerContext.getBean("customerService");

        try {
            while (true){
                int choice = getChoiceUser();
                switch (choice){
                    case 1:{
                        saveUser(specialistService, customerService);
                    }break;
                    case 2:{
                        changeUserPassword(specialistService, customerService);
                    }break;
                    case 3:{
                        filterUserList(customerService,specialistService);
                    }break;
                    case 4:{
                        HomeService homeService = getValidHomeServiceInfo();
                        homeServiceService.save(homeService);
                        System.out.println("home service saved!");
                    }break;
                    case 5:{
                        addSpecialistToService(specialistService, homeServiceService);

                    }break;
                    case 6:{
                        removeSpecialistFromService(specialistService, homeServiceService);
                    }break;
                    case 7:{
                        System.exit(0);
                    }break;
                }
            }

        }catch (Exception e) {
            System.out.println(ConsoleColors.RED + e.getMessage() + ConsoleColors.RESET);
            mainMenu();
        }

    }

    private static void removeSpecialistFromService(SpecialistService specialistService, HomeServiceService homeServiceService) {
        HomeService homeService = getHomeService(homeServiceService);
        if(homeService!=null){
            homeService.getSpecialistList().stream().map(i -> i.getId()+") "+i.getName()+" "+i.getFamily()).forEach(System.out::println);
            int specialistId = getValidInt("enter your choice: ");
            Specialist specialist = specialistService.findSpecialistById(specialistId);
            if(specialist!=null){
                homeServiceService.removeSpecialist(homeService,specialist);
            }else throw new RuntimeException("Wrong choice: cannot find specialist!");
        }else throw new RuntimeException("Wrong choice: cannot find home service!");
    }

    private static void addSpecialistToService(SpecialistService specialistService, HomeServiceService homeServiceService) {
        HomeService homeService = getHomeService(homeServiceService);
        if(homeService!=null){
            specialistService.getAllSpecialists().stream().map(i -> i.getId()+") "+i.getName()+" "+i.getFamily()).forEach(System.out::println);
            int specialistId = getValidInt("enter your choice: ");
            Specialist specialist = specialistService.findSpecialistById(specialistId);
            if(specialist!=null){
                homeServiceService.addSpecialist(homeService,specialist);
            }else throw new RuntimeException("Wrong choice: cannot find specialist!");
        }else throw new RuntimeException("Wrong choice: cannot find home service!");
    }

    private static HomeService getHomeService(HomeServiceService homeServiceService) {
        homeServiceService.findAllHomeServices().stream().map(i -> i.getId()+") "+i.getService()+" "+i.getSubService()+" price: "+i.getPrice()).forEach(System.out::println);
        int homeServiceId = getValidInt("enter your choice: ");
        HomeService homeService = homeServiceService.findHomeServiceById(homeServiceId);
        return homeService;
    }

    private static int getChoiceUser() {
        int choice = GetValidData.getValidChoice("--------------------- main menu ---------------------" +
                "\n1)add new customer or specialist\n" +
                "2)change password\n" +
                "3)filter users list \n" +
                "4)add service \n" +
                "5)add specialist to service \n" +
                "6)remove specialist from service \n" +
                "7)exit \n" +
                "please enter your choice: ",7);
        return choice;
    }

    private static void changeUserPassword(SpecialistService specialistService, CustomerService customerService) {
        String email = getValidString("enter your email:");
        String newPass = getValidString("enter your new password: ");
        UserType userType = getValidUserType("enter c for customer or \n enter s for specialist:");
        if(userType.equals(UserType.CUSTOMER)){
            customerService.changePassword(email,newPass);
        }else if(userType.equals(UserType.SPECIALIST)){
           specialistService.changePassword(email,newPass);
        }
    }

    private static void saveUser(SpecialistService specialistService, CustomerService customerService) {
        UserType userType = getValidUserType("enter c for customer or \n enter s for specialist:");
        if(userType.equals(UserType.CUSTOMER)){
            Customer customer = getValidCustomerInfo();
            customerService.save(customer);
            System.out.println("customer saved!");
        }else if(userType.equals(UserType.SPECIALIST)){
            Specialist specialist = getValidSpecialistInfo();
            specialistService.save(specialist);
            System.out.println("specialist saved!");
        }
    }

    public static void filterUserList(CustomerService customerService, SpecialistService specialistService){

        String name = null;
        String family = null;
        String email = null;

        UserType userType = GetValidData.getValidUserType("enter c for customer or \n enter s for specialist:");

        boolean filterDate=getValidYesOrNo("do you want to filter by name ? (yes or no) ");
        if(filterDate){
            name=getValidName("enter name: ");
        }

        filterDate=getValidYesOrNo("do you want to filter by family name ? (yes or no) ");
        if(filterDate){
            family=getValidName("enter family name: ");
        }

        filterDate=getValidYesOrNo("do you want to filter by email ? (yes or no) ");
        if(filterDate){
            email=getValidString("enter email: ");
        }

        List<UserDto> userDtoList = new ArrayList<>();
        if(userType.equals(UserType.CUSTOMER)){
            userDtoList=customerService.filter(name,family,email);
        }else if(userType.equals(UserType.SPECIALIST)){
            userDtoList=specialistService.filter(name,family,email);
        }

        userDtoList.forEach(System.out::println);

    }

}
