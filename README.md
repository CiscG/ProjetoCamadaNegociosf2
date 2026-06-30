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

---

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
```

Acesse **http://localhost:5000** no navegador.

---

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
│   ├── AirbnbCloneApplication.java        # Aplicação principal (Spring Boot)
│   ├── controller/                        # Controladores REST (Airbnb)
│   ├── service/                           # Lógica de negócio (Airbnb)
│   ├── repository/                        # Acesso a dados (MongoDB)
│   ├── model/                             # Entidades (Usuario, Local, Reserva)
│   ├── dto/                               # Data Transfer Objects
│   ├── util/                              # Utilitários
│   ├── seed/                              # Dados iniciais
│   └── cli/                               # Interface CLI
├── src/main/java/com/travelagency/
│   ├── TravelAgencyApplication.java       # Módulo de requisitos Java
│   ├── controller/                        # Controladores REST (Viagens/Clientes)
│   ├── service/                           # Serviços com try-catch-finally, concorrência
│   ├── repository/                        # Repositórios com tipos genéricos
│   ├── model/                             # Modelos (Viagem, Cliente, Reserva, TipoViagem)
│   ├── exception/                         # Exceções verificadas e não verificadas
│   ├── config/                            # Configuração CORS
│   └── util/                              # LoggerUtil, ArquivoUtil, XMLUtil
├── src/main/resources/
│   └── application.properties             # Configurações Spring Boot
├── pom.xml                                # Dependências Maven
├── FrontEnd/                              # Aplicação React
│   ├── src/
│   │   ├── pages/                         # Páginas
│   │   ├── components/                    # Componentes React
│   │   ├── context/                       # Context API (Auth, Toast)
│   │   ├── api/                           # Integração com backend
│   │   └── App.jsx
│   ├── package.json
│   └── vite.config.js
└── README.md                              # Este arquivo
```

---

## 🛠️ Requisitos de Java Implementados

O módulo `com.travelagency` demonstra a implementação de todos os 14 requisitos Java:

✅ Wrapper classes e Autoboxing (`model/Cliente.java`)
✅ Tipos Genéricos e Enumeration (`model/TipoViagem.java`, `repository/ViagemRepository.java`)
✅ Records, Text blocks e Blocos estáticos (`TravelAgencyApplication.java`)
✅ Tipos de Exceções verificadas e não verificadas (`exception/`)
✅ Blocos try-catch-finally (`service/ViagemService.java`)
✅ Criação e Lançamento de exceções (`service/ViagemService.java`)
✅ Referência this e super (`service/ClienteService.java`)
✅ Persistência e recuperação de objetos (`model/Viagem.java`, `model/Cliente.java`)
✅ Leitura e gravação de arquivos texto (`util/ArquivoUtil.java`)
✅ Manipulação de Dados (`service/ViagemService.java`)
✅ Manipulação de XML (`util/XMLUtil.java`)
✅ Logging de Sistema (`util/LoggerUtil.java`)
✅ Programação Concorrente (`service/ViagemService.java`)
✅ Desenvolvimento de Componentes de Serviços REST (`controller/`)

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
