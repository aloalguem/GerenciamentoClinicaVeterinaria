document.addEventListener('DOMContentLoaded', function () {
    const steps = document.querySelectorAll('.step');
    let currentStep = 0;

    function mostrarEtapa(index) {
        steps.forEach((step, i) => {
            if (i === index) {
                step.classList.add('active');
                step.style.display = 'block';
            } else {
                step.classList.remove('active');
                step.style.display = 'none';
            }
        });
    }

    // Inicializa mostrando apenas a primeira etapa
    mostrarEtapa(currentStep);

    document.querySelectorAll('.voltar').forEach(btn => {
        btn.addEventListener('click', () => {
            if (currentStep > 0) {
                currentStep--;
                mostrarEtapa(currentStep);
            }
        });
    });

    document.getElementById('continuar-step1').addEventListener('click', () => {
        const email = document.getElementById('email').value.trim();
        const senha = document.getElementById('senha').value.trim();
        if (email && senha.length >= 6) {
            currentStep++;
            mostrarEtapa(currentStep);
        } else {
            alert('Preencha um e-mail válido e uma senha com pelo menos 6 caracteres.');
        }
    });

    document.getElementById('continuar-step2').addEventListener('click', () => {
        const dataNascimentoStr = document.getElementById('idade').value;
        if (dataNascimentoStr) {
            const hoje = new Date();
            const dataNascimento = new Date(dataNascimentoStr);
            let idade = hoje.getFullYear() - dataNascimento.getFullYear();
            const m = hoje.getMonth() - dataNascimento.getMonth();
            if (m < 0 || (m === 0 && hoje.getDate() < dataNascimento.getDate())) {
                idade--;
            }
            if (idade >= 12) {
                currentStep++;
                mostrarEtapa(currentStep);
            } else {
                alert('Você precisa ter pelo menos 12 anos para se registrar.');
            }
        } else {
            alert('Informe sua data de nascimento.');
        }
    });

    document.getElementById('continuar-step3').addEventListener('click', () => {
        const nomeReal = document.getElementById('nome-real').value.trim();
        const nomeDisplay = document.getElementById('nome-display').value.trim();
        const username = document.getElementById('username').value.trim();

        if (nomeReal && nomeDisplay && username) {
            currentStep++;
            mostrarEtapa(currentStep);
        } else {
            alert('Preencha todos os campos obrigatórios.');
        }
    });

    // Preview da imagem
    document.getElementById('foto').addEventListener('change', e => {
        const file = e.target.files[0];
        if (file) {
            const preview = document.getElementById('preview');
            preview.src = URL.createObjectURL(file);
            preview.style.display = 'block';
        }
    });

    // Submissão final
    // document.getElementById('registro-form').addEventListener('submit', e => {
    //     e.preventDefault();
    //     alert('Registro completo! Você será redirecionado.');
    //     // Aqui você pode salvar os dados com localStorage ou enviar para o servidor
    // });
});
