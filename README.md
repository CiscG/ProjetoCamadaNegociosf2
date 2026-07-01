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
- **Estilização:** Tailwind CSS 3
- **Roteamento:** React Router v6
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
cd ~/ProjetoCamadaNegociosf2

# Compile e execute
mvn clean install -DskipTests
java -jar target/airbnb-clone-1.0.0.jar
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
cd ~/ProjetoCamadaNegociosf2/FrontEnd
npm install
npm run dev
```

Acesse **http://localhost:5173** no navegador.

> O Vite redireciona automaticamente as chamadas `/api` para o backend na porta 5000.

---

## 🏭 Como rodar (produção)

Gere o build do frontend e sirva tudo pelo Spring Boot:

```bash
cd ~/ProjetoCamadaNegociosf2/FrontEnd
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
| **Login/Logout** | Autenticação por email/senha com hash bcrypt |
| **Busca de imóveis** | Filtro por cidade, preço, avaliação |
| **Reserva de imóvel** | Criação com verificação de conflito de datas |
| **Disponibilidade** | Visualização de períodos ocupados |
| **Dashboard** | Reservas como hóspede e imóveis como anfitrião |
| **Cadastro de imóvel** | Apenas para perfis `anfitriao` e `ambos` |
| **Relatórios** | Exportação XML e TXT |
| **CLI** | Interface de linha de comando interativa |
| **Toast Notifications** | Sistema de notificações em tempo real |

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

### Propriedades (Imóveis)
| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/propriedades` | Listar imóveis (`cidade`, `preco_max`, `anfitriao_id`) |
| `GET` | `/api/propriedades/{id}` | Obter imóvel por ID |
| `POST` | `/api/propriedades` | Cadastrar novo imóvel |
| `PUT` | `/api/propriedades/{id}` | Atualizar imóvel |
| `DELETE` | `/api/propriedades/{id}` | Deletar imóvel |
| `GET` | `/api/propriedades/{id}/ocupacao` | Períodos ocupados |

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
ProjetoCamadaNegociosf2/
├── src/main/java/com/airbnbclone/
│   ├── AirbnbCloneApplication.java        # Aplicação principal (Spring Boot)
│   ├── controller/                        # Controladores REST
│   │   ├── PropriedadeController.java
│   │   ├── ReservaController.java
│   │   ├── UsuarioController.java
│   │   └── AuthController.java
│   ├── service/                           # Lógica de negócio
│   │   ├── PropriedadeService.java
│   │   ├── ReservaService.java
│   │   ├── UsuarioService.java
│   │   └── AuthService.java
│   ├── repository/                        # Acesso a dados (MongoDB)
│   │   ├── PropriedadeRepository.java
│   │   ├── ReservaRepository.java
│   │   ├── UsuarioRepository.java
│   │   └── EnderecoRepository.java
│   ├── model/                             # Entidades
│   │   ├── Usuario.java
│   │   ├── Propriedade.java
│   │   ├── Reserva.java
│   │   └── Endereco.java
│   ├── dto/                               # Data Transfer Objects
│   │   ├── UsuarioRequest.java
│   │   ├── PropriedadeRequest.java
│   │   ├── PropriedadeResponse.java
│   │   ├── ReservaRequest.java
│   │   └── ReservaResponse.java
│   ├── util/                              # Utilitários
│   │   ├── PasswordEncoder.java
│   │   └── DateUtil.java
│   ├── seed/                              # Dados iniciais
│   │   └── SeedDataLoader.java
│   └── config/                            # Configurações
│       └── CorsConfig.java
├── src/main/resources/
│   └── application.properties             # Configurações Spring Boot
├── pom.xml                                # Dependências Maven
├── FrontEnd/                              # Aplicação React
│   ├── src/
│   │   ├── pages/                         # Páginas
│   │   │   ├── HomePage.jsx               # Listar imóveis
│   │   │   ├── LoginPage.jsx              # Autenticação
│   │   │   ├── NewListingPage.jsx         # Criar imóvel
│   │   │   ├── ReservationPage.jsx        # Fazer reserva
│   │   │   └── DashboardPage.jsx          # Painel do usuário
│   │   ├── components/                    # Componentes React
│   │   │   ├── Header.jsx
│   │   │   ├── SearchBar.jsx
│   │   │   ├── ListingCard.jsx
│   │   │   ├── ReservationForm.jsx
│   │   │   ├── Footer.jsx
│   │   │   └── Toast.jsx
│   │   ├── context/                       # Context API
│   │   │   ├── AuthContext.jsx
│   │   │   └── ToastContext.jsx
│   │   ├── api/                           # Integração com backend
│   │   │   └── client.js
│   │   ├── App.jsx                        # Componente principal
│   │   ├── main.jsx                       # Entry point
│   │   └── index.css                      # Tailwind + estilos globais
│   ├── package.json
│   ├── vite.config.js
│   └── tailwind.config.js
└── README.md                              # Este arquivo
```

---

## 🛠️ Stack Técnico

### Backend
- Spring Boot 3.2.5
- MongoDB (NoSQL)
- Java Streams & Collections
- Annotations & Reflection
- Exception Handling (try-catch-finally)
- Record Classes
- Generics

### Frontend
- React 19 com Hooks
- React Router v6
- Context API
- Vite (build tool)
- Tailwind CSS 3
- Fetch API

---

## 🧪 Testes

Execute os testes com:

```bash
mvn test
```

---

## 📝 Variáveis de Ambiente

Você pode customizar as configurações via `application.properties`:

```properties
# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/airbnb_clone

# Server
server.port=5000

# Logging
logging.level.com.airbnbclone=INFO
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
- Limpe o cache do navegador (Ctrl+Shift+Delete ou Cmd+Shift+Delete)
- Revise o console do navegador para erros de CORS
- Verifique se o proxy Vite está configurado corretamente

### Erro "Campos obrigatórios ausentes"
- Verifique se todos os campos obrigatórios estão preenchidos no formulário
- Recarregue a página para garantir que a versão mais recente do código está sendo usada
- Limpe o cache do Vite: `rm -rf node_modules/.vite`

---

## 📄 Licença

Projeto acadêmico — livre para uso educacional.

---

## 👥 Autores

- **Francisco Sousa** (CiscG) - Desenvolvimento Full Stack

---

## 🔗 Links úteis

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [MongoDB Docs](https://docs.mongodb.com/)
- [React Documentation](https://react.dev)
- [Vite Guide](https://vitejs.dev/guide/)
- [Tailwind CSS](https://tailwindcss.com)
- [React Router](https://reactrouter.com)

---

## 📆 Última atualização

**01/07/2026** - Projeto em desenvolvimento ativo ✅
- ✅ Autenticação funcionando
- ✅ Reservas funcionando
- ✅ Criação de propriedades em desenvolvimento
- ✅ Toast notifications implementadas
- ✅ Dashboard do anfitrião em desenvolvimento

---

**Airbnb Clone - Pronto para usar! 🚀**
