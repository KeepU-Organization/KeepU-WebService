package com.keepu.webAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.keepu.webAPI.dto.request.CreateShopRequest;
import com.keepu.webAPI.dto.request.CreateWalletRequest;
import com.keepu.webAPI.dto.request.CreateTransferRequest;
import com.keepu.webAPI.dto.response.ShopResponse;
import com.keepu.webAPI.dto.response.TransferResponse;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.dto.response.WalletResponse;
import com.keepu.webAPI.model.Wallet;
import com.keepu.webAPI.service.PayPalService;
import com.keepu.webAPI.service.WalletService;
import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

@Tag(name = "Wallet Management", description = "Operations related to wallet management")
@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final PayPalService payPalService;

    @Operation(summary = "Create a new wallet",
            description = "Creates a new wallet for a user. The response contains the wallet details.",
            tags = { "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = WalletResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })
    @PostMapping
    public ResponseEntity<WalletResponse> create(@Valid @RequestBody CreateWalletRequest request) {
        return ResponseEntity.ok(walletService.createWallet(request));
    }

    @Operation(summary = "Get all wallets",
            description = "Retrieves a list of all wallets.",
            tags = {"get", "all"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = Wallet.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })
    @GetMapping
    public ResponseEntity<List<Wallet>> getAll() {
        return ResponseEntity.ok(walletService.getAllWallets());
    }

    @Operation(summary = "Get wallet by ID",
            description = "Retrieves a wallet by its ID.",
            tags = {"get"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = WalletResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })
    @GetMapping("/{id}")
    public ResponseEntity<WalletResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(walletService.getWalletById(id));
    }

    @Operation(summary = "Get wallet by User ID",
            description = "Retrieves a wallet by the User ID. Each user can have only one wallet.",
            tags = {"get"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = WalletResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })
    @GetMapping("/user/{userId}") //SOLO UNA WALLET!!!
    public ResponseEntity<WalletResponse> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getWalletByUserId(userId));
    }

    @Operation(summary = "Get wallet by Wallet ID",
            description = "Retrieves a wallet by its unique Wallet ID.",
            tags = {"get", "wallet", "byWalletId"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = WalletResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })
    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponse> getByWalletId(@PathVariable String walletId) {
        return ResponseEntity.ok(walletService.getWalletByWalletId(walletId));
    }

    @Operation(summary = "Deposit funds into a wallet",
            description = "Deposits funds into a wallet using PayPal. Requires orderId and walletId.",
            tags = {"put"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = WalletResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })
    //no uso userId porq los users pueden tener más de un wallet
    @PutMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody Map<String, Object> requestData) {
        try {
            String orderId = (String) requestData.get("orderId");
            String walletId = (String) requestData.get("walletId");

            String accessToken = payPalService.getAccessToken();
            BigDecimal amount = payPalService.captureOrder(accessToken, orderId);

            WalletResponse walletResponse = walletService.deposit(walletId, amount);

            return ResponseEntity.ok(walletResponse);
        } catch (IOException | InterruptedException | JsonProcessingException e) {
            // Registrar el error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el pago: " + e.getMessage());
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Transfer funds between wallets",
            description = "Transfers funds from one wallet to another. Requires senderWalletId, receiverWalletId, and transactionAmount.",
            tags = {"post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = TransferResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody CreateTransferRequest request) {
        return ResponseEntity.ok(walletService.transfer(
                request.senderWalletId(),
                request.receiverWalletId(),
                request.transactionAmount()
        ));
    }

    @Operation(summary = "Purchase a gift card",
            description = "Purchases a gift card from a store. Requires walletId, storeId, quantity, and totalPrice.",
            tags = {"purchase", "gift card", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content={@Content(schema=@Schema(implementation = ShopResponse.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content= {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500",content= {@Content(schema = @Schema())
            }) })
    @PostMapping("/purchase-gift-card")
    public ResponseEntity<ShopResponse> purchaseGiftCard(@RequestBody CreateShopRequest request) {
        return ResponseEntity.ok(walletService.purchaseGiftCard(
                request.walletId(),
                request.storeId(),
                request.quantity(),
                request.totalPrice()
        ));
    }
}