package Exchange;

public class ExchangeClass {

    public static String convertBDTtoUSD(double amount) {
        amount = amount * 0.0091;
        return String.format("%.2f$", amount);
    }

    public static String convertUSDtoBDT(double amount) {
        amount = amount * 112.50;
        return String.format("%.2f", amount);
    }

    public static String convertBDTtoEUR(double amount) {
        amount = amount * 0.0086;
        return String.format("%.2f€", amount);
    }

    public static String convertEURtoBDT(double amount) {
        amount = amount * 116.80;
        return String.format("%.2f", amount);
    }
}
