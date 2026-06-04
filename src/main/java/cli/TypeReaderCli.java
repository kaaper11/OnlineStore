package cli;

import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.config.smartphone.Accessory;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;

import java.math.BigDecimal;
import java.util.Scanner;

public class TypeReaderCli {
    public static int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowa wartość, podaj liczbę całkowitą.");
            }
        }
    }

    public static BigDecimal readBigDecimal(Scanner scanner) {
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowa wartość, podaj liczbę.");
            }
        }
    }

    public static Long readLong(Scanner scanner) {
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowa wartość, podaj liczbę całkowitą.");
            }
        }
    }

    public static Processor readProcessor(Scanner scanner) {
        while (true) {
            try {
                return Processor.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Nieprawidłowa wartość, podaj procesor z podanych wyżej.");
            }
        }
    }

    public static Ram readRam(Scanner scanner) {
        while (true) {
            try {
                return Ram.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Nieprawidłowa wartość, podaj procesor z podanych wyżej.");
            }
        }
    }

    public static Rom readRom(Scanner scanner) {
        while (true) {
            try {
                return Rom.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Nieprawidłowa wartość, podaj procesor z podanych wyżej.");
            }
        }
    }

    public static GraphicCard readGraphicCard(Scanner scanner) {
        while (true) {
            try {
                return GraphicCard.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Nieprawidłowa wartość, podaj procesor z podanych wyżej.");
            }
        }
    }

    public static SmartphoneColorType readColor(Scanner scanner) {
        while (true) {
            try {
                return SmartphoneColorType.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Nieprawidłowa wartość, podaj procesor z podanych wyżej.");
            }
        }
    }

    public static Battery readBattery(Scanner scanner) {
        while (true) {
            try {
                return Battery.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Nieprawidłowa wartość, podaj procesor z podanych wyżej.");
            }
        }
    }

    public static Accessory readAccessory(Scanner scanner) {
        while (true) {
            try {
                return Accessory.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Nieprawidłowa wartość, podaj procesor z podanych wyżej.");
            }
        }
    }
}
