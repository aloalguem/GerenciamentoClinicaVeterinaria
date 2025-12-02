    document.addEventListener('DOMContentLoaded', () => {
    const carouselItems = document.getElementById('carousel-items');
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');
    const cards = document.querySelectorAll('.card-wrapper');
    const itemsPerView = 3;
    const totalItems = cards.length;

    // Clona os primeiros e últimos cards para criar o loop
    const firstClones = Array.from(cards).slice(0, itemsPerView).map(node => node.cloneNode(true));
    const lastClones = Array.from(cards).slice(-itemsPerView).map(node => node.cloneNode(true));

    // Adiciona os clones no início e no final do container
    lastClones.reverse().forEach(clone => carouselItems.prepend(clone));
    firstClones.forEach(clone => carouselItems.append(clone));

    let currentIndex = itemsPerView; // Começa no primeiro card real
    let isAnimating = false; // Flag para evitar cliques durante a animação

    // Função para atualizar a posição do carrossel
    function updateCarousel() {
    const cardWidth = cards[0].getBoundingClientRect().width;
    carouselItems.style.transform = `translateX(-${currentIndex * cardWidth}px)`;
}

    // Evento para o botão "Próximo"
    nextBtn.addEventListener('click', () => {
    if (isAnimating) return;
    isAnimating = true;

    currentIndex++;
    updateCarousel();
});

    // Evento para o botão "Anterior"
    prevBtn.addEventListener('click', () => {
    if (isAnimating) return;
    isAnimating = true;

    currentIndex--;
    updateCarousel();
});

    // Evento de transição para lidar com o loop contínuo
    carouselItems.addEventListener('transitionend', () => {
    // Checa se estamos em um dos cards clonados
    if (currentIndex === 0) {
    // Se estiver no primeiro clone, pule para o último card real sem animação
    carouselItems.style.transition = 'none';
    currentIndex = totalItems;
    updateCarousel();
} else if (currentIndex === totalItems + itemsPerView) {
    // Se estiver no último clone, pule para o primeiro card real sem animação
    carouselItems.style.transition = 'none';
    currentIndex = itemsPerView;
    updateCarousel();
}

    // Reabilita a animação após o "salto"
    setTimeout(() => {
    carouselItems.style.transition = 'transform 0.5s ease-in-out';
    isAnimating = false;
}, 50);
});

    // Chama a função inicial para posicionar o carrossel corretamente
    updateCarousel();

    // Adiciona um listener para o redimensionamento da janela, caso o layout mude
    window.addEventListener('resize', updateCarousel);
});