# 🚀 Frontend - Agência de Viagens

## 📱 Sobre

Frontend React + Vite para gerenciamento de viagens e clientes, integrado 100% com a API REST em Spring Boot.

**Stack:**
- React 19.2.5
- Vite (Build tool)
- Tailwind CSS (Estilização)
- React Router (Navegação)
- Lucide React (Ícones)

---

## 🎯 Funcionalidades

✅ **Autenticação** - Login/Cadastro simples  
✅ **Listar Viagens** - Visualizar todas as viagens  
✅ **Criar Viagem** - Formulário completo com validações  
✅ **Editar/Deletar** - Gerenciar viagens  
✅ **Buscar por Destino** - Filtro em tempo real  
✅ **Gerenciar Clientes** - CRUD completo  
✅ **Notificações** - Toast system responsivo  
✅ **Interface Responsiva** - Mobile-friendly com Tailwind  

---

## 🚀 Como Rodar

### Pré-requisitos

- Node.js 18+
- npm ou yarn
- Backend rodando em `http://localhost:8080`

### Passos

```bash
# 1. Entrar na pasta FrontEnd
cd FrontEnd

# 2. Instalar dependências
npm install

# 3. Rodar servidor de desenvolvimento
npm run dev
```

**Frontend roda em:** `http://localhost:5173`

---

## 📁 Estrutura

```
FrontEnd/
├── src/
│   ├── api/
│   │   └── viagensApi.js          ← Client HTTP (chamadas à API)
│   ├── components/
│   │   ├── Header.jsx             ← Menu principal
│   │   ├── Toast.jsx              ← Notificação individual
│   │   └── ToastContainer.jsx     ← Container de notificações
│   ├── context/
│   │   ├── AuthContext.jsx        ← Contexto de autenticação
│   │   └── ToastContext.jsx       ← Contexto de notificações
│   ├── pages/
│   │   ├── LoginPage.jsx          ← Tela de login
│   │   ├── HomePage.jsx           ← Listar viagens
│   │   ├── NewListingPage.jsx     ← Criar viagem
│   │   └── DashboardPage.jsx      ← Gerenciar clientes
│   ├── App.jsx                    ← Componente principal
│   ├── main.jsx                   ← Entry point
│   └── index.css                  ← Tailwind + estilos globais
├── public/                        ← Assets estáticos
├── package.json
├── vite.config.js                 ← Configuração Vite
├── tailwind.config.js             ← Configuração Tailwind
└── index.html
```

---

## 🔌 Integração API

### Client HTTP (`src/api/viagensApi.js`)

Todas as requisições HTTP para o backend:

```javascript
// Viagens
api.getViagens()                    // GET /viagens
api.getViagemById(id)              // GET /viagens/{id}
api.createViagem(data)             // POST /viagens
api.updateViagem(id, data)         // PUT /viagens/{id}
api.deleteViagem(id)               // DELETE /viagens/{id}
api.buscarViagensPorDestino(dest)  // GET /viagens/destino/{destino}
api.exportarViagensXML()           // GET /viagens/export/xml
api.gerarRelatorioViagens()        // POST /viagens/relatorio

// Clientes
api.getClientes()                  // GET /clientes
api.getClienteById(id)             // GET /clientes/{id}
api.createCliente(data)            // POST /clientes
api.updateCliente(id, data)        // PUT /clientes/{id}
api.deleteCliente(id)              // DELETE /clientes/{id}
api.buscarClientePorEmail(email)   // GET /clientes/email/{email}
```

**Base URL:** `http://localhost:8080/api`

---

## 📄 Páginas

### 1. **LoginPage** (`/login`)
- Login simples por email
- Opção de cadastro
- Validação de campos

### 2. **HomePage** (`/`)
- Lista todas as viagens
- Busca por destino
- Editar/Deletar viagens
- Status de ativa/inativa
- Exibe preço, datas, vagas

### 3. **NewListingPage** (`/novo-viagem`)
- Criar nova viagem
- Campos: destino, tipo, datas, preço, vagas, descrição
- Validações completas
- Tipos de viagem enum

### 4. **DashboardPage** (`/clientes`)
- Listar todos os clientes
- Criar novo cliente
- Deletar cliente
- Exibe: nome, email, telefone, CPF, idade, renda

---

## 🎨 Estilização

**Tailwind CSS** com cores customizadas:
- Azul primário: `#2563EB` (blue-600)
- Verde sucesso: `#16A34A` (green-600)
- Vermelho erro: `#DC2626` (red-600)
- Cinza neutro: `#6B7280` (gray-500)

---

## 🔐 Autenticação

Usado `localStorage` para persistir usuário:

```javascript
const { user, login, logout } = useAuth()

// Login
login({ id: '123', email: 'user@email.com', nome: 'João' })

// Logout
logout()
```

Rotas protegidas com `<ProtectedRoute>`

---

## 🔔 Notificações (Toast)

```javascript
const { addToast } = useToast()

// Info
addToast('Mensagem', 'info')

// Success
addToast('Sucesso!', 'success')

// Error
addToast('Erro!', 'error')

// Warning
addToast('Atenção!', 'warning')
```

Duração padrão: 3 segundos (personalizável)

---

## 📦 Scripts

```bash
# Desenvolvimento
npm run dev

# Build para produção
npm run build

# Preview build
npm run preview

# Lint
npm run lint
```

---

## ⚠️ Troubleshooting

| Problema | Solução |
|----------|----------|
| "Cannot GET /" | Certifique-se que vite está rodando |
| "API Error" | Verifique se backend está em `http://localhost:8080` |
| CORS error | Backend deve ter CORS habilitado |
| Tailwind não funciona | Rode `npm install` novamente |

---

## 🔗 Comunicação com Backend

```
FrontEnd (React)          Backend (Spring Boot)
http://localhost:5173  →  http://localhost:8080/api
     ↓                                ↓
  Vite Dev Server            Spring Boot App
     ↓                                ↓
  React Router              MongoDB Database
```

---

## 📝 Exemplos de Uso

### Criar Viagem

```javascript
const form = {
  destino: 'Rio de Janeiro',
  tipo: 'TURISMO_PRAIA',
  dataPartida: '2026-07-15',
  dataRetorno: '2026-07-22',
  preco: 2500.00,
  vagas: 20,
  descricao: 'Viagem para as praias do Rio',
  ativa: true
}

await api.createViagem(form)
```

### Listar Viagens

```javascript
const response = await api.getViagens()
// response.viagens = Array<Viagem>
```

### Buscar por Destino

```javascript
const response = await api.buscarViagensPorDestino('Rio')
// response.viagens = Array<Viagem filtradas>
```

### Criar Cliente

```javascript
const cliente = {
  nome: 'João Silva',
  email: 'joao@email.com',
  telefone: '11999999999',
  cpf: '12345678900',
  idade: 35,
  renda: 5000.00
}

await api.createCliente(cliente)
```

---

## 🎓 Aprendizado

Este projeto demonstra:
- ✅ React Hooks (useState, useEffect, useContext)
- ✅ React Router para navegação
- ✅ Context API para estado global
- ✅ Fetch API para HTTP requests
- ✅ Tailwind CSS para estilização
- ✅ Form handling e validações
- ✅ Error handling com try-catch
- ✅ Componentes reutilizáveis

---

## 📄 Licença

Projeto Educacional - Agência de Viagens (2026)

---

**Frontend pronto para uso! 🚀**
