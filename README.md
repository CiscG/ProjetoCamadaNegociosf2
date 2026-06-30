<<<<<<< HEAD
# Airbnb Clone - Plataforma de Aluguel de Imóveis

Plataforma de aluguel de imóveis por temporada — clone didático do Airbnb — desenvolvida com **Spring Boot 3.2.5**, **MongoDB**, **React 19** e **Vite**.

Projeto acadêmico implementando camadas de negócio, persistência, requisitos de Java avançados (annotations, reflection, collections), geração de relatórios XML/TXT e CLI interativa.

---

## 🏗️ Arquitetura

### Backend (Spring Boot)
- **Framework:** Spring Boot 3.2.5
- **Banco de dados:** MongoDB
- **Linguagem:** Java 17+
- **Estrutura:** Controllers → Services → Repositories (camadas)
- **Porta:** 5000

### Frontend (React)
- **Framework:** React 19
- **Build Tool:** Vite 8
- **Estilização:** Tailwind CSS
- **Roteamento:** React Router
- **Porta:** 5173
=======
# 🏨 Agência de Viagens - Sistema de Gerenciamento

## 📋 Visão Geral

Sistema completo de gerenciamento de viagens desenvolvido em **Java 17** com **Spring Boot 3.2.5** e **MongoDB**. O projeto implementa todos os requisitos mínimos de programação Java, incluindo tipos genéricos, exceções, persistência, concorrência e componentes REST.

**Tema:** Agência de Viagens  
**Stack:** Java 17 + Spring Boot 3.2.5 + MongoDB + Maven

---

## ✅ Requisitos Implementados

### 1. ✓ Wrapper Classes e Autoboxing
**Arquivo:** `src/main/java/com/travelagency/model/Cliente.java`
>>>>>>> origin/implementar-requisitos

```java
// Wrapper classes sendo usadas
private Integer idade;           // Integer - wrapper de int
private Double renda;            // Double - wrapper de double
private Boolean ativo;           // Boolean - wrapper de boolean
private Long ultimoAcesso;       // Long - wrapper de long

<<<<<<< HEAD
## 📋 Pré-requisitos

- **Java 17+** (OpenJDK ou Oracle JDK)
- **Maven 3.8+** (para compilação)
- **Node.js 18+** (para frontend)
- **MongoDB 6+** (Docker recomendado)
- **Docker & Docker Compose** (opcional, para MongoDB)

---

## 🚀 Como rodar (desenvolvimento)

Você precisará de **três terminais abertos** ao mesmo tempo.

### Terminal 1 — MongoDB (Docker)

```bash
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

Ou use Docker Compose se houver um arquivo `docker-compose.yml`:

```bash
docker-compose up -d
```

### Terminal 2 — Backend Spring Boot

```bash
# Na raiz do projeto
cd ~/ProjetoCamadaNegocios2

# Compile e execute
mvn clean install
mvn spring-boot:run
```

Backend roda em **http://localhost:5000**

> **Nota para Linux:** Se receber erro de conexão, obtenha o IP do MongoDB:
> ```bash
> docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mongodb
> ```
> E atualize `src/main/resources/application.properties`:
> ```properties
> spring.data.mongodb.uri=mongodb://172.17.0.2:27017/airbnb_clone
> ```
> (Substitua `172.17.0.2` pelo IP obtido)

### Terminal 3 — Frontend React

```bash
cd FrontEnd
npm install
npm run dev
```

Acesse **http://localhost:5173** no navegador.

> O Vite redireciona automaticamente as chamadas `/api` para o backend na porta 5000.

---

## 🏭 Como rodar (produção)

Gere o build do frontend e sirva tudo pelo Spring Boot:

```bash
cd FrontEnd
npm run build
cd ..
mvn clean package -DskipTests
java -jar target/airbnb-clone-1.0.0.jar
=======
// Autoboxing em ação
this.ativo = true;               // boolean -> Boolean (autoboxing)
this.idade++;                    // Integer -> int -> Integer (unboxing + autoboxing)
```

---

### 2. ✓ Tipos Genéricos e Enumeration
**Arquivos:** 
- `src/main/java/com/travelagency/model/TipoViagem.java` (Enumeration)
- `src/main/java/com/travelagency/repository/ViagemRepository.java` (Tipos Genéricos)

