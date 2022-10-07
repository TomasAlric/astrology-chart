import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Scanner;
import java.util.Set;

public class Horoscope {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("Enter your birth date: ");
            String birthDate = scanner.nextLine();


            System.out.println("Enter your city of birth: ");
            String city = scanner.nextLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            TemporalAccessor parse = formatter.parse(birthDate);
            LocalDateTime from = LocalDateTime.from(parse);
            System.out.println();
            System.out.println("Date formatter: " + formatter.format(from));

            astrologyChart(from, city);

        } catch (DateTimeException e) {
            throw new RuntimeException("Type in is format dd/MM/yyyy HH:mm");
        }

    }

    private static String signo(LocalDate birthDate) {
        MonthDay date = MonthDay.of(birthDate.getMonth(), birthDate.getDayOfMonth());

        MonthDay virgoStartDate = MonthDay.of(Month.AUGUST, 23);
        MonthDay virgoEndDate = MonthDay.of(Month.SEPTEMBER, 22);

        MonthDay scorpionStartDate = MonthDay.of(Month.OCTOBER, 23);
        MonthDay scorpionEndDate = MonthDay.of(Month.NOVEMBER, 21);


        if (isWithinRange(date, virgoStartDate, virgoEndDate)) {
            return "Virgo";
        } else if (isWithinRange(date, scorpionStartDate, scorpionEndDate)) {
            return "Scorpio";
        }
        return "You do not have sign!!";
    }

    private static boolean isWithinRange(MonthDay birthDate, MonthDay startDate, MonthDay endDate) {
        return !(birthDate.isBefore(startDate) || birthDate.isAfter(endDate));
    }




    private static boolean isWithinRange(LocalTime birthTime, LocalTime startTime, LocalTime endTime) {
        return !(birthTime.isBefore(startTime) || (birthTime.isAfter(endTime)));
    }

    private static String ascending(String sign, LocalTime birthTime) {
        if ("Virgo".equalsIgnoreCase(sign)) {
            if (isWithinRange(birthTime, LocalTime.of(6, 31), LocalTime.of(8, 30))) {
                return "Libra";
            } else if (isWithinRange(birthTime, LocalTime.of(8, 31), LocalTime.of(10, 30))) {
                return "Scorpio";
            } else if (isWithinRange(birthTime, LocalTime.of(10, 31), LocalTime.of(12, 30))) {
                return "Sagittarius";

            }
        }
        if ("Scorpio".equalsIgnoreCase(sign)) {
            if (isWithinRange(birthTime, LocalTime.of(6, 31), LocalTime.of(8, 30))) {
                return "Sagittarius";
            } else if (isWithinRange(birthTime, LocalTime.of(8, 31), LocalTime.of(10, 30))) {
                return "Capricorn";
            } else if (isWithinRange(birthTime, LocalTime.of(10, 31), LocalTime.of(12, 30))) {
                return "Aquarius";
            }

        }

        return "You do not have ascendant";
    }

    private static void astrologyChart(LocalDateTime birthTime, String city) {
        Period age = Period.between(birthTime.toLocalDate(), LocalDate.now());
        System.out.println("Age: " + age.getYears());

        System.out.println("Leap year: " + birthTime.toLocalDate().isLeapYear());

        System.out.println("Sign: " + signo(birthTime.toLocalDate()));

        System.out.println("Ascending: " + ascending(signo(birthTime.toLocalDate()), birthTime.toLocalTime()));

        System.out.println("Moon sign: " + moonSign(birthTime.toLocalTime(), city));


    }

    private static String moonSign(LocalTime time, String zone) {
        Set<String> zones = ZoneId.getAvailableZoneIds();
        LocalDateTime localDateTime = LocalDateTime.now();

        for (String s : zones) {
            if (s.contains(zone)) {
                ZoneId zoneId = ZoneId.of(s);
                System.out.println(zoneId);
                ZoneOffset offset = zoneId.getRules().getOffset(localDateTime);
                System.out.println("Zone OffSet: " + offset);

            }
        }

        if ("America/Recife".contains(zone) && time.isAfter(LocalTime.of(12, 0))) {
            return "Casimiro";
        } else if ("America/Cuiaba".contains(zone) && time.isBefore(LocalTime.of(12, 0))) {
            return "Odin";
        } else if ("America/Sao_Paulo".contains(zone)) {
            return "Gandalf";
        } else {
            return "Dinossauro";
        }

    }
}