ocument.addEventListener('DOMContentLoaded', () => {
    const profileButton = document.getElementById('profileButton');
    const profileMenu = document.getElementById('profileMenu');

    // Alterna o menu ao clicar no botão de perfil
    profileButton.addEventListener('click', (e) => {
        e.stopPropagation(); // Impede que o clique feche o menu imediatamente
        profileMenu.classList.toggle('open');
    });

    // Fecha o menu se clicar fora
    document.addEventListener('click', (e) => {
        const clickedInside = profileMenu.contains(e.target) || profileButton.contains(e.target);
        if (!clickedInside) {
            profileMenu.classList.remove('open');
        }
    });
});
