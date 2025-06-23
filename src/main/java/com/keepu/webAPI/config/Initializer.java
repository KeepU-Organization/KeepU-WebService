package com.keepu.webAPI.config;

import com.keepu.webAPI.dto.request.*;
import com.keepu.webAPI.dto.response.StoreResponse;
import com.keepu.webAPI.mapper.QuizOptionMapper;
import com.keepu.webAPI.model.QuizOption;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.model.UserAuth;
import com.keepu.webAPI.model.enums.ContentType;
import com.keepu.webAPI.model.enums.StoreType;
import com.keepu.webAPI.model.enums.UserType;
import com.keepu.webAPI.repository.GiftCardsRepository;
import com.keepu.webAPI.repository.StoresRepository;
import com.keepu.webAPI.repository.UserRepository;
import com.keepu.webAPI.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {

    private final StoresService storesService;
    private final StoresRepository storesRepository;
    private final GiftCardsService giftCardsService;
    private final GiftCardsRepository giftCardsRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final Random random = new Random();
    private final PasswordEncoder passwordEncoder;

    private final CourseService courseService;
    private final ModulesService modulesService;
    private final ContentItemsService contentItemsService;
    private final QuizQuestionService quizQuestionService;
    private final QuizOptionService quizOptionService;
    private final QuizOptionMapper quizOptionMapper;


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

        //Crear un curso
        if (courseService.getAllCourses().isEmpty()) {
            courseService.createCourse(
                    new CreateCourseRequest(
                            "7 Hábitos de ahorro",
                            "Aprende a ahorrar de manera efectiva con estos 7 hábitos probados.",
                            1, // Dificultad nivel 1
                            false, // No es premium
                            "uploads/courses/cursoAhorro/cursoAhorro.jpg",
                            "AHORRO-2025" // Código del curso
                    ));
            modulesService.createModule( new CreateModuleRequest(
                            "Introducción al ahorro",
                            "Descubre la importancia del ahorro y cómo puede cambiar tu vida financiera.",
                            1, // Orden del módulo
                    "AHORRO-2025", // ID del curso,
                            "uploads/courses/cursoAhorro/modulo1.jpeg",
                            30, // Duración en minutos
                            "AHORRO-2025-M1" // Código del módulo
                    ));

            contentItemsService.createContentItem(new CreateContentItemRequest(
                            "¿Por qué es importante ahorrar?",
                            "El ahorro es fundamental para alcanzar tus metas financieras y tener seguridad económica.",
                            1, // Orden del contenido
                            null, //url
                    """
                            <p>Ahorrar es una práctica fundamental para alcanzar estabilidad financiera y prepararse para el futuro. No se trata solo de guardar lo que sobra, sino de tener un plan para el dinero que ganamos.</p>
                            
                            <p>Una de las razones principales para ahorrar es estar preparado ante emergencias. Los imprevistos, como una enferad, una pérdida de empleo o una reparación inesperada, pueden ocurrir en cualquier momento. Tener un fondo de emergencia brinda tranquilidad y seguridad.</p>
                            
                            <p>Además, el ahorro permite alcanzar metas a corto, mediano y largo plazo. Ya sea comprar una bicicleta, estudiar una carrera, o tener una vejez tranquila, contar con dinero reservado nos da más libertad para tomar decisiones importantes sin depender completamente de préstamos.</p>
                            
                            <p>El hábito de ahorrar también fomenta la disciplina financiera. Nos obliga a controlar los impulsos de gasto y a pensar en el valor real de las cosas. Esto desarrolla una mentalidad responsable que nos acompaña toda la vida.</p>
                            
                            <p>En resumen, ahorrar es importante porque nos da seguridad, nos acerca a nuestras metas y nos enseña a manejar mejor nuestros recursos. No importa cuánto ganes: lo esencial es comenzar con lo que tengas y hacerlo un hábito constante.</p>
                            
                            """,
                    ContentType.ARTICLE,
                            "AHORRO-2025-M1", // ID del módulo
                            "uploads/courses/cursoAhorro/contenido1.jpg",
                            10 // Duración en minutos
                    , "AHORRO-2025-C1" // ID del contenido
                    ));
            contentItemsService.createContentItem(new CreateContentItemRequest(
                            "Cómo crear un presupuesto",
                            "Aprende a crear un presupuesto efectivo para controlar tus gastos y ahorrar más.",
                            2, // Orden del contenido
                            "https://www.youtube.com/watch?v=wRztF8qdmGE",
                    null, // Datos del contenido no aplican,
                            ContentType.VIDEO,
                    "AHORRO-2025-M1", // ID del módulo
                            null,
                            4 // Duración en minutos
                    , "AHORRO-2025-C2"
                    ));

            modulesService.createModule( new CreateModuleRequest(
                            "Estableciendo metas de ahorro",
                            "Aprende a establecer metas claras y alcanzables para tu ahorro.",
                            2, // Orden del módulo
                    "AHORRO-2025", // ID del curso,
                            "uploads/courses/cursoAhorro/modulo2.jpg",
                            45, // Duración en minutos
                    "AHORRO-2025-M2"
                    ));
            contentItemsService.createContentItem(new CreateContentItemRequest(
                        "¿Cuál de los siguientes es un objetivo de ahorro a corto plazo?",
                        "Pon a prueba tus conocimientos sobre los objetivos de ahorro.",
                        1, // Orden del contenido
                        null, // URL no aplica, cambiar a null, no lo hago porque ya tengo datos
                        null, // Datos del contenido no aplican
                        ContentType.QUIZ,
                    "AHORRO-2025-M2", // ID del módulo
                    null, // Imagen no aplica
                        5 // Duración en minutos
                    , "AHORRO-2025-C3" // ID del contenido
            ));
            quizQuestionService.createQuizQuestion(new CreateQuizQuestionRequest(
                    "¿Cuál de los siguientes es un objetivo de ahorro a corto plazo?",
                    "AHORRO-2025-C3",
                    new QuizOption[]{},
                    "AHORRO-2025-Q1"
            ));
            quizOptionService.createQuizOption(new CreateQuizOptionRequest("Comprar un coche nuevo", false,"AHORRO-2025-Q1"));
            quizOptionService.createQuizOption(new CreateQuizOptionRequest("Ahorrar para unas vacaciones", true,"AHORRO-2025-Q1"));
            quizOptionService.createQuizOption(new CreateQuizOptionRequest("Invertir en acciones a largo plazo", false,"AHORRO-2025-Q1"));
            quizOptionService.createQuizOption(new CreateQuizOptionRequest("Comprar una casa", false,"AHORRO-2025-Q1"));

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