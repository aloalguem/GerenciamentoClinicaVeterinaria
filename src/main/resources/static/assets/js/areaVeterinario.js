// Variáveis globais para o calendário
let currentDay = new Date();
let selectedDate = new Date(); // Data atualmente selecionada na agenda

document.addEventListener('DOMContentLoaded', () => {
    // Inicializa o calendário
    renderCalendar(currentDay);
    // Renderiza as seções iniciais (Pacientes e Serviços)
    renderPatients();
    renderServices();

    // Configuração de Event Listeners
    setupNavigation();
    setupCalendarButtons();
    setupServiceModalListeners();
    setupPatientModalListeners();
    setupThemeToggle();

    // Garante que o agendamento inicial seja exibido (Hoje)
    displayAppointmentsForSelectedDate(selectedDate);
});

// Armazena o tema no localStorage
function setupThemeToggle() {
    const body = document.body;
    const themeButton = document.getElementById('toggle-theme-btn');
    const themeLabel = document.getElementById('theme-label');

    // Carrega o tema salvo
    const savedTheme = localStorage.getItem('theme') || 'light';
    if (savedTheme === 'dark') {
        body.classList.add('dark-theme');
        themeLabel.textContent = 'Tema Escuro';
    } else {
        themeLabel.textContent = 'Tema Claro';
    }

    themeButton.addEventListener('click', (e) => {
        e.preventDefault();
        if (body.classList.contains('dark-theme')) {
            body.classList.remove('dark-theme');
            localStorage.setItem('theme', 'light');
            themeLabel.textContent = 'Tema Claro';
        } else {
            body.classList.add('dark-theme');
            localStorage.setItem('theme', 'dark');
            themeLabel.textContent = 'Tema Escuro';
        }
    });
}


function setupNavigation() {
    const navLinks = document.querySelectorAll('.nav-link');
    const sections = document.querySelectorAll('.content-section');

    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const target = e.currentTarget.getAttribute('data-target');

            // Atualiza o estado "active" dos links
            navLinks.forEach(l => l.classList.remove('active'));
            e.currentTarget.classList.add('active');

            // Mostra/Esconde as seções
            sections.forEach(section => {
                section.classList.add('hidden');
                if (section.id === target) {
                    section.classList.remove('hidden');
                }
            });
        });
    });
}

/**
 * Abre um modal específico.
 * @param {string} id O ID do elemento modal.
 */
function openModal(id) {
    const modal = document.getElementById(id);
    if (modal) {
        modal.classList.remove('hidden');
    }
}

/**
 * Fecha um modal específico.
 * @param {string} id O ID do elemento modal.
 */
function closeModal(id) {
    const modal = document.getElementById(id);
    if (modal) {
        modal.classList.add('hidden');
        // Limpa o formulário
        const form = modal.querySelector('form');
        if(form) form.reset();
    }
}

// Fecha o modal ao clicar fora
document.querySelectorAll('.modal-overlay').forEach(overlay => {
    overlay.addEventListener('click', (e) => {
        if (e.target === overlay) {
            closeModal(overlay.id);
        }
    });
});

// =======================================
// LÓGICA DO CALENDÁRIO
// =======================================

const monthNames = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];

function getAppointmentsInMonth(year, month) {
    // Filtra os agendamentos que caem no mês e ano fornecidos
    const dateString = `${year}-${String(month + 1).padStart(2, '0')}`;
    return currentAppointments
        .filter(app => app.date.startsWith(dateString))
        .map(app => new Date(app.date).getDate());
}

function renderCalendar(date) {
    const year = date.getFullYear();
    const month = date.getMonth();

    // Datas com agendamentos
    const datesWithAppointments = getAppointmentsInMonth(year, month);

    document.getElementById('calendar-month-year').textContent = `${monthNames[month]} ${year}`;
    const grid = document.getElementById('calendar-grid');
    grid.innerHTML = '';

    const firstDayOfMonth = new Date(year, month, 1).getDay(); // 0 (Dom) a 6 (Sáb)
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    const today = new Date();
    const todayDate = today.getDate();
    const isCurrentMonth = today.getMonth() === month && today.getFullYear() === year;

    // Preenche os espaços vazios (dias do mês anterior)
    for (let i = 0; i < firstDayOfMonth; i++) {
        const emptyDay = document.createElement('div');
        emptyDay.classList.add('calendar-day', 'empty');
        grid.appendChild(emptyDay);
    }

    // Preenche os dias do mês
    for (let day = 1; day <= daysInMonth; day++) {
        const dayElement = document.createElement('div');
        dayElement.classList.add('calendar-day');
        dayElement.textContent = day;

        // Marca o dia de hoje
        if (isCurrentMonth && day === todayDate) {
            dayElement.classList.add('today');
            // Se for o mês atual, seleciona o dia de hoje por padrão se nenhuma data foi selecionada ainda
            if (selectedDate.getMonth() === month && selectedDate.getDate() === day) {
                dayElement.classList.add('selected');
            }
        }

        // Verifica se este dia tem agendamento
        if (datesWithAppointments.includes(day)) {
            dayElement.classList.add('has-appointment');
        }

        // Marca o dia selecionado
        if (selectedDate.getFullYear() === year && selectedDate.getMonth() === month && selectedDate.getDate() === day) {
            dayElement.classList.add('selected');
        }


        dayElement.setAttribute('data-date', `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`);
        dayElement.addEventListener('click', handleDayClick);
        grid.appendChild(dayElement);
    }
}

