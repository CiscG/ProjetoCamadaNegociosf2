# 🚀 GUIA RÁPIDO DE EXECUÇÃO

## ⚡ 3 Passos para Rodar o Projeto

### Pré-requisito: Ter MongoDB rodando

```bash
# Windows
net start MongoDB

# Linux/Mac
brew services start mongodb-community

# WSL
sudo service mongod start

# Verificar conexão
mongosh
```

---

## 1️⃣ OPÇÃO A: Via Maven (Recomendado)

```bash
# Entrar no diretório do projeto
cd ProjetoCamadaNegociosf2

# Fazer checkout da branch
git checkout implementar-requisitos

# Instalar dependências e compilar
mvn clean install

# Executar a aplicação
mvn spring-boot:run
```

**Tempo estimado:** 2-3 minutos na primeira vez

---

## 2️⃣ OPÇÃO B: Via JAR (Mais rápido)

```bash
# Compilar e gerar JAR
mvn clean package

# Executar JAR
java -jar target/airbnb-clone-1.0.0.jar
```

---

## 3️⃣ OPÇÃO C: Via IDE (Visual Studio Code / IntelliJ)

1. Abra o projeto na IDE
2. Clique em "Run" ou pressione `F5`
3. Selecione `TravelAgencyApplication`
4. IDE automaticamente irá:
   - Instalar dependências
   - Compilar código
   - Executar aplicação

---

## ✅ Como Saber que Funcionou

Busque no console por:

```
╔════════════════════════════════════════════════════════════════════════════════╗
║                   🌍 AGÊNCIA DE VIAGENS - TRAVEL AGENCY 🌍                    ║
║                   Sistema de Gerenciamento v1.0                               ║
╚════════════════════════════════════════════════════════════════════════════════╝
```

E depois:

```
.   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.5)

[main] Tomcat started on port(s): 8080 (http)
[main] Started TravelAgencyApplication in X.XXX seconds
```

**Pronto! ✅ Aplicação está rodando em http://localhost:8080**

---

## 🧪 Testar API

### Via cURL (Terminal)

```bash
# 1. Criar uma viagem
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

# 2. Listar todas as viagens
curl http://localhost:8080/api/viagens

# 3. Exportar em XML
curl http://localhost:8080/api/viagens/export/xml

# 4. Gerar relatório
curl -X POST http://localhost:8080/api/viagens/relatorio
```

### Via Postman

1. Baixar Postman: https://www.postman.com/downloads/
2. Criar nova Request
3. URL: `http://localhost:8080/api/viagens`
4. Body (JSON):
```json
{
  "destino": "Rio de Janeiro",
  "tipo": "TURISMO_PRAIA",
  "dataPartida": "2026-07-15",
  "dataRetorno": "2026-07-22",
  "preco": 2500.00,
  "vagas": 20,
  "descricao": "Viagem para as praias do Rio"
}
```

---

## 📊 Endpoints Principais

### Viagens
```
GET    http://localhost:8080/api/viagens              # Listar
GET    http://localhost:8080/api/viagens/{id}         # Obter por ID
POST   http://localhost:8080/api/viagens              # Criar
PUT    http://localhost:8080/api/viagens/{id}         # Atualizar
DELETE http://localhost:8080/api/viagens/{id}         # Deletar
GET    http://localhost:8080/api/viagens/export/xml   # Exportar XML
POST   http://localhost:8080/api/viagens/relatorio    # Gerar TXT
```

### Clientes
```
GET    http://localhost:8080/api/clientes             # Listar
GET    http://localhost:8080/api/clientes/{id}        # Obter por ID
POST   http://localhost:8080/api/clientes             # Criar
PUT    http://localhost:8080/api/clientes/{id}        # Atualizar
DELETE http://localhost:8080/api/clientes/{id}        # Deletar
```

---

## 🎯 Requisitos Testáveis

✅ **Wrapper Classes** → Ver Cliente.java (Integer, Double, Boolean, Long)  
✅ **Genéricos** → Ver ViagemRepository (List<Viagem>)  
✅ **Enumeration** → Ver TipoViagem.java  
✅ **Exceções** → Tentar criar viagem com datas inválidas  
✅ **Try-Catch-Finally** → Ver ViagemService.java  
✅ **XML** → GET /api/viagens/export/xml  
✅ **Arquivo Texto** → POST /api/viagens/relatorio  
✅ **Logging** → Observe o console  
✅ **Concorrência** → GET /api/viagens/async/{id}  
✅ **REST** → Todos os endpoints acima  

---

## 🔧 Troubleshooting Rápido

| Problema | Solução |
|----------|----------|
| "Connection refused" | MongoDB não está rodando |
| "Port 8080 already in use" | Altere em application.properties: `server.port=8081` |
| "Cannot find MongoDB" | Instale: https://www.mongodb.com/try/download/community |
| Maven não encontrado | Instale Maven ou use `./mvnw` em vez de `mvn` |
| Java não reconhecido | Instale Java 17+: https://www.oracle.com/java/technologies/downloads/ |

---

## 📁 Arquivos Gerados

Ao executar, a aplicação cria:

```
relatorios/
├── relatorio_viagens.txt      # Relatório em texto
├── viagens_export.xml         # Exportação em XML
└── operacoes.log              # Log de operações
```

---

## 📚 Estrutura do Projeto

```
ProjetoCamadaNegociosf2/
├── src/main/java/com/travelagency/
│   ├── TravelAgencyApplication.java      # Classe main
│   ├── model/                            # Modelos (Viagem, Cliente, Reserva)
│   ├── exception/                        # Exceções (5 classes)
│   ├── repository/                       # Acesso a dados (MongoDB)
│   ├── service/                          # Lógica de negócio
│   ├── controller/                       # REST endpoints
│   └── util/                             # Utilitários (Logger, Arquivo, XML)
│
├── src/main/resources/
│   └── application.properties             # Configuração MongoDB
│
├── pom.xml                                # Dependências Maven
└── README.md                              # Documentação completa
```

---

## 🎓 Para Aprender Mais

Ver README.md para:
- Documentação detalhada de cada requisito
- Exemplos de código
- Diagramas de fluxo
- Stack tecnológico completo
- Endpoints documentados

---

## ✨ Pronto!

Agora é só testar! 🚀

```bash
mvn clean install && mvn spring-boot:run
```

**Sucesso!** 🎉
