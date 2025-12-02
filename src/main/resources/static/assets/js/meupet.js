// =======================================
// FUNÇÕES JAVASCRIPT PARA MODAIS E ALERTA
// =======================================

// 1. Controle de Modal
document.addEventListener('DOMContentLoaded', () => {
    // Encontra todos os botões que abrem modais
    const openModalBtns = document.querySelectorAll('[data-modal-target]');

    openModalBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            const targetId = btn.getAttribute('data-modal-target');
            openModal(targetId);
        });
    });

    // Adiciona funcionalidade de fechar modal ao clicar fora
    document.querySelectorAll('.app-modal').forEach(modal => {
        modal.addEventListener('click', (e) => {
            // Verifica se o clique foi no background do modal (e não dentro do modal-dialog)
            if (e.target.classList.contains('app-modal')) {
                modal.classList.remove('is-visible');
            }
        });
    });
});

/**
 * Abre um modal específico.
 * @param {string} id O ID do elemento modal.
 */
function openModal(id) {
    const modal = document.getElementById(id);
    if (modal) {
        modal.classList.add('is-visible');
    }
}

/**
 * Fecha um modal específico.
 * @param {string} id O ID do elemento modal.
 */
function closeModal(id) {
    const modal = document.getElementById(id);
    if (modal) {
        modal.classList.remove('is-visible');
    }
}

// 2. Simulação de Submissão de Formulário (e Alerta)

/**
 * Simula a submissão de um formulário e mostra uma mensagem de status.
 * @param {Event} event O evento de submissão do formulário.
 * @param {string} message A mensagem de sucesso a ser exibida.
 */
function handleFormSubmit(event, message) {
    event.preventDefault(); // Impede o envio real do formulário

    // Simulação de Validação (Apenas para demonstração de erro)
    const form = event.target;
    const nomeInput = form.querySelector('[name="nome"]');

    // Se o nome estiver vazio (exemplo de erro para o modal de novo pet)
    if (nomeInput && nomeInput.closest('#modalNovoPet') && nomeInput.value.trim() === "") {
        // Mostra a mensagem de erro simulada abaixo do campo.
        const errorSpan = nomeInput.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('text-danger-custom')) {
            errorSpan.style.display = 'block';
        }
        showStatusMessage('Preencha todos os campos obrigatórios.', 'danger');
        return;
    } else if (nomeInput) {
        // Esconde o erro se ele foi corrigido
        const errorSpan = nomeInput.nextElementSibling;
        if (errorSpan && errorSpan.classList.contains('text-danger-custom')) {
            errorSpan.style.display = 'none';
        }
    }

    // Se a validação passar (sucesso)
    const modalId = event.target.closest('.app-modal').id;
    closeModal(modalId);

    showStatusMessage(message, 'success');

    event.target.reset(); // Limpa os campos
}

/**
 * Simula a confirmação de cancelamento e mostra um alerta.
 * @param {Event} event O evento de clique.
 */
function handleCancel(event) {
    event.preventDefault();

    // Substituindo o antigo window.confirm() por um alerta visual
    if (confirm('Tem certeza que deseja cancelar este agendamento?')) {
        showStatusMessage('Agendamento cancelado com sucesso.', 'danger');
    }
}

/**
 * Exibe uma mensagem de status na UI.
 * @param {string} text O texto da mensagem.
 * @param {string} type O tipo de mensagem (success ou danger).
 */
function showStatusMessage(text, type) {
    const statusElement = document.getElementById('status-message');
    const messageSpan = statusElement.querySelector('span');

    statusElement.style.display = 'block';
    messageSpan.textContent = text;

    // Adapta o estilo do alerta
    if (type === 'success') {
        statusElement.style.backgroundColor = '#d4edda';
        statusElement.style.color = '#155724';
        statusElement.style.border = '1px solid #c3e6cb';
    } else if (type === 'danger') {
        statusElement.style.backgroundColor = '#f8d7da';
        statusElement.style.color = '#721c24';
        statusElement.style.border = '1px solid #f5c6cb';
    }

    // Esconde a mensagem após 5 segundos
    setTimeout(() => {
        statusElement.style.display = 'none';
    }, 5000);
}

// Fallback para o alert/confirm (Para evitar erros no console do ambiente,
// mas lembrando que em apps Canvas não usamos window.confirm/alert)
if (typeof confirm === 'undefined') {
    window.confirm = (message) => {
        console.log("Simulação de Confirmação: " + message);
        return true; // Assume 'sim' para a simulação
    };
}