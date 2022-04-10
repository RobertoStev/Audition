package aud9.Audition;

import java.util.*;

class Participant {
    String city;
    String code;
    String name;
    int age;

    public Participant(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", code, name, age);
    }

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}

class Audition {
    Map<String, Set<Participant>> participantsByCity; //key - city, value - Set<Participant>

    public Audition() {
        participantsByCity = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age) {
        Participant p = new Participant(city, code, name, age);
        if (!participantsByCity.containsKey(city)) { //ako mapata nema kluc so toj grad
            participantsByCity.put(city, new HashSet<>()); //za toj grad dodadi prazno mnozestvo
            //                 O(1)
        }
        participantsByCity.get(city).add(p); //ke go zeme mnozestvoto i ke go dodade p
        //                            O(1) doavanje element vo HashSet

    }

    public void listByCity(String city) {
        Set<Participant> sortedSet = new TreeSet<>(Comparator.comparing(Participant::getName)
                .thenComparingInt(Participant::getAge)
                .thenComparing(Participant::getCode));//elementite mora da se comparable ili comparator
        //treba sporedba i spored kodot, pr: 003 Ivan 29 i 009 Ivan 29, ako nema sporedba spored kod, ke se smeta za duplikat element
        sortedSet.addAll(participantsByCity.get(city));
        //       O(logn) dodavanje elementi vo TreeSet

        sortedSet.stream().forEach(System.out::println); //toString() od Participant klasata
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}