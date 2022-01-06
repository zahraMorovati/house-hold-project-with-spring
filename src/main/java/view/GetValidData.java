package view;

import model.entity.Customer;
import model.entity.HomeService;
import model.entity.Specialist;
import model.enums.HomeServiceTypes;
import model.enums.UserType;

import java.util.Date;
import java.util.Scanner;

import static view.ConsoleColors.RESET;
import static view.ConsoleColors.BLUE_BRIGHT;
import static view.ConsoleColors.RED;
public class GetValidData {


    public static Scanner input = new Scanner(System.in);

    public static String getValidName(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
        String name = input.next();
        if (name.matches("[a-zA-Z]*")) {
            return name;
        } else {
            System.out.println(RED + "invalid input!" + RESET);
            return getValidName(message);
        }
    }

    public static String getValidUsername(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
        String username = input.next();
        if (username.matches("^[a-z0-9_-]{3,15}$")) {
            return username;
        } else {
            System.out.println(RED + "invalid username!" + RESET);
            return getValidName(message);
        }
    }

    public static String getValidString(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
        String str = input.next();
        return str;
    }

    public static String getValidFullName(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
        String str = input.nextLine();
        return str;
    }

    public static Date getValidDate(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
        try {
            Date date = new Date(input.nextInt(), input.nextInt(), input.nextInt());
            return date;
        } catch (Exception e) {
            System.out.println(RED + "invalid Date!" + RESET);
            return getValidDate(message);
        }
    }


    public static String getValidPhoneNumber(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
        String phoneNumber = input.next();
        String str = phoneNumber.substring(0);
        if (isNumeric(str)) {
            return phoneNumber;
        } else {
            System.out.println(RED + "invalid phone number!" + RESET);
            return getValidPhoneNumber(message);
        }
    }

    public static int getValidInt(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
        String str = input.next();
        if (isNumeric(str)) {
            return Integer.parseInt(str);
        } else {
            System.out.println(RED + "invalid input!" + RESET);
            return getValidInt(message);
        }
    }

    public static double getValidDouble(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
        String str = input.next();
        if (isNumeric(str)) {
            return Double.parseDouble(str);
        } else {
            System.out.println(RED + "invalid input!" + RESET);
            return getValidDouble(message);
        }
    }

    public static int getValidChoice(String message, int maxChoice) {
        int number = getValidInt(message);
        for (int i = 1; i < maxChoice + 1; i++) {
            if (i == number) {
                return number;
            }
        }
        System.out.println(RED + "invalid choice!" + RESET);
        return getValidChoice(message, maxChoice);
    }

    public static boolean isNumeric(String str) {

        if (str == null || str.length() == 0) {
            return false;
        }

        try {
            Double.parseDouble(str);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }

    }

    public static boolean getValidYesOrNo(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
        String str = input.next();
        if (str.equalsIgnoreCase("yes")) {
            return true;
        } else if (str.equalsIgnoreCase("no")) {
            return false;
        } else {
            System.out.println(RED + "invalid input!" + RESET);
            return getValidYesOrNo(message);
        }
    }

    public static UserType getValidUserType(String message) {
        System.out.print(BLUE_BRIGHT + message + RESET);
        String str = input.next();
        if (str.equalsIgnoreCase("c")) {
            return UserType.CUSTOMER;
        } else if (str.equalsIgnoreCase("s")) {
            return UserType.SPECIALIST;
        } else {
            System.out.println(RED + "invalid input!" + RESET);
            return getValidUserType(message);
        }
    }

    public static Customer getValidCustomerInfo() {
        String name = getValidName("name: ");
        String family = getValidName("family: ");
        String email = getValidString("email: ");
        String password = getValidString("password: ");
        double balance = getValidDouble("balance: ");

        Customer customer = Customer.CustomerBuilder.aCustomer()
                .setName(name).setFamily(family).setEmail(email)
                .setPassword(password).setBalance(balance).build();
        return customer;
    }

    public static Specialist getValidSpecialistInfo() {
        String name = getValidName("name: ");
        String family = getValidName("family: ");
        String email = getValidString("email: ");
        String password = getValidString("password: ");
        double balance = getValidDouble("balance: ");

        Specialist specialist = Specialist.SpecialistBuilder.aSpecialist()
                .setName(name).setFamily(family).setEmail(email)
                .setPassword(password).setBalance(balance).build();
        return specialist;
    }

    public static HomeServiceTypes getValidHomeServiceType() {
        int choice = getValidChoice("1-CLEANING\n 2-REPAIR_APPLIANCE\n" +
                "    3-BUILDINGS_DECORATION\n 4-BUILDINGS_FACILITIES\n 5-MOVING \n enter your choice: ", 5);
        switch (choice) {
            case 1:
                return HomeServiceTypes.CLEANING;
            case 2:
                return HomeServiceTypes.REPAIR_APPLIANCE;
            case 3:
                return HomeServiceTypes.BUILDINGS_DECORATION;
            case 4:
                return HomeServiceTypes.BUILDINGS_FACILITIES;
            case 5:
                return HomeServiceTypes.MOVING;
            default:
                return null;
        }
    }

    public static HomeService getValidHomeServiceInfo() {
        HomeServiceTypes service = getValidHomeServiceType();
        String subService = getValidString("sub service:");
        double price = getValidDouble("price");
        String explanations = getValidString("explanations: ");

        HomeService homeService = HomeService.builder()
                .setService(service).setSubService(subService)
                .setPrice(price).setExplanations(explanations).build();

        return homeService;
    }


}
