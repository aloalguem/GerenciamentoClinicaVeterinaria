document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    const response = await fetch('http://localhost:3000/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
    });

    const data = await response.json();
    if (response.ok) {
        // Armazenar o token JWT no LocalStorage
        localStorage.setItem('token', data.token);
        alert('Login bem-sucedido!');
        window.location.href = 'home/homepet.html'; // Redireciona para a página inicial após o login
    } else {
        alert(data.error);
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
  
    loginForm.addEventListener('submit', function(event) {
      event.preventDefault(); // Impede o envio padrão do formulário
  
      const email = document.getElementById('loginEmail').value;
      const password = document.getElementById('loginPassword').value;
  
      // Aqui você adicionaria a lógica de autenticação
      // Exemplo básico (NÃO USE EM PRODUÇÃO):
      if (email === 'teste@exemplo.com' && password === 'senha123') {
        alert('Login bem-sucedido!');
        // Redirecionar para a página principal
        // window.location.href = 'pagina-principal.html';
      } else {
        alert('Email ou senha incorretos.');
      }
    });
  });

document.getElementById("login-form").addEventListener("submit", function (e) {
    e.preventDefault();
    const email = document.getElementById("email").value;
    const senha = document.getElementById("password").value;

    if (email === "admin@teste.com" && senha === "1234") {
        alert("Login bem-sucedido!");
        localStorage.setItem("logado", "true");
        window.location.href = "home/homepet.html";
    } else {
        alert("E-mail ou senha inválidos.");
    }
});

// Mostrar recuperação de senha
document.getElementById("esqueci-senha-link").addEventListener("click", function (e) {
    e.preventDefault();
    document.getElementById("login-form").style.display = "none";
    document.getElementById("recuperar-container").style.display = "block";
});

// Voltar ao login
document.getElementById("voltar-login").addEventListener("click", function (e) {
    e.preventDefault();
    document.getElementById("recuperar-container").style.display = "none";
    document.getElementById("login-form").style.display = "block";
});

// Enviar código de verificação (simulado)
document.getElementById("recuperar-form").addEventListener("submit", function (e) {
    e.preventDefault();
    const recEmail = document.getElementById("rec-email").value;

    // Simulação simples de verificação
    if (recEmail === "") {
        alert("Por favor, insira um e-mail válido.");
    } else {
        alert("Um código de verificação foi enviado para " + recEmail);
        // Aqui você pode integrar com back-end real futuramente
    }
});