function setupCalendarButtons() {
    document.getElementById('prev-month-btn').addEventListener('click', () => {
        currentDay.setMonth(currentDay.getMonth() - 1);
        renderCalendar(currentDay);
    });

    document.getElementById('next-month-btn').addEventListener('click', () => {
        currentDay.setMonth(currentDay.getMonth() + 1);
        renderCalendar(currentDay);
    });
}

function handleDayClick(e) {
    const dateString = e.currentTarget.getAttribute('data-date');

    // Remove a seleção de todos os dias
    document.querySelectorAll('.calendar-day.selected').forEach(d => d.classList.remove('selected'));

    // Adiciona a seleção ao dia clicado
    e.currentTarget.classList.add('selected');

    // Atualiza a data selecionada globalmente
    selectedDate = new Date(dateString);

    displayAppointmentsForSelectedDate(selectedDate);
}

function displayAppointmentsForSelectedDate(date) {
    const formattedDate = date.toISOString().split('T')[0]; // YYYY-MM-DD
    const displayDate = `${String(date.getDate()).padStart(2, '0')}/${String(date.getMonth() + 1).padStart(2, '0')}/${date.getFullYear()}`;

    document.getElementById('appointments-title').textContent = `Agendamentos para ${displayDate}`;
    const listContainer = document.getElementById('appointments-list');
    listContainer.innerHTML = '';

    const appointmentsToday = currentAppointments.filter(app => app.date === formattedDate);

    if (appointmentsToday.length === 0) {
        listContainer.innerHTML = `<div class="card text-default opacity-70">Nenhum agendamento para esta data.</div>`;
        return;
    }

    appointmentsToday.sort((a, b) => (a.time > b.time) ? 1 : -1);

    appointmentsToday.forEach(app => {
        const statusClass = app.status === 'Confirmado' ? 'bg-accent text-contrast' : 'bg-yellow-100 text-yellow-800';

        const appointmentCard = `
                <div class="card p-4 flex justify-between items-center border-l-4 ${app.status === 'Confirmado' ? 'border-heading' : 'border-yellow-500'}">
                    <div>
                        <p class="text-lg font-semibold text-heading">${app.pet} (${app.service})</p>
                        <p class="text-sm text-default opacity-80">Tutor: ${app.tutor}</p>
                        <p class="text-sm font-bold text-default">${app.time}</p>
                    </div>
                    <span class="badge ${statusClass} text-xs font-bold uppercase py-1 px-3 rounded-full">${app.status}</span>
                </div>
            `;
        listContainer.innerHTML += appointmentCard;
    });
}

// =======================================
// LÓGICA DE PACIENTES
// =======================================

function renderPatients() {
    const listContainer = document.getElementById('patients-list');
    listContainer.innerHTML = '';

    if (currentPatients.length === 0) {
        document.getElementById('no-patients-message').classList.remove('hidden');
        return;
    }
    document.getElementById('no-patients-message').classList.add('hidden');

    currentPatients.forEach(patient => {
        const patientCard = `
                <div class="patient-card">
                    <h4 class="text-accent">${patient.name}</h4>
                    <p class="text-sm text-default opacity-90">Tutor: ${patient.tutor}</p>
                    <p class="text-sm text-default opacity-90">Raça: ${patient.breed}</p>
                    <p class="text-sm text-default opacity-90">Idade: ${patient.age} anos</p>
                    <span class="badge">${patient.species}</span>
                    <button class="primary-button mt-4 w-full text-xs" onclick="viewPatientDetails(${patient.id})">Ver Histórico</button>
                </div>
            `;
        listContainer.innerHTML += patientCard;
    });
}

