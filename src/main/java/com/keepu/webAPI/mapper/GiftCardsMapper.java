package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateGiftCardRequest;
import com.keepu.webAPI.dto.response.GiftCardResponse;
import com.keepu.webAPI.model.GiftCards;
import com.keepu.webAPI.model.Stores;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class GiftCardsMapper {

    public GiftCardResponse toGiftCardResponse(GiftCards giftCard) {
        if (giftCard == null) {
            return null;
        }
        return new GiftCardResponse(
                giftCard.getId(),
                giftCard.getCode(),
                giftCard.getAmount(),
                giftCard.isRedeemed(),
                giftCard.getCreatedAt(),
                giftCard.getRedeemedAt(),
                giftCard.getStore().getId()
        );
    }

    public GiftCards toGiftCardEntity(CreateGiftCardRequest request, Stores store) {
        if (request == null || store == null) {
            return null;
        }

        GiftCards giftCard = new GiftCards();
        giftCard.setCode(request.code());
        giftCard.setAmount(request.amount());
        giftCard.setRedeemed(false);
        giftCard.setCreatedAt(LocalDateTime.now());
        giftCard.setStore(store);
        return giftCard;
    }
}