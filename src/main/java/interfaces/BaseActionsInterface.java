package interfaces;

import java.util.List;

public interface BaseActionsInterface {
    List<Integer> dealOfCardsToPlayers(List<Integer> deckOfCards, int numbOfCards, int maxPointOnCard);
    void showCard(List<Integer> cards);
    void promptEnterKey();
}
