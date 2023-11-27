package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // Inicjalizacja generatora liczb losowych
        Random random = new Random();

        // Lista przechowująca wyniki losowań
        ArrayList<ArrayList<Integer>> losowania = new ArrayList<>();

        // Wygeneruj 1000 losowań
        for (int i = 0; i < 1000; i++) {
            ArrayList<Integer> losowanie = generujLosowanie(random);
            losowania.add(losowanie);
        }

        // Oblicz częstotliwość wystąpień poszczególnych liczb
        Map<Integer, Integer> czestotliwosc = obliczCzestotliwosc(losowania);

        // Zapisz wyniki do pliku
        zapiszWynikiDoPliku(losowania, czestotliwosc);
    }

    private static ArrayList<Integer> generujLosowanie(Random random) {
        ArrayList<Integer> losowanie = new ArrayList<>();

        // Generuj 7 unikalnych liczb z zakresu 1..43
        while (losowanie.size() < 7) {
            int liczba = random.nextInt(43) + 1;
            if (!losowanie.contains(liczba)) {
                losowanie.add(liczba);
            }
        }

        // Sortuj wygenerowane liczby
        Collections.sort(losowanie);

        return losowanie;
    }

    private static Map<Integer, Integer> obliczCzestotliwosc(ArrayList<ArrayList<Integer>> losowania) {
        Map<Integer, Integer> czestotliwosc = new HashMap<>();

        // Iteruj po wszystkich losowaniach
        for (ArrayList<Integer> losowanie : losowania) {
            // Iteruj po liczbach w danym losowaniu
            for (int liczba : losowanie) {
                // Aktualizuj częstotliwość dla danej liczby
                czestotliwosc.put(liczba, czestotliwosc.getOrDefault(liczba, 0) + 1);
            }
        }

        return czestotliwosc;
    }

    private static void zapiszWynikiDoPliku(ArrayList<ArrayList<Integer>> losowania, Map<Integer, Integer> czestotliwosc) {
        try (FileWriter writer = new FileWriter("wyniki_totolotka.txt")) {
            // Zapisz częstotliwość wystąpień do pliku
            writer.write("Częstotliwość wystąpień poszczególnych liczb (%):\n");
            for (Map.Entry<Integer, Integer> entry : czestotliwosc.entrySet()) {
                double procent = (double) entry.getValue() / 1000 * 100;
                writer.write(entry.getKey() + ": " + String.format("%.2f", procent) + "%\n");
            }

            // Zapisz wykaz poszczególnych losowań do pliku
            writer.write("\nWykaz poszczególnych losowań:\n");
            for (int i = 0; i < losowania.size(); i++) {
                writer.write("Losowanie " + (i + 1) + ": " + losowania.get(i) + "\n");
            }

            System.out.println("Wyniki zapisano do pliku wyniki_totolotka.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
