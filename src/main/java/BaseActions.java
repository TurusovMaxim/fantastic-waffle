import interfaces.BaseActionsInterface;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BaseActions implements BaseActionsInterface {

    /**
     * @param deckOfCards - колода карт.
     * @param numbOfCards - количество карт в колоде.
     * @param maxPointOnCard - максимальное количество очков на карте.
     * @return - возвращает колоду карт с произвольно заданными очками для карт из
     * установленного диапозона.
     */
    @Override
    public List<Integer> dealOfCardsToPlayers(List<Integer> deckOfCards, int numbOfCards, int maxPointOnCard) {
        Random gen = new Random();

        for (int i = 0; i < numbOfCards; i++) {
            deckOfCards.add(gen.nextInt(maxPointOnCard));
        }

        return deckOfCards;
    }

    /**
     * @param cards - колода карт.
     * Метод предназначем для вывода колоды карт игрока.
     */
    @Override
    public void showCard(List<Integer> cards) {
        //все эти условия нужны для красивого и ровного вывода карт игрока в виде таблицы


        String longSeparator = " ---------------- ";
        String shortSeparator = " ----------------- ";
        int i = 0;
        for (int card : cards) {
            i++;
            if (i > 9) {
                if (card == 1) {
                    System.out.println("card number " + i + longSeparator + card + " point");
                } else {
                    System.out.println("card number " + i + longSeparator + card + " points");
                }
            } else {
                if (card == 1) {
                    System.out.println("card number " + i + shortSeparator + card + " point");
                } else {
                    System.out.println("card number " + i + shortSeparator + card + " points");
                }
            }
        }
        System.out.println();
    }

    /**
     * Метод ждем нажатия кнопки Enter от пользователя
     */
    @Override
    public void promptEnterKey() {
        System.out.println("\nPress \"ENTER\" to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
