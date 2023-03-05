package domain;

import domain.card.Card;
import domain.card.Deck;
import domain.card.Number;
import domain.card.Shape;
import domain.player.Dealer;
import domain.player.Participants;
import domain.result.Result;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class GameResultTest {

    @Test
    @DisplayName("딜러와 플레이어가 버스트되지 않았을때")
    void createResults_thenClassifyParticipants() {
        //given
        final Participants participants = Participants.from(List.of("준팍", "파워", "범블비", "서브웨이"));
        final List<Number> numbers = List.of(Number.SIX, Number.SEVEN, Number.TWO, Number.THREE, Number.FOUR, Number.FIVE, Number.KING, Number.JACK);
        final Deck deck = Deck.from(TestCardGenerator.from(numbers));
        participants.drawCard(deck);

        final Dealer dealer = Dealer.create();
        dealer.takeCard(Card.of(Shape.CLUBS, Number.SIX));
        dealer.takeCard(Card.of(Shape.CLUBS, Number.SIX));

        //when
        Result result = participants.getResult(dealer);

        //then
        Assertions.assertThat(result.getLosers()).isEqualTo(List.of("파워", "범블비"));
    }

    @Test
    @DisplayName("딜러가 버스트되고, 플레이어 한명은 버스트 한명은 버스트되지 않았을때")
    void whenDealerBustedAndOnePlayerBusted() {
        //given
        final Participants participants = Participants.from(List.of("준팍", "파워"));
        final List<Number> numbers = List.of(Number.KING, Number.SEVEN, Number.TWO, Number.THREE, Number.SIX, Number.FIVE, Number.ACE, Number.ACE);
        final Deck deck = Deck.from(TestCardGenerator.from(numbers));
        participants.drawCard(deck);
        participants.drawCard(deck);

        final Dealer dealer = Dealer.create();
        dealer.takeCard(Card.of(Shape.CLUBS, Number.TEN));
        dealer.takeCard(Card.of(Shape.CLUBS, Number.TEN));
        dealer.takeCard(Card.of(Shape.CLUBS, Number.THREE));

        //when
        Result result = participants.getResult(dealer);

        //then
        Assertions.assertThat(result.getLosers()).isEqualTo(List.of("준팍"));
    }
}
