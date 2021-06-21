import interfaces.BaseActionsInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        //количество карт в колоде
        int numbOfCards = 12;
        //максимальное количество очков на карте
        int maxPointOnCard = 12;

        //инициализация интерфейса для работы с базовыми функциями приложения
        BaseActionsInterface baseActions = new BaseActions();

        //создание колод карт игроков
        ArrayList<Integer> userCards = new ArrayList<>();

        ArrayList<Integer> aiCards = new ArrayList<>();

        //для каждой карты задаем очки атаки/обороны
        List<Integer> dealtUsersCards = baseActions.dealOfCardsToPlayers(userCards, numbOfCards, maxPointOnCard);
        List<Integer> dealtAIsCards = baseActions.dealOfCardsToPlayers(aiCards, numbOfCards, maxPointOnCard);

        /*
        Сортировка необходима для более эффективной работы ИИ со списком.
        Используется модифицированный метод сортировки Слияния (n log (n)).
         */
        Collections.sort(dealtAIsCards);

        //передаем конструктору класса cardGame, количество карт в колоде, макс. кол-во очков на карте
        CardGame cardGame = new CardGame(numbOfCards);

        //Игра начинается! Вызываем метод для начала раунда
        cardGame.playersFight(dealtUsersCards, dealtAIsCards);
    }
}