import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // All requests to /api will be forwarded to your backend
      '/api': {
        target: 'http://localhost:8080', // your Spring Boot server URL
        changeOrigin: true,
        secure: false,
      },
    },
  },
});
