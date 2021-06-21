import interfaces.BaseActionsInterface;

import java.util.*;

/**
 * Класс представляющий данную игру в карты с ИИ.
 */
public class CardGame {
    //штрафные очки пользователя и ИИ
    private int userPenaltyNumber = 0;
    private int aiPenaltyNumber = 0;

    //текущий раунд
    private int round = 1;

    //максимальное количество раундов
    int maxNumbOfRound = 12;

    //количество карт в колоде
    private int numbOfCards;
    //максимальное еколичество очков на карте


    /**
     * @param numbOfCards - количество карт в колоде.
     * Конструктор класса.
     */
    public CardGame(int numbOfCards) {
        this.numbOfCards = numbOfCards;
    }


    /**
     * @param aiCards - колода карт ИИ.
     * @return - индекс и значение очков выбранной карты.
     *
     * Метод определяет выбор карты ИИ. Первую половину игры (6 раундов)
     * ИИ будет стараться защищаться "сильными" картами, а на атаку ставить "слабые".
     *
     * При этом стоить учесть, что ИИ может оставить сильные карты на запас. Запас
     * сильных карт регулируется в переменной aiStockOfStrongCards.
     *
     * С помощью этого запаса (Если запас != 0) будут осуществляться сильные атаки ИИ
     * во второй половине игры, а защита будет происходить с помощью карт "средней силы",
     * ведь все слабые ИИ "выкинул" из колоды еще в начале игры.
     */
    private List<Integer> aiChoice(List<Integer> aiCards) {
        int aiCardIdx;
        int aiCard;

        //запас сильных карт ИИ
        int aiStockOfStrongCards = 1;

        List<Integer> aiChoice = new ArrayList<>();

        if (round < maxNumbOfRound / 2) {
            if (round %2 != 0) {

                aiCardIdx = numbOfCards - aiStockOfStrongCards - 1;
            } else {
                aiCardIdx = 0;
            }
        } else {
            if (round %2 != 0) {
                aiCardIdx = 0;
            } else {
                aiCardIdx = numbOfCards - 1;
            }
        }

        aiCard = aiCards.get(aiCardIdx);

        aiChoice.add(aiCardIdx);
        aiChoice.add(aiCard);

        return aiChoice;
    }


