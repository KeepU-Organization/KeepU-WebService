package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateTransactionRequest;
import com.keepu.webAPI.dto.response.TransferResponse;
import com.keepu.webAPI.dto.response.WalletResponse;
import com.keepu.webAPI.exception.InsufficientFundsException;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.WalletMapper;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.model.enums.TransactionType;
import com.keepu.webAPI.model.enums.UserType;
import com.keepu.webAPI.model.enums.WalletType;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WalletServiceUnitTest {

    @Mock
    private WalletRepository walletRepository;
    @Mock
    private UserRepository userRepository;
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

}
