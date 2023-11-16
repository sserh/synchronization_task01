package ru.raccoon;

import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    static final int NEEDEDNUMBER = 1000; //нужное по задаче количество строк

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < NEEDEDNUMBER; i++) {

            Thread thread = new Thread(() -> {
                String str = generateRoute("RLRFR", 100);
                int n = str.length() - str.replace("R", "").length(); //считаем количество символов R

                synchronized (sizeToFreq) {
                    //если размер уже есть, то увеличиваем значение
                    if (sizeToFreq.containsKey(n)) {
                        sizeToFreq.replace(n, sizeToFreq.get(n) + 1);
                    //если размера нет, то добавляем его
                    } else {
                        sizeToFreq.put(n, 1);
                    }
                }
                System.out.println(n);
            });
            thread.start(); // стартуем поток
            threadList.add(thread); // все потоки помещаем в список
        }

        for (Thread thread:
             threadList) {
            thread.join(); //приостанавливаем основной поток до завершения всех потоков из листа
        }

            System.out.println("----------------------------------------------------------"); //разделитель
            System.out.println("Самое частое количество повторений " + sizeToFreq.entrySet().stream()
                    .max(Map.Entry.<Integer, Integer>comparingByValue()).get().getKey() + " (встретилось " + sizeToFreq.entrySet().stream()
                    .max(Map.Entry.<Integer, Integer>comparingByValue()).get().getValue() + " раз)"); //выведем ключ и значение элемента с максимальным значением

            sizeToFreq.remove(sizeToFreq.entrySet().stream()
                    .max(Map.Entry.<Integer, Integer>comparingByValue()).get().getKey()); //удалим первый (с максимальным значением) элемент

            System.out.println("Другие размеры:");

            for (Map.Entry<Integer, Integer> entry:
                 sizeToFreq.entrySet()) {
                System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)"); // вывод остальных размеров (согласно примеру - сортировка их уже не нужна)
            }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}