// API Configuration
const isDevelopment = import.meta.env.DEV

export const API_CONFIG = {
    BASE_URL: isDevelopment 
        ? '/api/v1' 
        : 'https://cite-30363-frogcrew.onrender.com/api/v1',
    TIMEOUT: 10000, // 10 seconds
    HEADERS: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
}

// Helper function to get full API URL
export const getApiUrl = (endpoint) => {
    return `${API_CONFIG.BASE_URL}${endpoint}`
}

// Helper function to get headers with optional authorization
export const getHeaders = (includeAuth = false, token = null) => {
    const headers = { ...API_CONFIG.HEADERS }
    
    if (includeAuth && token) {
        headers['Authorization'] = `Bearer ${token}`
    }
    
    return headers
}
