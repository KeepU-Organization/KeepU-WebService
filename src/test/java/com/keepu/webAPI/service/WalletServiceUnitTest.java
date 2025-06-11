package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateShopRequest;
import com.keepu.webAPI.dto.request.CreateTransactionRequest;
import com.keepu.webAPI.dto.response.ShopResponse;
import com.keepu.webAPI.dto.response.TransferResponse;
import com.keepu.webAPI.dto.response.WalletResponse;
import com.keepu.webAPI.exception.InsufficientFundsException;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.WalletMapper;
import com.keepu.webAPI.model.GiftCards;
import com.keepu.webAPI.model.Stores;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.model.enums.StoreType;
import com.keepu.webAPI.model.enums.TransactionType;
import com.keepu.webAPI.model.enums.UserType;
import com.keepu.webAPI.model.enums.WalletType;
import com.keepu.webAPI.repository.GiftCardsRepository;
import com.keepu.webAPI.repository.StoresRepository;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WalletServiceUnitTest {

    @Mock
    private WalletRepository walletRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GiftCardsRepository giftCardsRepository;
    @Mock
    private StoresRepository storesRepository;
    @Mock
    private WalletMapper walletMapper;
    @Mock
    private TransactionsService transactionsService;

    @InjectMocks
    private WalletService walletService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("CP10 - Transferencia de fondos entre billeteras")
    public void createTransfer_validData_shouldTransfer() {

        //Arrange
        Wallet senderWallet = new Wallet();
        Wallet receiverWallet = new Wallet();
        senderWallet.setId(1);
        receiverWallet.setId(2);
        senderWallet.setBalance(new BigDecimal("100.00"));
        receiverWallet.setBalance(new BigDecimal("50.00"));
        BigDecimal transferAmount = new BigDecimal("30.00");
        senderWallet.setWalletId("wallet-sender-123");
        receiverWallet.setWalletId("wallet-receiver-456");
        senderWallet.setWalletType(WalletType.PARENT);
        receiverWallet.setWalletType(WalletType.STANDARD);
        senderWallet.setUser(new User(1L, "user-sender", "User Sender","pfp.jpg", UserType.PARENT, "email@gmail.com", false, true));
        receiverWallet.setUser(new User(2L, "user-receiver", "User Receiver","pfp.jpg", UserType.CHILD, "child@gmail.com", false, true));

        CreateTransactionRequest request = new CreateTransactionRequest(
                senderWallet,
                transferAmount,
                "Transaccion exitosa",
                TransactionType.TRANSFER,
                null,null
        );

        WalletResponse senderWalletResponse = new WalletResponse(
                senderWallet.getId(),
                senderWallet.getWalletId(),
                senderWallet.getWalletType(),
                senderWallet.getBalance(),
                senderWallet.getUser().getId()
        );
        WalletResponse receiverWalletResponse = new WalletResponse(
                receiverWallet.getId(),
                receiverWallet.getWalletId(),
                receiverWallet.getWalletType(),
                receiverWallet.getBalance(),
                receiverWallet.getUser().getId()
        );


        when(walletRepository.findByWalletId(senderWallet.getWalletId()))
                .thenReturn(java.util.Optional.of(senderWallet));
        when(walletRepository.findByWalletId(receiverWallet.getWalletId()))
                .thenReturn(java.util.Optional.of(receiverWallet));

        when(walletRepository.save(senderWallet)).thenReturn(senderWallet);
        when(walletRepository.save(receiverWallet)).thenReturn(receiverWallet);

        when(walletMapper.toWalletResponse(senderWallet)).thenReturn(senderWalletResponse);
        when(walletMapper.toWalletResponse(receiverWallet)).thenReturn(receiverWalletResponse);

        doNothing().when(transactionsService).recordTransfer(request);

        //Act
        TransferResponse result = walletService.transfer(
                senderWallet.getWalletId(),
                receiverWallet.getWalletId(),
                transferAmount
        );
        //Assert
        assertNotNull(result);
        assertEquals(senderWalletResponse, result.senderWallet());
        assertEquals(receiverWalletResponse, result.receiverWallet());
        assertEquals(transferAmount, result.transactionAmount());

        assertEquals(new BigDecimal("70.00"), senderWallet.getBalance());
        assertEquals(new BigDecimal("80.00"), receiverWallet.getBalance());

        // Verificar que se haya guardado correctamente
        verify(walletRepository, times(1)).findByWalletId(senderWallet.getWalletId());
        verify(walletRepository, times(1)).findByWalletId(receiverWallet.getWalletId());
        verify(walletRepository, times(1)).save(senderWallet);
        verify(walletRepository, times(1)).save(receiverWallet);
    }
    @Test
    @DisplayName("CP11 - Transferencia de fondos entre billeteras con saldo negativo")
    public void createTransfer_negativeBalance_shouldThrowException() {

        //Arrange
        Wallet senderWallet = new Wallet();
        Wallet receiverWallet = new Wallet();
        senderWallet.setId(1);
        receiverWallet.setId(2);
        senderWallet.setBalance(new BigDecimal("100.00"));
        receiverWallet.setBalance(new BigDecimal("50.00"));
        BigDecimal transferAmount = new BigDecimal("-150.00");
        senderWallet.setWalletId("wallet-sender-123");
        receiverWallet.setWalletId("wallet-receiver-456");
        senderWallet.setWalletType(WalletType.PARENT);
        receiverWallet.setWalletType(WalletType.STANDARD);
        senderWallet.setUser(new User(1L, "user-sender", "User Sender", "pfp.jpg", UserType.PARENT, "email@gmail.com", false, true));
        receiverWallet.setUser(new User(2L, "user-receiver", "User Receiver", "pfp.jpg", UserType.CHILD, "child@gmail.com", false, true));

        assertThrows(IllegalArgumentException.class, () -> {
            walletService.transfer(
                    senderWallet.getWalletId(),
                    receiverWallet.getWalletId(),
                    transferAmount
            );
        });
    }
    @Test
    @DisplayName("CP12 - Transferencia de fondos entre billeteras con saldo no suficiente")
    public void createTransfer_insufficientBalance_shouldThrowException() {

        //Arrange
        Wallet senderWallet = new Wallet();
        Wallet receiverWallet = new Wallet();
        senderWallet.setId(1);
        receiverWallet.setId(2);
        senderWallet.setBalance(new BigDecimal("100.00"));
        receiverWallet.setBalance(new BigDecimal("50.00"));
        BigDecimal transferAmount = new BigDecimal("150.00");
        senderWallet.setWalletId("wallet-sender-123");
        receiverWallet.setWalletId("wallet-receiver-456");
        senderWallet.setWalletType(WalletType.PARENT);
        receiverWallet.setWalletType(WalletType.STANDARD);
        senderWallet.setUser(new User(1L, "user-sender", "User Sender", "pfp.jpg", UserType.PARENT, "email@gmail.com", false, true));
        receiverWallet.setUser(new User(2L, "user-receiver", "User Receiver", "pfp.jpg", UserType.CHILD, "child@gmail.com", false, true));

        when(walletRepository.findByWalletId(senderWallet.getWalletId()))
                .thenReturn(java.util.Optional.of(senderWallet));
        when(walletRepository.findByWalletId(receiverWallet.getWalletId()))
                .thenReturn(java.util.Optional.of(receiverWallet));

        assertThrows(InsufficientFundsException.class, () -> {walletService.transfer(
                senderWallet.getWalletId(),
                receiverWallet.getWalletId(),
                transferAmount
        );
        });
        }
    @Test
    @DisplayName("CP13 - Transferencia de fondos entre billeteras que no existen")
    public void createTransfer_walletNotFound_shouldThrowException() {

        //Arrange
        Wallet senderWallet = new Wallet();
        Wallet receiverWallet = new Wallet();
        senderWallet.setId(1);
        receiverWallet.setId(2);
        senderWallet.setBalance(new BigDecimal("100.00"));
        receiverWallet.setBalance(new BigDecimal("50.00"));
        BigDecimal transferAmount = new BigDecimal("150.00");
        senderWallet.setWalletId("wallet-sender-123");
        receiverWallet.setWalletId("wallet-receiver-456");
        senderWallet.setWalletType(WalletType.PARENT);
        receiverWallet.setWalletType(WalletType.STANDARD);
        senderWallet.setUser(new User(1L, "user-sender", "User Sender", "pfp.jpg", UserType.PARENT, "email@gmail.com", false, true));
        receiverWallet.setUser(new User(2L, "user-receiver", "User Receiver", "pfp.jpg", UserType.CHILD, "child@gmail.com", false, true));

        //Act & Assert
        assertThrows(NotFoundException.class, () -> {
            walletService.transfer(
                    senderWallet.getWalletId(),
                    receiverWallet.getWalletId(),
                    transferAmount
            );
        });
    }
    @Test
    @DisplayName("CP14 - Transferencia de fondos entre billeteras de tipo PARENT")
    public void createTransfer_toParentWallet_shouldThrowException() {

        //Arrange
        Wallet senderWallet = new Wallet();
        Wallet receiverWallet = new Wallet();
        senderWallet.setId(1);
        receiverWallet.setId(2);
        senderWallet.setBalance(new BigDecimal("100.00"));
        receiverWallet.setBalance(new BigDecimal("50.00"));
        BigDecimal transferAmount = new BigDecimal("30.00");
        senderWallet.setWalletId("wallet-sender-123");
        receiverWallet.setWalletId("wallet-receiver-456");
        senderWallet.setWalletType(WalletType.PARENT);
        receiverWallet.setWalletType(WalletType.PARENT);
        senderWallet.setUser(new User(1L, "user-sender", "User Sender", "pfp.jpg", UserType.PARENT, "email@gmail.com", false, true));
        receiverWallet.setUser(new User(2L, "user-receiver", "User Receiver", "pfp.jpg", UserType.PARENT, "email@gmail.com", false, true));

        when(walletRepository.findByWalletId(senderWallet.getWalletId()))
                .thenReturn(java.util.Optional.of(senderWallet));
        when(walletRepository.findByWalletId(receiverWallet.getWalletId()))
                .thenReturn(java.util.Optional.of(receiverWallet));

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            walletService.transfer(
                    senderWallet.getWalletId(),
                    receiverWallet.getWalletId(),
                    transferAmount
            );
        });
    }

    @DisplayName("CP20 - Se realiza la compra con éxito")
    @Test
    public void testCP20_purchaseGiftCardSuccessful() {
        // Arrange
        String walletId = "wallet-sender-123";
        Integer storeId = 1;
        Integer quantity = 2;
        BigDecimal amount = BigDecimal.valueOf(50.0);

        User user = new User(1L, "user-sender", "User Sender", "pfp.jpg", UserType.PARENT, "email@gmail.com", false, true);

        Wallet wallet = new Wallet();
        wallet.setWalletId(walletId);
        wallet.setBalance(BigDecimal.valueOf(200.0));
        wallet.setUser(user);
        wallet.setId(1);
        wallet.setWalletType(WalletType.STANDARD);

        Stores store = new Stores();
        store.setId(storeId);
        store.setName("Amazon");
        store.setLocation("Online");
        store.setActive(true);
        store.setType(StoreType.CLOTHING);
        store.setLink("https://amazon.com");

        GiftCards giftCard1 = new GiftCards();
        giftCard1.setId(1);
        giftCard1.setStore(store);
        giftCard1.setRedeemed(false);
        giftCard1.setAmount(amount);
        giftCard1.setCode("ABC123");

        GiftCards giftCard2 = new GiftCards();
        giftCard2.setId(2);
        giftCard2.setStore(store);
        giftCard2.setRedeemed(false);
        giftCard2.setAmount(amount);
        giftCard2.setCode("XYZ789");

        List<GiftCards> availableGiftCards = Arrays.asList(giftCard1, giftCard2);

        WalletResponse walletResponse = new WalletResponse(
                wallet.getId(),
                wallet.getWalletId(),
                wallet.getWalletType(),
                wallet.getBalance().subtract(amount), 
                user.getId()
        );

        when(walletRepository.findByWalletId(walletId)).thenReturn(Optional.of(wallet));
        when(storesRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(giftCardsRepository.findByStore(store)).thenReturn(availableGiftCards);
        when(walletMapper.toWalletResponse(any())).thenReturn(walletResponse);

        // Act
        ShopResponse result = walletService.purchaseGiftCard(walletId, storeId, quantity, amount);

        // Assert
        assertNotNull(result);
        assertEquals("Amazon", result.storeName());
        assertEquals(walletId, result.wallet().walletId());
        assertEquals(amount, result.amount()); /
        assertEquals(2, result.giftCards().size());
    }

    @DisplayName("CP21 - Cantidad negativa o nula de giftcards")
    @Test
    public void testCP21_purchaseGiftCardWithInvalidQuantity_shouldThrowException() {
        String walletId = "wallet123";
        Integer storeId = 1;
        Integer amountGiftCards = 0;
        BigDecimal amount = BigDecimal.valueOf(50);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            walletService.purchaseGiftCard(walletId, storeId, amountGiftCards, amount);
        });

        assertEquals("La cantidad de gift cards debe ser mayor que cero", exception.getMessage());
    }

    @DisplayName("CP22 - Saldo insuficiente")
    @Test
    public void testCP22_purchaseGiftCardWithInsufficientBalance_shouldThrowException() {
        String walletId = "wallet123";
        Integer storeId = 1;
        Integer amountGiftCards = 2;
        BigDecimal amount = BigDecimal.valueOf(80);

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(100));

        Stores store = new Stores();

        when(walletRepository.findByWalletId(walletId)).thenReturn(Optional.of(wallet));
        when(storesRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(giftCardsRepository.findByStore(store)).thenReturn(List.of(
                buildGiftCard(false), buildGiftCard(false)
        ));

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> {
            walletService.purchaseGiftCard(walletId, storeId, amountGiftCards, amount);
        });

        assertEquals("Saldo insuficiente para realizar la compra", exception.getMessage());
    }

    @DisplayName("CP23 - Giftcards no disponibles en la tienda")
    @Test
    public void testCP23_purchaseGiftCardWithNoAvailableGiftCards_shouldThrowException() {
        String walletId = "wallet123";
        Integer storeId = 1;
        Integer amountGiftCards = 3;
        BigDecimal amount = BigDecimal.valueOf(50);

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(200));

        Stores store = new Stores();

        // Solo hay 2 giftcards no redimidas pero se solicitan 3
        when(walletRepository.findByWalletId(walletId)).thenReturn(Optional.of(wallet));
        when(storesRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(giftCardsRepository.findByStore(store)).thenReturn(List.of(
                buildGiftCard(false), buildGiftCard(false)
        ));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            walletService.purchaseGiftCard(walletId, storeId, amountGiftCards, amount);
        });

        assertEquals("No hay gift cards disponibles para esta tienda", exception.getMessage());
    }

    // Método auxiliar para construir GiftCards
    private GiftCards buildGiftCard(boolean redeemed) {
        GiftCards giftCard = new GiftCards();
        giftCard.setCode(UUID.randomUUID().toString());
        giftCard.setAmount(BigDecimal.valueOf(50));
        giftCard.setRedeemed(redeemed);
        giftCard.setCreatedAt(LocalDateTime.now());
        return giftCard;
    }

}
