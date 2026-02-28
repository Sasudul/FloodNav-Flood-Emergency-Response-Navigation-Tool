import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('API Error:', error.response?.data || error.message);
    return Promise.reject(error);
  }
);

// SOS Request APIs
export const submitSosRequest = async (data) => {
  const response = await api.post('/api/sos', data);
  return response.data;
};

export const getPendingSosRequests = async () => {
  const response = await api.get('/api/sos/pending');
  return response.data;
};

// Admin APIs
export const blockRoad = async (data) => {
  const response = await api.post('/api/admin/block-road', data);
  return response.data;
};

export const unblockRoad = async (data) => {
  const response = await api.post('/api/admin/unblock-road', data);
  return response.data;
};

export const blockRoadRange = async (data) => {
  const response = await api.post('/api/admin/block-road-range', data);
  return response.data;
};

export const unblockRoadRange = async (data) => {
  const response = await api.post('/api/admin/unblock-road-range', data);
  return response.data;
};

export const getGraphStats = async () => {
  const response = await api.get('/api/admin/graph-stats');
  return response.data;
};

export const getBlockedRoads = async () => {
  const response = await api.get('/api/admin/blocked-roads');
  return response.data;
};

export const getRescueBases = async () => {
  const response = await api.get('/api/rescue/bases');
  return response.data;
};

// Rescue APIs
export const getNextMission = async () => {
  const response = await api.get('/api/rescue/next-mission');
  return response.data;
};

export const getMissionHistory = async () => {
  const response = await api.get('/api/rescue/mission-history');
  return response.data;
};

export const checkHealth = async () => {
  const response = await api.get('/api/rescue/health');
  return response.data;
};

export default api;