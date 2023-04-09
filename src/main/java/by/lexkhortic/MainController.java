package by.lexkhortic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String startApp() {
        return "index";
    }

    @GetMapping("/checkFractionIsTrue")
    public String checkFractionIsTrue(
            @RequestParam(
                    name = "numerator",
                    required = false) String numerator,
            @RequestParam(
                    name = "denominator",
                    required = false) String denominator,
            Model model) {

        if (numerator != null && denominator != null) {
            int inputNumerator = Integer.parseInt(numerator);
            int inputDenominator = Integer.parseInt(denominator);
            if (inputNumerator < inputDenominator) {
                model.addAttribute("answer", "Дробь " + inputNumerator + "/" + inputDenominator + " правильная!");
            } else {
                model.addAttribute("answer", "Дробь " + inputNumerator + "/" + inputDenominator + " не правильная!");
            }
        } else {
            model.addAttribute("answer", "Введите дробь и нажмите проверить!");
        }

        return "fraction_check";
    }

    @GetMapping("/reduceFraction")
    public String reduceFraction(
            @RequestParam(
                    name = "numerator",
                    required = false) String numerator,
            @RequestParam(
                    name = "denominator",
                    required = false) String denominator,
            Model model) {

        if (numerator != null && denominator != null) {
            int inputNumerator = Integer.parseInt(numerator);
            int inputDenominator = Integer.parseInt(denominator);
            if (inputNumerator > inputDenominator) {
                model.addAttribute("answer", "Дробь " + inputNumerator + "/" + inputDenominator + " не правильная, дробь необходимо разделить!");
            } else {
                int NOD = findNOD(inputNumerator, inputDenominator);
                int newNumerator = inputNumerator / NOD;
                int newDenominator = inputDenominator / NOD;
                if (newNumerator == inputNumerator && newDenominator == inputDenominator) {
                    model.addAttribute("answer", "Дробь " + inputNumerator + "/" + inputDenominator + " несократимая.");
                } else {
                    model.addAttribute("answer", "Дробь " + inputNumerator + "/" + inputDenominator + " сокращается в дробь " +
                            newNumerator + "/" + newDenominator + ". НОД - " + NOD);
                }
            }
        } else {
            model.addAttribute("answer", "Введите дробь и нажмите сократить!");
        }

        return "fraction_reduce";
    }

    @GetMapping("/operationsFractions")
    public String operationsWithFractions(
            @RequestParam(
                    name = "numerator1",
                    required = false) String numerator1,
            @RequestParam(
                    name = "denominator1",
                    required = false) String denominator1,
            @RequestParam(
                    name = "numerator2",
                    required = false) String numerator2,
            @RequestParam(
                    name = "denominator2",
                    required = false) String denominator2,
            @RequestParam(
                    name = "operation",
                    required = false) String operation,
            Model model) {

        if (numerator1 != null && denominator1 != null && numerator2 != null && denominator2 != null && operation != null) {
            int n1 = Integer.parseInt(numerator1);
            int d1 = Integer.parseInt(denominator1);
            int n2 = Integer.parseInt(numerator2);
            int d2 = Integer.parseInt(denominator2);
            String fraction;
            switch (operation) {
                case "+" -> {
                    fraction = calcAddFractions(n1, d1, n2, d2);
                    model.addAttribute("answer",
                            "Сумма " + numerator1 + "/" + denominator1 + " и " + numerator2 + "/" + denominator2
                                    + " равна " + fraction + ".");
                }
                case "-" -> {
                    fraction = calcSubtractFractions(n1, d1, n2, d2);
                    model.addAttribute("answer",
                            "Разница " + numerator1 + "/" + denominator1 + " и " + numerator2 + "/" + denominator2
                                    + " равна " + fraction + ".");
                }
                case "*" -> {
                    fraction = calcMultiplyFractions(n1, d1, n2, d2);
                    model.addAttribute("answer",
                            "Произведение " + numerator1 + "/" + denominator1 + " и " + numerator2 + "/" + denominator2
                                    + " равно " + fraction + ".");
                }
                case "/" -> {
                    fraction = calcDivisionFractions(n1, d1, n2, d2);
                    model.addAttribute("answer",
                            "Деление " + numerator1 + "/" + denominator1 + " и " + numerator2 + "/" + denominator2
                                    + " равно " + fraction + ".");
                }
                default -> model.addAttribute("answer", "Введите значения дробей и выберите действие");
            }
        } else {
            model.addAttribute("answer", "Введите значения дробей и выберите действие");
        }

        return "fraction_operations";
    }

    public static int findNOD(int min, int max) {
        int NOD = max % min;

        while (NOD != 0) {
            max = min;
            if (max % NOD == 0) {
                break;
            } else {
                NOD = max % NOD;
            }
        }
        return NOD;
    }

    public static String calcAddFractions(int n1, int d1, int n2, int d2) {
        if(d1 == d2) {
            return (n1 + n2) + "/" + d1;
        } else {
            return (n1 * d2 + n2 * d1) + "/" + (d1 * d2);
        }
    }

    public static String calcSubtractFractions(int n1, int d1, int n2, int d2) {
        if(d1 == d2) {
            return (n1 - n2) + "/" + d1;
        } else {
            return (n1 * d2 - n2 * d1) + "/" + (d1 * d2);
        }
    }

    public static String calcMultiplyFractions(int n1, int d1, int n2, int d2) {
        return (n1 * n2) + "/" + (d1 * d2);
    }

    public static String calcDivisionFractions(int n1, int d1, int n2, int d2) {
        return (n1 * d2) + "/" + (d1 * n2);
    }

}
