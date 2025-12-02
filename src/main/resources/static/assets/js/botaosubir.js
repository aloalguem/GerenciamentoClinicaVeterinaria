// Mostrar/ocultar botão ao rolar
window.addEventListener("scroll", function () {
    const btn = document.getElementById("btnTop");
    if (window.scrollY > 20) {
        btn.style.display = "block";
    } else {
        btn.style.display = "none";
    }
});

// Função para subir suavemente
function subirAoTopo() {
    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
}