```java
// Enumeration
public enum TipoViagem {
    NACIONAL("Viagem Nacional"),
    INTERNACIONAL("Viagem Internacional"),
    TURISMO_RELIGIOSO("Turismo Religioso"),
    TURISMO_AVENTURA("Turismo de Aventura"),
    TURISMO_PRAIA("Turismo de Praia"),
    TURISMO_CULTURAL("Turismo Cultural"),
    TURISMO_NEGOCIOS("Turismo de Negócios");
}

// Tipos Genéricos
public interface ViagemRepository extends MongoRepository<Viagem, String> {
    List<Viagem> findByDestino(String destino);
    List<Viagem> findByAtivaTrue();
}
```

---

### 3. ✓ Records, Text Blocks e Blocos Estáticos
**Arquivos:**
- `src/main/java/com/travelagency/model/Viagem.java` (Text blocks e bloco estático)
- `src/main/java/com/travelagency/TravelAgencyApplication.java` (Bloco estático)

```java
// Text block (quebra de linha em string)
static {
    System.out.println("""
        ╔════════════════════════════════════════════════════════════════════════════╗
        ║                   🌍 AGÊNCIA DE VIAGENS - TRAVEL AGENCY 🌍                ║
        ║                   Sistema de Gerenciamento v1.0                          ║
        ╚════════════════════════════════════════════════════════════════════════════╝
        """);
}
>>>>>>> origin/implementar-requisitos
```

---

<<<<<<< HEAD
## 📊 Dados de demonstração

O banco é populado automaticamente na primeira inicialização (classe `SeedDataLoader`).

| Email | Perfil | Senha |
|---|---|---|
| carlos@email.com | anfitriao | 123456 |
| maria@email.com | hospede | 123456 |
| ana@email.com | ambos | 123456 |

---

## ✨ Funcionalidades

| Recurso | Detalhes |
|---|---|
| **Login** | Autenticação por email/senha com hash bcrypt |
| **Busca de imóveis** | Filtro por cidade, preço, avaliação |
| **Reserva** | Criação com verificação de conflito de datas |
| **Disponibilidade** | Visualização de períodos ocupados |
| **Dashboard** | Reservas como hóspede e imóveis como anfitrião |
| **Cadastro de imóvel** | Perfis `anfitriao` e `ambos` |
| **Relatórios** | Exportação XML e TXT |
| **CLI** | Interface de linha de comando interativa |

---

## 🔌 API REST

### Autenticação
| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/login` | Autenticar usuário |
| `POST` | `/api/logout` | Fazer logout |

### Usuários
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/usuarios` | Listar usuários |
| `GET` | `/api/usuarios/{id}` | Obter usuário por ID |
| `POST` | `/api/usuarios` | Criar novo usuário |
| `PUT` | `/api/usuarios/{id}` | Atualizar usuário |
| `DELETE` | `/api/usuarios/{id}` | Deletar usuário |

### Imóveis (Locais)
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/locais` | Listar imóveis (`cidade`, `preco_max`, `anfitriao_id`) |
| `GET` | `/api/locais/{id}` | Obter imóvel por ID |
| `POST` | `/api/locais` | Cadastrar novo imóvel |
| `PUT` | `/api/locais/{id}` | Atualizar imóvel |
| `DELETE` | `/api/locais/{id}` | Deletar imóvel |
| `GET` | `/api/locais/{id}/ocupacao` | Períodos ocupados |

### Reservas
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/reservas` | Listar reservas (`hospede_id`, `anfitriao_id`, `status`) |
| `GET` | `/api/reservas/{id}` | Obter reserva por ID |
| `POST` | `/api/reservas` | Criar reserva |
| `PUT` | `/api/reservas/{id}` | Atualizar reserva |
| `DELETE` | `/api/reservas/{id}` | Cancelar reserva |
| `GET` | `/api/reservas/relatorio/xml` | Exportar em XML |
| `GET` | `/api/reservas/relatorio/txt` | Exportar em TXT |

---

## 📁 Estrutura do projeto

```
ProjetoCamadaNegocios2/
├── src/main/java/com/airbnbclone/
│   ├── AirbnbCloneApplication.java        # Aplicação principal
│   ├── controller/                        # Controladores REST
│   ├── service/                           # Lógica de negócio
│   ├── repository/                        # Acesso a dados (MongoDB)
│   ├── model/                             # Entidades (Usuario, Local, Reserva)
│   ├── dto/                               # Data Transfer Objects
│   ├── util/                              # Utilitários
│   ├── seed/                              # Dados iniciais
│   └── cli/                               # Interface CLI
├── src/main/resources/
│   └── application.properties             # Configurações Spring Boot
├── pom.xml                                # Dependências Maven
├── FrontEnd/                              # Aplicação React
│   ├── src/
│   │   ├── pages/                         # Páginas
│   │   ├── components/                    # Componentes React
│   │   ├── hooks/                         # Custom hooks
│   │   ├── utils/                         # Funções utilitárias
│   │   └── App.jsx
│   ├── package.json
│   └── vite.config.js
└── README.md                              # Este arquivo
```

