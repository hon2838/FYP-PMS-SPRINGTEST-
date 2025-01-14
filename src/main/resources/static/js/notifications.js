window.NotificationModal = class NotificationModal {
    static show(options = {}) {
        const {
            type = 'info',
            title = 'Notification',
            message = '',
            details = '',
            redirectUrl = null,
            buttonText = 'OK'
        } = options;

        const modalElement = document.getElementById('notificationModal');
        if (!modalElement) {
            console.error('notificationModal not found in the DOM.');
            return;
        }

        const modal = new bootstrap.Modal(modalElement);
        const header = document.getElementById('notificationHeader');
        const icon = document.getElementById('notificationIcon');
        const mainIcon = document.getElementById('notificationMainIcon');
        const titleEl = document.getElementById('notificationTitle');
        const messageEl = document.getElementById('notificationMessage');
        const detailsEl = document.getElementById('notificationDetails');
        const button = document.getElementById('notificationButton');

        // Define configurations for different types
        const config = {
            success: {
                headerClass: 'bg-success text-white',
                iconClass: 'fa-check-circle',
                mainIconClass: 'text-success fa-check-circle',
                buttonClass: 'btn-success'
            },
            error: {
                headerClass: 'bg-danger text-white',
                iconClass: 'fa-exclamation-circle',
                mainIconClass: 'text-danger fa-exclamation-circle',
                buttonClass: 'btn-danger'
            },
            warning: {
                headerClass: 'bg-warning text-dark',
                iconClass: 'fa-exclamation-triangle',
                mainIconClass: 'text-warning fa-exclamation-triangle',
                buttonClass: 'btn-warning'
            },
            info: {
                headerClass: 'bg-info text-white',
                iconClass: 'fa-info-circle',
                mainIconClass: 'text-info fa-info-circle',
                buttonClass: 'btn-info'
            }
        };

        // Apply styles based on type
        const currentConfig = config[type] || config['info'];
        header.className = `modal-header border-0 ${currentConfig.headerClass}`;
        icon.className = `fas ${currentConfig.iconClass} me-2`;
        mainIcon.className = `fas ${currentConfig.mainIconClass} animate__animated animate__bounceIn`;
        button.className = `btn ${currentConfig.buttonClass} px-4`;

        // Set content
        titleEl.textContent = title;
        messageEl.textContent = message;
        detailsEl.textContent = details;
        button.textContent = buttonText;

        // Handle redirect after modal is hidden
        if (redirectUrl) {
            modalElement.addEventListener('hidden.bs.modal', () => {
                window.location.href = redirectUrl;
            }, { once: true });
        }

        // Show the modal
        modal.show();
    }
};