    /**
     * @param userCards - карты пользователя.
     * @param aiCards - карты ИИ.
     * @throws InterruptedException - Исключение, которое возникает, когда поток или какое-либо действие прерывается.
     * В нашем случае таким действием является сон потока. Команды, отправляющие поток спать,
     * нужны, так как поток выполняется слишком быстро, пользователь теряет из-за этого фокус.
     *
     * Метод определяющий логику игры.
     */
    public void playersFight(List<Integer> userCards, List<Integer> aiCards) throws InterruptedException {
        BaseActionsInterface baseActions = new BaseActions();

        Scanner sc = new Scanner(System.in);

        //индекс карты, которую выбрал пользователь
        int userCardIdx;
        //индекс карты, которую выбрал ИИ
        int aiCardIdx;

        //очки карты пользователя
        int userCard;
        //очки карты ИИ
        int aiCard;

        //результат раунда
        int roundResult;

        //пока у игроков есть карты, продолжаем играть (это рекурсивный метод)
        if (!userCards.isEmpty() && !aiCards.isEmpty()){

            /*
            В первом раунде пользователь атакует ИИ, во втором он уже обронятеся и т.д,
            пока у игроков есть карты. Следовательно, при нечетном значении раунда
            пользователь всегда будет атаковать(round %2 != 0), а при четном защищаться (else).
             */
            System.out.println("\n-----------------------------------------------");
            if (round %2 != 0) {
                System.out.println("ROUND " + round + " ATTACK!💪");
            } else {
                System.out.println("ROUND " + round + " PROTECT!🍀");
            }
            System.out.println("-----------------------------------------------");

            //вывод карт пользователя
            System.out.println("\nYour cards: ");
            baseActions.showCard(userCards);

            //пользователь выбирает карту
            System.out.print("Choose your CARD NUMBER:");
            userCardIdx = sc.nextInt();

            /*
            Получаем очки карты пользователя по укзанному индексу.
            Индекс проходит валидацию.
             */
            if (userCardIdx <= numbOfCards && userCardIdx >= 1) {
                userCard = userCards.get(userCardIdx - 1);
            } else {
                System.out.println("Incorrect number!");
                return;
            }

            //ИИ выбирает карту
            aiCardIdx = aiChoice(aiCards).get(0);
            //Получаем очки карты ИИ по укзанному индексу
            aiCard = aiChoice(aiCards).get(1);

            //Вывод номера карты, которую выбрал пользователь
            Thread.sleep(1000);
            System.out.println("\nYour choices: card number "+ userCardIdx);

            //Вывод номера карты, которую выбрал ИИ
            int showAIChoice = aiCardIdx + 1;
            System.out.println("AI's choices: card number "+ showAIChoice);

            //Переворачиваем карты
            Thread.sleep(1000);
            System.out.println("\nFlip your cards upside down!");

            //Игроки узнают карты друг друга
            Thread.sleep(1000);
            System.out.println("\nYour card has the following points: " + userCard);
            System.out.println("AI's card has the following points: " + aiCard);

            /*
            Если пользователь атакует (round %2 != 0), то штрафные очки будут
            записаны ИИ. Возможно несколько ситуаций.
             */
            Thread.sleep(1000);
            if (round %2 != 0) {
                roundResult = userCard - aiCard;

                //Атака пользователя слишком мала либо равна очкам защиты ИИ
                if (roundResult < 0 || roundResult == 0) {
                    //Вывод сообщения, что пользователь не навредил ИИ
                    System.out.println("\nYou didn't damage the enemy!");

                } else {
                    //Атака пользователя превышает очки обороны ИИ, записываем полученную разницу в штрафные очки ИИ
                    aiPenaltyNumber += roundResult;
                    System.out.println("\nAI received the following number of penalty points: " + roundResult);
                }

                //Вывод общего урона, нанесенного ИИ
                System.out.println("ENEMY'S TOTAL DAMAGE:" + aiPenaltyNumber);
                //Такие же действия проводятся, если атакует ИИ
            } else {
                roundResult = aiCard - userCard;
                //Атака ИИ слишком мала либо равна очкам защиты пользователя
                if (roundResult < 0 || roundResult == 0) {
                    //Вывод сообщения, что ИИ не навредил пользователю
                    System.out.println("\nYou can relax, you have not been damaged!");
                } else {
                    //Атака ИИ превышает очки обороны пользователя, записываем полученную разницу в штрафные очки юзера
                    userPenaltyNumber += roundResult;
                    System.out.println("\nYou received the following number of penalty points: " + roundResult);
                }
                //Вывод общего урона, нанесенного пользователю
                System.out.println("YOUR TOTAL DAMAGE:" + userPenaltyNumber);
            }

            //Удаляем использованные карточки из колоды
            userCards.remove(userCardIdx - 1);
            aiCards.remove(aiCardIdx);

            //Уменьшаем допустимое количество карточек при их выборе
            numbOfCards--;

            //Наступает следующий раунд
            round++;

            baseActions.promptEnterKey();

            //переход к следующему раунду
            playersFight(userCards, aiCards);
        } else {
            //У игроков нет больше карт, сравниваем штрафные очки
            if (aiPenaltyNumber > userPenaltyNumber) {
                System.out.println("\nYou WIN!");
            } else if (aiPenaltyNumber == userPenaltyNumber){
                System.out.println("\nFriendship WON!");
            } else {
                System.out.println("\nYou lose!");
            }
            System.out.println("your total damage:" + userPenaltyNumber);
            System.out.println("enemy's total damage:" + aiPenaltyNumber);
        }
    }

}
