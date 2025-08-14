// Test script to verify API connection
import { getApiUrl } from './config.js'

export const testApiConnection = async () => {
    try {
        console.log('Testing API connection...')
        console.log('API Base URL:', getApiUrl(''))
        
        // Test a simple endpoint
        const response = await fetch(getApiUrl('/crewMember'))
        console.log('Response status:', response.status)
        console.log('Response ok:', response.ok)
        
        if (response.ok) {
            const data = await response.json()
            console.log('Response data:', data)
            return true
        } else {
            console.error('API request failed:', response.statusText)
            return false
        }
    } catch (error) {
        console.error('Connection test failed:', error)
        return false
    }
}

// Test CORS
export const testCors = async () => {
    try {
        console.log('Testing CORS...')
        
        const response = await fetch(getApiUrl('/crewMember'), {
            method: 'OPTIONS'
        })
        
        console.log('CORS preflight response status:', response.status)
        console.log('CORS headers:', {
            'Access-Control-Allow-Origin': response.headers.get('Access-Control-Allow-Origin'),
            'Access-Control-Allow-Methods': response.headers.get('Access-Control-Allow-Methods'),
            'Access-Control-Allow-Headers': response.headers.get('Access-Control-Allow-Headers')
        })
        
        return response.ok
    } catch (error) {
        console.error('CORS test failed:', error)
        return false
    }
}
