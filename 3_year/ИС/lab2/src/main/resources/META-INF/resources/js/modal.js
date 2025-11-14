// modal.js - Функции для работы с модальными окнами

// Показать модальное окно с ошибкой
function showErrorModal(title, message, options = {}) {
    const modal = document.getElementById('errorModal');
    const titleElement = document.getElementById('modalErrorTitle');
    const messageElement = document.getElementById('modalErrorMessage');

    // Устанавливаем заголовок и сообщение
    titleElement.textContent = title || 'Error';
    messageElement.textContent = message || 'An unexpected error occurred';

    // Настраиваем иконку в зависимости от типа ошибки
    const iconElement = document.querySelector('.modal-icon');
    if (options.type === 'warning') {
        iconElement.textContent = '⚠️';
        iconElement.style.color = '#ffa726';
    } else if (options.type === 'success') {
        iconElement.textContent = '✅';
        iconElement.style.color = '#4ecca3';
        titleElement.style.color = '#4ecca3';
    } else {
        iconElement.textContent = '❌';
        iconElement.style.color = '#ff6b6b';
    }

    // Показываем модальное окно
    modal.style.display = 'flex';

    // Автоматическое скрытие через указанное время (если задано)
    if (options.autoHide) {
        setTimeout(() => {
            hideErrorModal();
        }, options.autoHide);
    }

    // Блокировка прокрутки фона
    document.body.style.overflow = 'hidden';
}

// Скрыть модальное окно
function hideErrorModal() {
    const modal = document.getElementById('errorModal');
    modal.style.display = 'none';
    document.body.style.overflow = 'auto';
}

// Универсальная функция для обработки ошибок
function handleError(error, context = 'Operation') {
    console.error(`${context} error:`, error);

    let errorMessage = 'An unexpected error occurred';

    if (error instanceof Error) {
        errorMessage = error.message;
    } else if (typeof error === 'string') {
        errorMessage = error;
    } else if (error?.message) {
        errorMessage = error.message;
    } else if (error?.response?.data?.message) {
        errorMessage = error.response.data.message;
    }

    showErrorModal(`${context} Failed`, errorMessage);
}

// Функция для показа успешного сообщения
function showSuccessModal(title, message, autoHide = 3000) {
    showErrorModal(title, message, {
        type: 'success',
        autoHide: autoHide
    });
}

// Функция для показа предупреждения
function showWarningModal(title, message, autoHide = 5000) {
    showErrorModal(title, message, {
        type: 'warning',
        autoHide: autoHide
    });
}

// Закрытие модального окна по клику на overlay
document.getElementById('errorModal').addEventListener('click', function(e) {
    if (e.target === this) {
        hideErrorModal();
    }
});

// Закрытие модального окна по клавише Escape
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        hideErrorModal();
    }
});

window.showErrorModal = showErrorModal;
window.hideErrorModal = hideErrorModal;
window.handleError = handleError;
window.showSuccessModal = showSuccessModal;
window.showWarningModal = showWarningModal;