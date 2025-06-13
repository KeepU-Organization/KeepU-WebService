package com.keepu.webAPI.config;

import com.keepu.webAPI.dto.request.CreateGiftCardRequest;
import com.keepu.webAPI.dto.request.CreateStoreRequest;
import com.keepu.webAPI.dto.response.StoreResponse;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserAuth;
import com.keepu.webAPI.model.enums.StoreType;
import com.keepu.webAPI.model.enums.UserType;
import com.keepu.webAPI.repository.GiftCardsRepository;
import com.keepu.webAPI.repository.StoresRepository;
import com.keepu.webAPI.repository.UserAuthRespository;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.service.GiftCardsService;
import com.keepu.webAPI.service.StoresService;
import com.keepu.webAPI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class StoresInitializer implements CommandLineRunner {

    private final StoresService storesService;
    private final StoresRepository storesRepository;
    private final GiftCardsService giftCardsService;
    private final GiftCardsRepository giftCardsRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final Random random = new Random();
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) {
        // Solo crear tiendas si no existen
        if (storesRepository.count() == 0) {
            List<CreateStoreRequest> defaultStores = List.of(
                    new CreateStoreRequest("Steam", "Plataforma de videojuegos digitales", StoreType.GAMING ,
                            "https://store.steampowered.com/about/"),
                    new CreateStoreRequest("Riot", "Desarrollador de videojuegos populares", StoreType.GAMING,
                            "https://www.riotgames.com/es/"),
                    new CreateStoreRequest("Ripley", "Tienda departamental", StoreType.DEPARTMENT_STORE,
                            "https://simple.ripley.com.pe/")
            );

            List<StoreResponse> createdStores = defaultStores.stream()
                    .map(storesService::createStore)
                    .toList();

            System.out.println("Tiendas por defecto creadas exitosamente");

            // Crear 10 gift cards para cada tienda
            if (giftCardsRepository.count() == 0) {
                for (StoreResponse store : createdStores) {
                    BigDecimal randomAmount = BigDecimal.valueOf(10 + random.nextInt(91)); // Valores entre 10 y 100
                    for (int i = 0; i < 10; i++) {
                        String code = generateRandomCode();
                        CreateGiftCardRequest giftCardRequest = new CreateGiftCardRequest(
                                code,
                                randomAmount,
                                store.id()
                        );
                        giftCardsService.createGiftCard(giftCardRequest);
                    }
                }
                System.out.println("Gift cards creadas exitosamente");
            }
        }

        // crear un user de tipo admin:
        if (userRepository.findByEmail("admin@keepu.com").isEmpty()) {
            User adminUser = new User();
            adminUser.setName("admin");
            adminUser.setLastNames("1");
            adminUser.setEmail("admin@keepu.com");
            adminUser.setUserType(UserType.ADMIN);
            UserAuth adminAuth = new UserAuth();
            adminAuth.setEmailVerified(true);

            String encodedNewPassword = passwordEncoder.encode("@Dmin12345");

            adminAuth.setPassword(encodedNewPassword);
            adminAuth.setUser(adminUser);

            userService.createAdminUser(adminUser, adminAuth);
            System.out.println("Usuario admin creado exitosamente");
        }


    }

    private String generateRandomCode() {
        // Genera un código aleatorio para las gift cards (formato: XXXX-XXXX-XXXX)
        StringBuilder code = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                code.append(chars.charAt(random.nextInt(chars.length())));
            }
            if (i < 2) code.append("-");
        }

        return code.toString();
    }
}