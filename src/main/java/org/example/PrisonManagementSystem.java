package org.example;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class PrisonManagementSystem {
    public static void main(String[] args) {
        PrisonSystem prisonSystem = new PrisonSystem();
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner);

        boolean continueProgram = true;
        while (continueProgram) {
            menu.displayMainMenu();
            int choice = menu.getChoice();
            menu.handleInput(choice, prisonSystem, scanner);

            System.out.println("Желаете продолжить? (да/нет)");
            String continueChoice = menu.getContinueChoice();
            if (!continueChoice.equalsIgnoreCase("да")) {
                continueProgram = false;
                prisonSystem.exitProgram();
            }
        }
    }
}

class Menu {
    private Log logger;
    private Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayMainMenu() {
        System.out.println("Программа управления тюрьмой");
        System.out.println("Выберите действие:");
        System.out.println("1. Зарегистрировать нового заключенного");
        System.out.println("2. Провести обыск");
        System.out.println("3. Выдать прием пищи");
        System.out.println("4. Рассчитать длительность наказания");
        System.out.println("5. Назначить работу заключенному");
        System.out.println("6. Перевести заключенного в другую камеру");
        System.out.println("7. Освободить заключенного");
        System.out.println("8. Провести допрос");
        System.out.println("9. Провести физические упражнения для заключенных");
        System.out.println("10. Посетить библиотеку для чтения книг");
        System.out.println("11. Провести консультацию для заключенных");
        System.out.println("12. Завершить программу");
    }
    public int getChoice() {
        return Integer.parseInt(scanner.nextLine());
    }

    public String getContinueChoice() {
        return scanner.nextLine();
    }

                                            //полиморфизм
    public void handleInput(int choice,  PrisonSystem prisonSystem, Scanner scanner) {
        switch (choice) {
            case 1:
                prisonSystem.registerPrisoner();
                break;
            case 2:
                PrisonSystem.conductRandomSearch();
                break;
            case 3:
                prisonSystem.provideMeal();
                break;
            case 4:
                PrisonSystem.calculateVariableSentenceDuration();
                break;
            case 5:
                PrisonSystem.assignWork();
                break;
            case 6:
                PrisonSystem.transferPrisonerToRandomCell();
                break;
            case 7:
                PrisonSystem.releasePrisoner();
                break;
            case 8:
                PrisonSystem.conductInterrogation();
                break;
            case 9:
                prisonSystem.conductExercise();
                break;
            case 10:
                prisonSystem.visitLibrary();
                break;
            case 11:
                prisonSystem.conductCounselingSession();
                break;
            case 12:
                PrisonSystem.exitProgram();
                break;
            default:
                PrisonSystem.printCustomMessage("Некорректный выбор.");
        }
    }
}
abstract class Person {
    protected String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
class Prisoner extends Person{
    static Log my_log;
    static {
        try {
            my_log = new Log("prisoner_logger");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int id;

    //конструктор
    public Prisoner(String name) {
        super(name);
        this.id = generateId();
        my_log.logger.info("Creating prisoner with name: " + name);
        my_log.logger.warning("Prisoner created with ID: " + id);
    }

    public int getId() {
        return id;
    }
    private int generateId() {
        return new Random().nextInt(1000);
    }
}
class Food {
    static Log my_log2;
    static {
        try {
            my_log2 = new Log("food_logger");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String name;

    //конструктор
    public Food(String name) {
        this.name = name;
        my_log2.logger.info("Creating food with name: " + name);
    }

    public String getName() {
        return name;
    }
}

class PrisonSystem {
    private Log logger;

    public PrisonSystem() {
        try {
            logger = new Log("prison_system_logger");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //конструктор
    private Scanner scanner = new Scanner(System.in);

    public void registerPrisoner() {
        try {
            System.out.println("Введите данные нового заключенного:");
            String input = scanner.nextLine();
            if (isValidName(input)) {
                Prisoner prisoner = new Prisoner(input);
                System.out.println("Заключенный " + prisoner.getName() + " зарегистрирован. ID: " + prisoner.getId());
            } else {
                System.out.println("Неверный ввод. Пожалуйста, введите имя заключенного.");
                registerPrisoner();
            }
            logger.logger.info("Registering prisoner...");
        } catch (Exception e) {
            logger.logger.severe("Error registering prisoner", e);
        }
    }

    private static boolean isValidName(String input) {
        return input.matches("[а-яА-Я\\s]+");
    }

    public void provideMeal() {
            System.out.println("Выберите прием пищи:");
            System.out.println("1. Завтрак");
            System.out.println("2. Обед");
            System.out.println("3. Ужин");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Выдаем завтрак:");
                    provideFood(new Food("Каша"));
                    System.out.println("Завтрак выдан.");
                    break;
                case 2:
                    System.out.println("Выдаем обед...");
                    provideFood(new Food("Суп"));
                    System.out.println("Обед выдан.");
                    break;
                case 3:
                    System.out.println("Выдаем ужин...");
                    provideFood(new Food("Чай"));
                    System.out.println("Ужин выдан.");
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }

    public static void assignWork() {
        System.out.println("Назначаем работу заключенному...");
        System.out.println("Работа назначена.");
    }

    public static void exitProgram() {
        System.out.println("Программа завершена.");
        System.exit(0);
    }

    public static void printCustomMessage(String message) {
        System.out.println(message);
    }

    public static void releasePrisoner() {
        System.out.println("Освобождаем заключенного...");
        System.out.println("Заключенный освобожден.");
    }

    public static void conductInterrogation() {
        System.out.println("Проводим допрос...");
        System.out.println("Допрос завершен.");
    }
    public void conductExercise() {
        System.out.println("Проводим физические упражнения для заключенных...");
        System.out.println("Упражнения завершены.");
    }

    public void visitLibrary() {
        System.out.println("Посещаем библиотеку для чтения книг...");
        System.out.println("Чтение завершено.");
    }

    public void conductCounselingSession() {
        System.out.println("Проводим консультацию для заключенных...");
        System.out.println("Консультация завершена.");
    }
    public static void conductRandomSearch() {
        Random random = new Random();
        boolean contrabandFound = random.nextBoolean();

        if (contrabandFound) {
            System.out.println("Контрабанда найдена!");
        } else {
            System.out.println("Контрабанды не найдено.");
        }
    }
                               //полиморфизм
        private void provideFood(Food food) {
            System.out.println("Выдаем: " + food.getName());
        }

    public static void calculateVariableSentenceDuration() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите длительность наказания (в днях):");
        String input = scanner.nextLine();
        if (isValidNumber(input)) {
            int baseDuration = Integer.parseInt(input);
            int variableDuration = baseDuration * 24;
            System.out.println("Длительность наказания: " + variableDuration + " часов.");
        } else {
            System.out.println("Неверный ввод. Пожалуйста, введите число.");
            calculateVariableSentenceDuration();
        }
    }

    private static boolean isValidNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void transferPrisonerToRandomCell() {
        System.out.println("Переводим заключенного в случайную камеру...");
        Random random = new Random();
        int randomCell = random.nextInt(10) + 1;
        System.out.println("Заключенный переведен в камеру " + randomCell + ".");
    }
}
