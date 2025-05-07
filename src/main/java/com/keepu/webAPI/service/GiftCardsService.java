package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateGiftCardRequest;
import com.keepu.webAPI.dto.response.GiftCardResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.GiftCardsMapper;
import com.keepu.webAPI.model.GiftCards;
import com.keepu.webAPI.model.Stores;
import com.keepu.webAPI.repository.GiftCardsRepository;
import com.keepu.webAPI.repository.StoresRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GiftCardsService {

    private final GiftCardsRepository giftCardsRepository;
    private final StoresRepository storesRepository;
    private final GiftCardsMapper giftCardsMapper;

    @Transactional
    public GiftCardResponse createGiftCard(CreateGiftCardRequest request) {
        Stores store = storesRepository.findById(request.storeId())
                .orElseThrow(() -> new NotFoundException("Tienda no encontrada"));

        GiftCards giftCard = giftCardsMapper.toGiftCardEntity(request, store);
        GiftCards savedGiftCard = giftCardsRepository.save(giftCard);

        return giftCardsMapper.toGiftCardResponse(savedGiftCard);
    }

    @Transactional(readOnly = true)
    public GiftCardResponse getGiftCardById(Integer id) {
        GiftCards giftCard = giftCardsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gift card no encontrada"));
        return giftCardsMapper.toGiftCardResponse(giftCard);
    }
}