function viewPatientDetails(id) {
    // Simulação de ação de ver detalhes/histórico
    const patient = currentPatients.find(p => p.id === id);
    if (patient) {
        alert(`Simulando visualização do Histórico Clínico de: ${patient.name} (Tutor: ${patient.tutor})`);
    }
}


function setupPatientModalListeners() {
    document.getElementById('add-patient-btn').addEventListener('click', () => {
        document.getElementById('patient-modal-title').textContent = 'Adicionar Paciente';
        openModal('patient-modal');
    });

    document.getElementById('patient-form').addEventListener('submit', handlePatientSubmit);
}

function handlePatientSubmit(e) {
    e.preventDefault();

    const form = e.target;
    const newPatient = {
        id: Date.now(),
        name: form.elements['patient-name'].value,
        tutor: form.elements['patient-tutor'].value,
        species: form.elements['patient-species'].value,
        breed: form.elements['patient-breed'].value,
        age: parseInt(form.elements['patient-age'].value),
    };

    currentPatients.push(newPatient);
    renderPatients();
    closeModal('patient-modal');
    alert(`Paciente ${newPatient.name} adicionado com sucesso!`);
}

// =======================================
// LÓGICA DE SERVIÇOS
// =======================================

function renderServices() {
    const listContainer = document.getElementById('services-list');
    listContainer.innerHTML = '';

    if (currentServices.length === 0) {
        document.getElementById('no-services-message').classList.remove('hidden');
        return;
    }
    document.getElementById('no-services-message').classList.add('hidden');

    currentServices.forEach(service => {
        const serviceItem = `
                <div class="flex justify-between items-center p-3 border-base border rounded-lg bg-white dark:bg-gray-700">
                    <div>
                        <p class="font-semibold text-heading">${service.name}</p>
                        <p class="text-sm text-default opacity-80">R$ ${service.price.toFixed(2).replace('.', ',')}</p>
                    </div>
                    <div class="flex gap-2">
                        <button class="secondary-button text-xs py-1 px-3" onclick="editService(${service.id})">Editar</button>
                        <button class="bg-red-500 hover:bg-red-600 text-white text-xs py-1 px-3 rounded-full transition" onclick="deleteService(${service.id})">Excluir</button>
                    </div>
                </div>
            `;
        listContainer.innerHTML += serviceItem;
    });
}

function setupServiceModalListeners() {
    document.getElementById('add-service-btn').addEventListener('click', () => {
        document.getElementById('service-modal-title').textContent = 'Adicionar Serviço';
        document.getElementById('service-id').value = '';
        openModal('service-modal');
    });

    document.getElementById('service-form').addEventListener('submit', handleServiceSubmit);
}

function editService(id) {
    const service = currentServices.find(s => s.id === id);
    if (service) {
        document.getElementById('service-modal-title').textContent = 'Editar Serviço';
        document.getElementById('service-id').value = service.id;
        document.getElementById('service-name').value = service.name;
        document.getElementById('service-price').value = service.price;
        openModal('service-modal');
    }
}

function deleteService(id) {
    if (confirm("Tem certeza que deseja excluir este serviço?")) {
        currentServices = currentServices.filter(s => s.id !== id);
        renderServices();
        alert("Serviço excluído com sucesso.");
    }
}

function handleServiceSubmit(e) {
    e.preventDefault();

    const form = e.target;
    const id = form.elements['service-id'].value;
    const name = form.elements['service-name'].value;
    const price = parseFloat(form.elements['service-price'].value);

    if (id) {
        // Edição
        const index = currentServices.findIndex(s => s.id == id);
        if (index !== -1) {
            currentServices[index] = { ...currentServices[index], name, price };
            alert(`Serviço ${name} atualizado com sucesso!`);
        }
    } else {
        // Novo Serviço
        const newService = { id: Date.now(), name, price };
        currentServices.push(newService);
        alert(`Serviço ${name} adicionado com sucesso!`);
    }

    renderServices();
    closeModal('service-modal');
}

// Substitui window.confirm e window.alert por funções que usam o padrão alert() do JS,
// mas com um aviso no console sobre o uso em iframe.
const originalAlert = window.alert;
const originalConfirm = window.confirm;

window.alert = function(message) {
    console.warn("AVISO: Usando alert() nativo. Em produção, use um modal customizado.");
    originalAlert(message);
};
window.confirm = function(message) {
    console.warn("AVISO: Usando confirm() nativo. Em produção, use um modal customizado.");
    return originalConfirm(message);
};
