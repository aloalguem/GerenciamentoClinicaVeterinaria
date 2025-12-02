    // Lógica para simular carregamento infinito (placeholder)
    const infiniteScrollContainer = document.getElementById('infinite-scroll-container');
    const loadingIndicator = document.getElementById('loading-indicator');
    let itemCount = 20; // Número inicial de itens
    let isLoading = false; // Flag para evitar carregamentos simultâneos

    function loadMoreContent() {
    if (isLoading) return; // Impede o carregamento se já estiver carregando
    isLoading = true;

    // Mostrar indicador de carregamento
    loadingIndicator.classList.remove('hidden');

    // Simular um atraso de carregamento
    setTimeout(() => {
    const fragment = document.createDocumentFragment();
    for (let i = 0; i < 10; i++) { // Adiciona 10 novos itens
    itemCount++;
    const newItem = document.createElement('div');
    newItem.classList.add('content-block');
    newItem.textContent = `Item de Conteúdo ${itemCount}`;
    fragment.appendChild(newItem);
}
    infiniteScrollContainer.appendChild(fragment);

    // Esconder indicador de carregamento
    loadingIndicator.classList.add('hidden');
    isLoading = false; // Resetar a flag de carregamento
}, 1000); // Carrega mais conteúdo após 1 segundo
}

    // Adicionar um listener de rolagem para detectar quando o usuário chega ao final
    window.addEventListener('scroll', () => {
    // Verifica se o usuário rolou até o final da página (ou próximo ao final)
    // O limite de 500px é uma prática comum para iniciar o carregamento antes do fim da página
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 500) {
    // Se o indicador de carregamento não estiver visível (ou seja, não está carregando no momento), carrega mais conteúdo
    if (loadingIndicator.classList.contains('hidden')) {
    loadMoreContent();
}
}
});