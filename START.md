# 🚀 GUIA RÁPIDO - RODAR TUDO

## ⚡ 4 PASSOS

### 1️⃣ MongoDB (Terminal 0)
```bash
mongosh
# Deve conectar sem erro
```

### 2️⃣ Backend (Terminal 1)
```bash
cd ProjetoCamadaNegociosf2
git checkout implementar-requisitos
mvn clean install
mvn spring-boot:run
```
**Rodando em:** `http://localhost:8080`

### 3️⃣ Frontend (Terminal 2)
```bash
cd FrontEnd
npm install
npm run dev
```
**Rodando em:** `http://localhost:5173`

### 4️⃣ Abrir no Navegador
```
http://localhost:5173
```

---

## ✅ Pronto!

✓ Backend rodando  
✓ Frontend rodando  
✓ Conectados via API  
✓ MongoDB pronto  

---

## 🎯 Testar

1. **Login** com qualquer email
2. **Criar Viagem** - Menu "+ Nova Viagem"
3. **Gerenciar Clientes** - Menu "👥 Clientes"
4. **Buscar** - Filtro por destino

---

## 📚 Para Mais Detalhes

Ver: `GUIA_COMPLETO.md`