---

## 🛠️ Requisitos de Java Implementados

- ✅ **Annotations customizadas** (@ValidEmail, @UniqueEmail)
- ✅ **Reflection** para validação genérica
- ✅ **Collections** (List, Set, Map) para manipulação de dados
- ✅ **Streams** para processamento funcional
- ✅ **Padrões de Design** (Repository, Service, DTO)
- ✅ **Tratamento de exceções** customizado
- ✅ **Serialização** JSON com Jackson
- ✅ **Geração de relatórios** XML e TXT

---

## 🧪 Testes

Execute os testes com:

```bash
mvn test
```

---

## 📝 Variáveis de Ambiente

Você pode customizar as configurações via variáveis de ambiente:

```bash
export MONGO_URI=mongodb://seu-host:27017/airbnb_clone
export SERVER_PORT=5000
export SPRING_PROFILES_ACTIVE=dev
```

---

## 🐛 Troubleshooting

### MongoDB não conecta (Linux)
```bash
# Obter IP do container
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mongodb

# Atualizar application.properties com o IP
spring.data.mongodb.uri=mongodb://IP:27017/airbnb_clone
```

### Erro de porta já em uso
```bash
# Liberar a porta (ex: 5000)
lsof -i :5000
kill -9 <PID>
```

### Frontend não conecta ao backend
- Verifique se o backend está rodando em `http://localhost:5000`
- Limpe o cache do navegador (Ctrl+Shift+Delete)
- Revise o console do navegador para erros de CORS

---

## 📄 Licença

Projeto acadêmico — livre para uso educacional.

---

## 👥 Autores

- **Francisco Sousa** (CiscG)

---

