package modern.java.study.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StreamTest {

    List<Transaction> transactions;

    @BeforeEach
    public void beforeEach(){
       Trader raoul = new Trader("Raoul", "Cambridge");
       Trader mario = new Trader("Mario", "Milan");
       Trader alan = new Trader("Alan", "Cambridge");
       Trader brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
         new Transaction(brian, 2011, 300),
         new Transaction(raoul, 2012, 1000),
         new Transaction(raoul, 2011, 400),
         new Transaction(mario, 2012, 710),
         new Transaction(mario, 2012, 700),
         new Transaction(alan, 2012, 950)
       );
    }

    static class Trader {

        private final String name;
        private final String city;

        public Trader(String n, String c) {
            this.name = n;
            this.city = c;
        }

        public String getName() {
            return this.name;
        }

        public String getCity() {
            return this.city;
        }

        @Override
        public String toString() {
            return "Trader{" +
                    "name='" + name + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }

    static class Transaction {

        private final Trader trader;
        private final int year;
        private final int value;

        public Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return this.trader;
        }

        public int getYear() {
            return this.year;
        }

        public int getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "trader=" + trader +
                    ", year=" + year +
                    ", value=" + value +
                    '}';
        }
    }

    @Test
    void test1() {
        // 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.
        List<Transaction> result = transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        System.out.println(result);
    }

    @Test
    void test2() {
        // 거래자가 일하는 모든 도시를 중복없이 나열하시오.
        List<String> cities = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .toList();

        assertEquals(2, cities.size());
    }

    @Test
    void test3() {
        // 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.
        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .distinct()
                .filter(t -> t.getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::getName))
                .toList();

        assertEquals("Alan", traders.get(0).getName());
        assertEquals("Brian", traders.get(1).getName());
        assertEquals("Raoul", traders.get(2).getName());

        assertEquals(3, traders.size());
    }

    @Test
    void test4() {
        // 모든 거래자의 이름을 알파벳순으로 정렬하시오.
        List<String> result = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted()
                .toList();

        assertEquals(List.of("Alan", "Brian", "Mario", "Raoul"), result);
    }
}
