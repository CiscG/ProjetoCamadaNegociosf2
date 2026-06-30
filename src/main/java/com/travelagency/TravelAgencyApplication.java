package com.travelagency;

import com.travelagency.util.LoggerUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Classe principal da aplicação Spring Boot
 * Requisito: Blocos estáticos de código
 * Requisito: Desenvolvimento de Componentes de Serviços
 */
@SpringBootApplication
public class TravelAgencyApplication {
    
    // Bloco estático executado quando a classe é carregada
    static {
        System.out.println("""
            ╔══════════════════════════════════════════════════════════════════════════════╗
            ║                                                                              ║
            ║                   🌍 AGÊNCIA DE VIAGENS - TRAVEL AGENCY 🌍                  ║
            ║                                                                              ║
            ║                        Sistema de Gerenciamento v1.0                        ║
            ║                                                                              ║
            ║  Implementação de Requisitos Java:                                          ║
            ║  ✓ Wrapper classes e Autoboxing                                             ║
            ║  ✓ Tipos Genéricos e Enumeration                                           ║
            ║  ✓ Records, Text blocks e Blocos estáticos                                 ║
            ║  ✓ Tipos de Exceções (verificadas e não verificadas)                       ║
            ║  ✓ Blocos try-catch-finally                                                ║
            ║  ✓ Criação e Lançamento de exceções                                        ║
            ║  ✓ Referência this e super                                                 ║
            ║  ✓ Persistência e recuperação de objetos                                   ║
            ║  ✓ Leitura e gravação de arquivos texto                                    ║
            ║  ✓ Manipulação de Dados                                                    ║
            ║  ✓ Manipulação de XML                                                      ║
            ║  ✓ Logging de Sistema                                                      ║
            ║  ✓ Programação Concorrente                                                 ║
            ║  ✓ Desenvolvimento de Componentes de Serviços (REST)                       ║
            ║                                                                              ║
            ║  Stack Tecnológico:                                                        ║
            ║  • Framework: Spring Boot 3.2.5                                             ║
            ║  • Linguagem: Java 17                                                      ║
            ║  • Banco de Dados: MongoDB                                                 ║
            ║                                                                              ║
            ║  Endpoints disponíveis:                                                    ║
            ║  • GET    /api/viagens                 - Listar todas as viagens           ║
            ║  • GET    /api/viagens/{id}            - Obter viagem por ID              ║
            ║  • POST   /api/viagens                 - Criar nova viagem                ║
            ║  • PUT    /api/viagens/{id}            - Atualizar viagem                 ║
            ║  • DELETE /api/viagens/{id}            - Deletar viagem                   ║
            ║  • GET    /api/viagens/export/xml      - Exportar viagens em XML          ║
            ║  • POST   /api/viagens/relatorio       - Gerar relatório em TXT           ║
            ║  • GET    /api/clientes                - Listar todos os clientes         ║
            ║  • GET    /api/clientes/{id}           - Obter cliente por ID             ║
            ║  • POST   /api/clientes                - Criar novo cliente               ║
            ║  • PUT    /api/clientes/{id}           - Atualizar cliente                ║
            ║  • DELETE /api/clientes/{id}           - Deletar cliente                  ║
            ║  • GET    /api/clientes/{id}/export/xml - Exportar cliente em XML         ║
            ║                                                                              ║
            ║  Documentação: http://localhost:8080/swagger-ui.html                       ║
            ║                                                                              ║
            ╚══════════════════════════════════════════════════════════════════════════════╝
            """);
    }

    public static void main(String[] args) {
        SpringApplication.run(TravelAgencyApplication.class, args);
    }

    /**
     * Bean para LoggerUtil
     */
    @Bean
    public LoggerUtil loggerUtil() {
        return new LoggerUtil();
    }
}