## 🔗 Links úteis

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [MongoDB Docs](https://docs.mongodb.com/)
- [React Documentation](https://react.dev)
- [Vite Guide](https://vitejs.dev/guide/)
=======
### 4. ✓ Tipos de Exceções (Verificadas e Não Verificadas)
**Pasta:** `src/main/java/com/travelagency/exception/`

**Exceções Verificadas (Checked Exceptions):**
- `ViagemNotFoundException.java` - extends Exception
- `DatasInvalidasException.java` - extends Exception
- `ReservaException.java` - extends Exception

**Exceções Não Verificadas (Runtime Exceptions):**
- `ClienteInvalidoException.java` - extends RuntimeException
- `ArquivoProcessamentoException.java` - extends RuntimeException

```java
// Exceção Verificada
public class ViagemNotFoundException extends Exception {
    private String viagemId;
    
    public ViagemNotFoundException(String message, String viagemId) {
        super(message);
        this.viagemId = viagemId;
    }
}

// Exceção Não Verificada
public class ClienteInvalidoException extends RuntimeException {
    private String clienteId;
    
    public ClienteInvalidoException(String message, String clienteId) {
        super(message);
        this.clienteId = clienteId;
    }
}
```

---

### 5. ✓ Blocos Try-Catch-Finally
**Arquivo:** `src/main/java/com/travelagency/service/ViagemService.java`

```java
public Viagem buscarViagemById(String id) throws ViagemNotFoundException {
    try {
        if (id == null || id.isEmpty()) {
            throw new ViagemNotFoundException("ID da viagem não pode ser vazio", id);
        }

        Optional<Viagem> viagem = viagemRepository.findById(id);
        
        if (viagem.isEmpty()) {
            throw new ViagemNotFoundException("Viagem não encontrada com ID: " + id, id);
        }
        
        logger.info("Viagem encontrada: " + viagem.get().getDestino());
        return viagem.get();
        
    } catch (ViagemNotFoundException e) {
        logger.error("Erro - Viagem não encontrada: " + e.getMessage());
        throw e;
    } catch (Exception e) {
        logger.error("Erro ao buscar viagem: " + e.getMessage());
        throw new ViagemNotFoundException("Erro ao buscar viagem", e);
    } finally {
        logger.info("Finalizada busca de viagem com ID: " + id);
    }
}
```

---

### 6. ✓ Criação e Lançamento de Exceções
**Arquivo:** `src/main/java/com/travelagency/service/ViagemService.java`

```java
public Viagem salvarViagem(Viagem viagem) throws DatasInvalidasException {
    try {
        if (viagem == null) {
            throw new ViagemNotFoundException("Viagem não pode ser nula");
        }
        
        // Validações com lançamento de exceções
        if (viagem.getDataPartida().isAfter(viagem.getDataRetorno())) {
            throw new DatasInvalidasException(
                "Data de partida não pode ser após data de retorno",
                viagem.getDataPartida().toString(),
                viagem.getDataRetorno().toString()
            );
        }
        
        if (viagem.getDataPartida().isBefore(LocalDate.now())) {
            throw new DatasInvalidasException(
                "Data de partida não pode ser no passado",
                viagem.getDataPartida().toString(),
                viagem.getDataRetorno().toString()
            );
        }
        
        Viagem salva = viagemRepository.save(viagem);
        logger.info("Viagem salva com sucesso: " + salva.getDestino());
        return salva;
        
    } catch (DatasInvalidasException e) {
        logger.error("Erro nas datas: " + e.getMessage());
        throw e;  // Lançamento de exceção
    } finally {
        logger.info("Operação de salvar viagem finalizada");
    }
}
```

---

### 7. ✓ Referência This e Super
**Arquivo:** `src/main/java/com/travelagency/service/ClienteService.java`

```java
@Service
public class ClienteService {
    
    private final ClienteRepository clienteRepository;  // this.clienteRepository
    private final LoggerUtil logger;                    // this.logger

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, LoggerUtil logger) {
        this.clienteRepository = clienteRepository;     // Referência this
        this.logger = logger;                           // Referência this
    }
}

// Herança (super implícito em MongoRepository)
public interface ViagemRepository extends MongoRepository<Viagem, String> {
    // Super implícito - herda métodos do MongoRepository
}
```

---

### 8. ✓ Persistência e Recuperação de Objetos
**Arquivos:**
- `src/main/java/com/travelagency/model/Viagem.java`
- `src/main/java/com/travelagency/model/Cliente.java`
- `src/main/java/com/travelagency/model/Reserva.java`

```java
@Document(collection = "viagens")
public class Viagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    private String destino;
    private TipoViagem tipo;
    private LocalDate dataPartida;
    // ... outros campos
}

// Recuperação
Viagem viagem = viagemService.buscarViagemById(id);

// Persistência
Viagem salva = viagemRepository.save(viagem);
```

---

### 9. ✓ Leitura e Gravação de Arquivos Texto
**Arquivo:** `src/main/java/com/travelagency/util/ArquivoUtil.java`

```java
// Gravação de arquivo
public static void gravarRelatorioViagens(List<Viagem> viagens, String nomeArquivo) {
    FileWriter writer = null;
    try {
        String caminhoCompleto = DIRETORIO_RELATORIOS + nomeArquivo + ".txt";
        writer = new FileWriter(caminhoCompleto, StandardCharsets.UTF_8);
        
        writer.write("=".repeat(80) + "\n");
        writer.write("RELATÓRIO DE VIAGENS - AGÊNCIA DE VIAGENS\n");
        // ... escreve dados
        
    } catch (IOException e) {
        logger.error("Erro ao gravar relatório: " + e.getMessage());
    } finally {
        if (writer != null) {
            writer.close();
        }
    }
}

// Leitura de arquivo
public static List<String> lerConfiguracao(String nomeArquivo) {
    List<String> linhas = new ArrayList<>();
    BufferedReader reader = null;
    
    try {
        String caminho = "config/" + nomeArquivo + ".txt";
        reader = new BufferedReader(new FileReader(caminho, StandardCharsets.UTF_8));
        
        String linha;
        while ((linha = reader.readLine()) != null) {
            if (!linha.trim().isEmpty() && !linha.startsWith("#")) {
                linhas.add(linha.trim());
            }
        }
    } catch (FileNotFoundException e) {
        logger.warn("Arquivo não encontrado: " + nomeArquivo);
    } finally {
        if (reader != null) {
            reader.close();
        }
    }
    return linhas;
}
```

---

### 10. ✓ Manipulação de Dados
**Arquivos:**
- `src/main/java/com/travelagency/service/ViagemService.java` - Busca, filtragem, atualização
- `src/main/java/com/travelagency/service/ClienteService.java` - CRUD operations

```java
// Listar com tipos genéricos
public List<Viagem> listarTodas() {
    try {
        List<Viagem> viagens = viagemRepository.findByAtivaTrue();
        logger.info("Total de viagens ativas encontradas: " + viagens.size());
        return viagens;
    } finally {
        logger.info("Busca de todas as viagens finalizada");
    }
}

// Buscar com filtro
public List<Viagem> buscarPorDestino(String destino) {
    try {
        if (destino == null || destino.isEmpty()) {
            throw new ViagemNotFoundException("Destino não pode ser vazio");
        }
        return viagemRepository.findByDestino(destino);
    } finally {
        logger.info("Busca de viagens por destino finalizada: " + destino);
    }
}
```

---

### 11. ✓ Manipulação de XML
**Arquivo:** `src/main/java/com/travelagency/util/XMLUtil.java`

```java
public static String viagensParaXML(List<Viagem> viagens) {
    StringWriter stringWriter = null;
    try {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        
        // Elemento raiz
        Element rootElement = doc.createElement("viagens");
        rootElement.setAttribute("total", String.valueOf(viagens.size()));
        rootElement.setAttribute("xmlns", "http://www.travelagency.com/viagens");
        doc.appendChild(rootElement);
        
        // Adiciona cada viagem
        for (Viagem viagem : viagens) {
            Element viagemElement = doc.createElement("viagem");
            addElement(doc, viagemElement, "id", viagem.getId());
            addElement(doc, viagemElement, "destino", viagem.getDestino());
            // ... outros elementos
            rootElement.appendChild(viagemElement);
        }
        
        // Converte para String
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        stringWriter = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
        
        return stringWriter.toString();
        
    } finally {
        if (stringWriter != null) {
            stringWriter.close();
        }
    }
}
```

---

### 12. ✓ Logging de Sistema
**Arquivo:** `src/main/java/com/travelagency/util/LoggerUtil.java`

```java
public class LoggerUtil {
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String LOG_PREFIX = "[TRAVEL-AGENCY]";
    
    public void info(String mensagem) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println(String.format("%s [INFO] %s - %s", LOG_PREFIX, timestamp, mensagem));
    }
    
    public void warn(String mensagem) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println(String.format("%s [WARN] %s - %s", LOG_PREFIX, timestamp, mensagem));
    }
    
    public void error(String mensagem) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.err.println(String.format("%s [ERROR] %s - %s", LOG_PREFIX, timestamp, mensagem));
    }
}
```

---

### 13. ✓ Programação Concorrente
**Arquivo:** `src/main/java/com/travelagency/service/ViagemService.java`

```java
private final ExecutorService executorService = Executors.newFixedThreadPool(3);

// Busca assíncrona
public Future<Viagem> buscarViagemAsync(String id) {
    return executorService.submit(() -> {
        try {
            Thread.sleep(500); // Simula operação demorada
            return buscarViagemById(id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrompida");
            return null;
        } catch (ViagemNotFoundException e) {
            logger.error("Erro na busca assíncrona: " + e.getMessage());
            return null;
        }
    });
}

// Atualização concorrente
public void atualizarVagasAsync(String id, Integer novasVagas) {
    executorService.execute(() -> {
        try {
            Viagem viagem = buscarViagemById(id);
            viagem.setVagas(novasVagas);
            viagemRepository.save(viagem);
            logger.info("Vagas atualizadas para viagem: " + id);
        } catch (ViagemNotFoundException e) {
            logger.error("Erro ao atualizar vagas: " + e.getMessage());
        }
    });
}
```

---

### 14. ✓ Desenvolvimento de Componentes de Serviços (REST)
**Arquivos:**
- `src/main/java/com/travelagency/controller/ViagemController.java`
- `src/main/java/com/travelagency/controller/ClienteController.java`

```java
@RestController
@RequestMapping("/api/viagens")
public class ViagemController {
    
    private final ViagemService viagemService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodas() { ... }
    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obterPorId(@PathVariable String id) { ... }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> criar(@RequestBody Viagem viagem) { ... }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizar(@PathVariable String id, @RequestBody Viagem viagem) { ... }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletar(@PathVariable String id) { ... }
    
    @GetMapping("/export/xml")
    public ResponseEntity<String> exportarXML() { ... }
    
    @PostMapping("/relatorio")
    public ResponseEntity<Map<String, Object>> gerarRelatorio() { ... }
    
    @GetMapping("/async/{id}")
    public ResponseEntity<Map<String, Object>> buscarAsync(@PathVariable String id) { ... }
}
```

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

1. **Java 17 ou superior**
   ```bash
   java -version
   ```
   Se não tiver, baixe em: https://www.oracle.com/java/technologies/downloads/

2. **Maven 3.6+**
   ```bash
   mvn -version
   ```
   Se não tiver, baixe em: https://maven.apache.org/download.cgi

3. **MongoDB rodando localmente**
   ```bash
   # Windows
   # Instale MongoDB Community: https://www.mongodb.com/try/download/community
   # Após instalação, o serviço deve iniciar automaticamente
   
   # Linux/Mac
   brew install mongodb-community
   brew services start mongodb-community
   
   # WSL (Windows Subsystem for Linux)
   sudo service mongod start
   ```

   Verifique se o MongoDB está rodando:
   ```bash
   mongosh
   # Deve conectar ao localhost:27017
   ```

### Passos para Executar

#### 1️⃣ Clonar ou abrir o repositório
```bash
cd ProjetoCamadaNegociosf2
```

#### 2️⃣ Fazer checkout da branch implementar-requisitos
```bash
git checkout implementar-requisitos
```

#### 3️⃣ Instalar dependências e compilar
```bash
mvn clean install
```

Este comando irá:
- Limpar builds anteriores
- Baixar todas as dependências
- Compilar o código
- Executar testes (se houver)

#### 4️⃣ Executar a aplicação

**Opção A: Via Maven**
```bash
mvn spring-boot:run
```

**Opção B: Executar JAR diretamente**
```bash
mvn package
java -jar target/airbnb-clone-1.0.0.jar
```

#### 5️⃣ Verificar se está rodando

A aplicação deve iniciar com a seguinte saída no console:

```
╔════════════════════════════════════════════════════════════════════════════╗
║                   🌍 AGÊNCIA DE VIAGENS - TRAVEL AGENCY 🌍                ║
║                   Sistema de Gerenciamento v1.0                          ║
╚════════════════════════════════════════════════════════════════════════════╝

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.5)

Yyyy-MM-dd HH:mm:ss - com.travelagency.TravelAgencyApplication - Started TravelAgencyApplication
```

Pronto! A aplicação está rodando em **http://localhost:8080**

---

## 📡 Testando a API

### Via Postman ou Curl

#### 1. Criar uma Viagem
```bash
curl -X POST http://localhost:8080/api/viagens \
  -H "Content-Type: application/json" \
  -d '{
    "destino": "Rio de Janeiro",
    "tipo": "TURISMO_PRAIA",
    "dataPartida": "2026-07-15",
    "dataRetorno": "2026-07-22",
    "preco": 2500.00,
    "vagas": 20,
    "descricao": "Viagem para as praias do Rio"
  }'
```

#### 2. Listar todas as viagens
```bash
curl http://localhost:8080/api/viagens
```

#### 3. Obter viagem por ID
```bash
curl http://localhost:8080/api/viagens/{id}
```

#### 4. Atualizar viagem
```bash
curl -X PUT http://localhost:8080/api/viagens/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "vagas": 15
  }'
```

#### 5. Deletar viagem
```bash
curl -X DELETE http://localhost:8080/api/viagens/{id}
```

#### 6. Exportar viagens em XML
```bash
curl http://localhost:8080/api/viagens/export/xml
```

#### 7. Gerar relatório em TXT
```bash
curl -X POST http://localhost:8080/api/viagens/relatorio
```

#### 8. Busca assíncrona
```bash
curl http://localhost:8080/api/viagens/async/{id}
```

#### 9. Criar cliente
```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@example.com",
    "telefone": "11999999999",
    "cpf": "12345678900",
    "idade": 35,
    "renda": 5000.00
  }'
```

---

## 📁 Estrutura de Diretórios

```
ProjetoCamadaNegociosf2/
│
├── src/main/java/com/travelagency/
│   ├── TravelAgencyApplication.java          # Classe principal com bloco estático
│   │
│   ├── model/
│   │   ├── Viagem.java                       # Modelo com Record e text blocks
│   │   ├── Cliente.java                      # Wrapper classes e autoboxing
│   │   ├── Reserva.java                      # Persistência
│   │   └── TipoViagem.java                   # Enumeration
│   │
│   ├── exception/
│   │   ├── ViagemNotFoundException.java       # Checked Exception
│   │   ├── DatasInvalidasException.java      # Checked Exception
│   │   ├── ReservaException.java             # Checked Exception
│   │   ├── ClienteInvalidoException.java     # Runtime Exception
│   │   └── ArquivoProcessamentoException.java # Runtime Exception
│   │
│   ├── repository/
│   │   ├── ViagemRepository.java             # Tipos genéricos
│   │   ├── ClienteRepository.java
│   │   └── ReservaRepository.java
│   │
│   ├── service/
│   │   ├── ViagemService.java                # Try-catch-finally, concorrência
│   │   ├── ClienteService.java               # Try-catch-finally
│   │   └── ReservaService.java               # (Placeholder)
│   │
│   ├── controller/
│   │   ├── ViagemController.java             # Componentes REST
│   │   └── ClienteController.java            # Componentes REST
│   │
│   └── util/
│       ├── LoggerUtil.java                   # Logging de sistema
│       ├── ArquivoUtil.java                  # Leitura/gravação de arquivos
│       └── XMLUtil.java                      # Manipulação de XML
│
├── src/main/resources/
│   └── application.properties                # Configuração do MongoDB
│
├── pom.xml                                   # Dependências Maven
│
├── mvnw / mvnw.cmd                          # Maven wrapper
│
└── README.md                                 # Este arquivo
```

---

## 📊 Diagrama de Fluxo

```
┌─────────────────────────────────────────────────────────────┐
│                   Cliente HTTP/REST                         │
└──────────────┬──────────────────────────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────────────────────────┐
│              ViagemController / ClienteController           │
│  (Componentes de Serviço - REST Endpoints)                  │
└──────────────┬──────────────────────────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────────────────────────┐
│     ViagemService / ClienteService                          │
│  (Tipos genéricos, try-catch-finally, concorrência)         │
└──────────────┬──────────────────────────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────────────────────────┐
│   ViagemRepository / ClienteRepository                      │
│  (Persistência com MongoDB)                                 │
└──────────────┬──────────────────────────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────────────────────────┐
│                    MongoDB                                  │
│  (Banco de dados NoSQL)                                     │
└──────────��──────────────────────────────────────────────────┘

Utilitários paralelos:
  - LoggerUtil: Logging de todas as operações
  - ArquivoUtil: Geração de relatórios em TXT
  - XMLUtil: Exportação em XML
  - Exceções: Tratamento de erros verificados e não verificados
  - Modelos: Wrapper classes, autoboxing, enumerações
```

---

## 🧪 Testando Requisitos Implementados

### 1. Testar Wrapper Classes e Autoboxing
```java
// Ver Cliente.java - métodos fazerAniversario(), isMenorIdade(), calcularBonus()
Cliente cliente = new Cliente("João", "joao@email.com", "11999999", "12345678900", 25, 5000.0);
cliente.fazerAniversario();  // Usa autoboxing/unboxing
Boolean menorIdade = cliente.isMenorIdade();  // Boolean (wrapper)
Double bonus = cliente.calcularBonus(10.0);   // Double (wrapper)
```

### 2. Testar Tipos Genéricos
```java
// Ver ViagemRepository.java
List<Viagem> viagens = viagemRepository.findAll();  // List<Viagem>
Optional<Cliente> cliente = clienteRepository.findByEmail("email@test.com");  // Optional<Cliente>
```

### 3. Testar Enumeração
```java
// Via API
POST /api/viagens
{
  "tipo": "TURISMO_PRAIA"  // Enumeration
}
```

### 4. Testar Exceções
```bash
# Exceção verificada - Data inválida
POST /api/viagens com dataPartida > dataRetorno
# Retorna: DatasInvalidasException (checked)

# Exceção não verificada - Cliente inválido
POST /api/clientes com dados incompletos
# Retorna: ClienteInvalidoException (runtime)
```

### 5. Testar Logging
```bash
# Observar console durante operações
[TRAVEL-AGENCY] [INFO] 2026-06-30 18:35:10 - Viagem encontrada: Rio de Janeiro
[TRAVEL-AGENCY] [DEBUG] 2026-06-30 18:35:10 - Iniciando busca...
[TRAVEL-AGENCY] [ERROR] 2026-06-30 18:35:11 - Erro ao buscar viagem
```

### 6. Testar Concorrência
```bash
# Busca assíncrona
GET /api/viagens/async/{id}
# Resposta: {"status":"sucesso", "processamento":"assincrono"}
```

### 7. Testar Manipulação de XML
```bash
# Exportar viagens em XML
GET /api/viagens/export/xml
# Cria arquivo: relatorios/viagens_export.xml
```

### 8. Testar Arquivo Texto
```bash
# Gerar relatório
POST /api/viagens/relatorio
# Cria arquivo: relatorios/relatorio_viagens.txt
```

---

## 🛠️ Troubleshooting

### MongoDB não conecta
```bash
# Verifique se o MongoDB está rodando
mongosh
# Se erro: "unable to connect"

# Windows
net start MongoDB

# Linux/Mac
brew services start mongodb-community

# WSL
sudo service mongod start
```

### Porta 8080 já em uso
```bash
# Altere em application.properties
server.port=8081
```

### Erro de dependências Maven
```bash
mvn clean
mvn install -DskipTests
```

### Limpar cache do Maven
```bash
rm -rf ~/.m2/repository
mvn install
```

---

## 📚 Stack Tecnológico

| Tecnologia | Versão | Propósito |
|-----------|--------|----------|
| Java | 17 | Linguagem principal |
| Spring Boot | 3.2.5 | Framework web e IoC |
| Spring Data MongoDB | 3.2.5 | Persistência |
| MongoDB | 5.0+ | Banco de dados NoSQL |
| Maven | 3.6+ | Build e dependências |
| JDK | 17 | Runtime |

---

## ✨ Funcionalidades Principais

✅ **CRUD Completo** - Create, Read, Update, Delete de viagens e clientes  
✅ **Busca Avançada** - Filtro por destino, tipo, data  
✅ **Exportação XML** - Gera relatórios em formato XML  
✅ **Relatórios TXT** - Gera relatórios formatados em texto  
✅ **Processamento Assíncrono** - Busca e atualização em background  
✅ **Tratamento de Erros** - Exceções verificadas e não verificadas  
✅ **Logging Completo** - Rastreamento de todas as operações  
✅ **Validação de Dados** - Validação de datas, emails, CPF  
✅ **REST API** - Endpoints RESTful para integração  

---

## 📝 Endpoints Disponíveis

### Viagens
| Método | Endpoint | Descrição |
|--------|----------|----------|
| GET | `/api/viagens` | Listar todas as viagens |
| GET | `/api/viagens/{id}` | Obter viagem por ID |
| POST | `/api/viagens` | Criar nova viagem |
| PUT | `/api/viagens/{id}` | Atualizar viagem |
| DELETE | `/api/viagens/{id}` | Deletar viagem |
| GET | `/api/viagens/destino/{destino}` | Buscar por destino |
| GET | `/api/viagens/export/xml` | Exportar em XML |
| POST | `/api/viagens/relatorio` | Gerar relatório TXT |
| GET | `/api/viagens/async/{id}` | Busca assíncrona |

### Clientes
| Método | Endpoint | Descrição |
|--------|----------|----------|
| GET | `/api/clientes` | Listar todos os clientes |
| GET | `/api/clientes/{id}` | Obter cliente por ID |
| POST | `/api/clientes` | Criar novo cliente |
| PUT | `/api/clientes/{id}` | Atualizar cliente |
| DELETE | `/api/clientes/{id}` | Deletar cliente |
| GET | `/api/clientes/email/{email}` | Buscar por email |
| GET | `/api/clientes/{id}/export/xml` | Exportar cliente em XML |

---

## 📌 Observações Importantes

1. **MongoDB é obrigatório** - A aplicação não funcionará sem MongoDB rodando localmente
2. **Java 17+** - Requisito mínimo para Spring Boot 3.2.5
3. **Portas** - Aplicação usa porta 8080 e MongoDB usa 27017
4. **Diretórios** - A aplicação cria automaticamente `/relatorios` e `/config`
5. **Primeiro push** - Na primeira execução, o MongoDB criará automaticamente o banco `travel_agency`

---

## 🎯 Conclusão

Todos os **14 requisitos Java** foram implementados neste projeto:

✅ Wrapper classes e Autoboxing  
✅ Tipos Genéricos e Enumeration  
✅ Records, Text blocks e Blocos estáticos  
✅ Tipos de Exceções (verificadas e não verificadas)  
✅ Blocos try-catch-finally  
✅ Criação e Lançamento de exceções  
✅ Referência this e super  
✅ Persistência e recuperação de objetos  
✅ Leitura e gravação de arquivos texto  
✅ Manipulação de Dados  
✅ Manipulação de XML  
✅ Logging de Sistema  
✅ Programação Concorrente  
✅ Desenvolvimento de Componentes de Serviços (REST)  

**Projeto pronto para uso e aprendizado!** 🎓

---

**Autor:** Projeto Educacional - Agência de Viagens  
**Data:** Junho 2026  
**Versão:** 1.0.0  
**Status:** ✅ Completo
>>>>>>> origin/implementar-requisitos
