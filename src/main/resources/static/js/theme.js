/**
 * Theme management for SOC Paperwork Management System
 * Handles theme switching, system preference detection, and persistence
 */

class ThemeManager {
    constructor() {
        this.defaultTheme = 'light';
        this.storageKey = 'theme';
        this.themeAttribute = 'data-theme';
        this.systemDarkQuery = '(prefers-color-scheme: dark)';
        
        this.initialize();
    }

    initialize() {
        // Initialize theme on load
        this.applyTheme(this.getCurrentTheme());
        
        // Add system theme change listener
        this.handleSystemThemeChange();
        
        // Handle theme toggles
        this.setupThemeToggles();
        
        // Mark body as theme-loaded to prevent flash
        document.body.classList.add('theme-loaded');
    }

    getCurrentTheme() {
        const savedTheme = localStorage.getItem(this.storageKey);
        if (savedTheme === 'system') {
            return this.getSystemTheme();
        }
        return savedTheme || this.defaultTheme;
    }

    getSystemTheme() {
        return window.matchMedia(this.systemDarkQuery).matches ? 'dark' : 'light';
    }

    applyTheme(theme) {
        if (theme === 'system') {
            const systemTheme = this.getSystemTheme();
            document.documentElement.setAttribute(this.themeAttribute, systemTheme);
        } else {
            document.documentElement.setAttribute(this.themeAttribute, theme);
        }
        localStorage.setItem(this.storageKey, theme);
        
        // Dispatch theme change event
        window.dispatchEvent(new CustomEvent('themechange', { 
            detail: { theme: theme } 
        }));
    }

    handleSystemThemeChange() {
        window.matchMedia(this.systemDarkQuery)
            .addEventListener('change', (e) => {
                if (localStorage.getItem(this.storageKey) === 'system') {
                    this.applyTheme('system');
                }
            });
    }

    setupThemeToggles() {
        // Handle theme radio buttons in settings
        document.querySelectorAll('[name="theme"]').forEach(radio => {
            radio.addEventListener('change', (e) => {
                this.applyTheme(e.target.value);
            });
        });

        // Update radio button states
        const currentTheme = this.getCurrentTheme();
        const themeRadio = document.querySelector(`[name="theme"][value="${currentTheme}"]`);
        if (themeRadio) {
            themeRadio.checked = true;
        }
    }

    // Helper method to check if dark mode is active
    isDarkMode() {
        const theme = this.getCurrentTheme();
        return theme === 'dark' || (theme === 'system' && this.getSystemTheme() === 'dark');
    }
}

// Initialize theme manager
const themeManager = new ThemeManager();

// Export for use in other scripts
window.themeManager = themeManager;

// Theme-specific UI adjustments
document.addEventListener('DOMContentLoaded', () => {
    const updateThemeUI = (theme) => {
        // Update theme icon in navbar
        const themeIcon = document.querySelector('.theme-icon');
        if (themeIcon) {
            themeIcon.className = `fas fa-${themeManager.isDarkMode() ? 'moon' : 'sun'} theme-icon`;
        }

        // Update charts if they exist
        if (window.updateChartsTheme) {
            window.updateChartsTheme();
        }

        // Update code syntax highlighting if it exists
        if (window.highlightAll) {
            window.highlightAll();
        }
    };

    // Listen for theme changes
    window.addEventListener('themechange', (e) => {
        updateThemeUI(e.detail.theme);
    });

    // Initial UI update
    updateThemeUI(themeManager.getCurrentTheme());
});

// Add CSS transitions for smooth theme switching
const style = document.createElement('style');
style.textContent = `
    body {
        transition: background-color 0.3s ease, color 0.3s ease;
    }
    
    .card,
    .navbar,
    .modal-content,
    .form-control,
    .btn {
        transition: background-color 0.3s ease, 
                    border-color 0.3s ease, 
                    color 0.3s ease;
    }
`;
document.head.appendChild(style);