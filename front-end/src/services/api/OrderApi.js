import axios from 'axios';

const BASE_URL = "http://localhost:8080/api";
const api = axios.create({
    headers: {
        "Content-Type": "application/json"
    },
    baseURL: BASE_URL,
    timeout: 20000,
    maxRedirects: 0
});

export const CreateOrderApi = async (createOrderRequest) => {
    try {
        const response = await api.post("/order", createOrderRequest);
        return response.data;
    } catch (error) {
        throw error;
    }
};
export const GetOrderByIdApi = async (orderId) => {
    try {
        const response = await api.get(`/order/${orderId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
}
export const GetFilteredOrdersApi = async (request) => {
    try {
        const response = await api.get(`/order`, request);
        return response.data;
    } catch (error) {
        throw error;
    }
}
export const GetOrderByIdWithDetailOrderAndTicketApi = async (orderId) => {
    try {
        const response = await api.get(`/order/${orderId}?include=ticketOfOrders,ticket`);
        return response.data;
    } catch (error) {
        throw error;
    }
}
export const GetOrderByIdWithDetailOrderApi = async (orderId) => {
    try {
        const response = await api.get(`/order/${orderId}?include=ticketOfOrders`);
        return response.data;
    } catch (error) {
        throw error;
    }
}
export const GetOrderByIdWithDetailOrderAndTicketApiAndEvent = async (orderId) => {
    try {
        const response = await api.get(`/order/${orderId}?include=ticketOfOrders,ticket,event`);
        return response.data;
    } catch (error) {
        throw error;
    }
}
export const ReverseOrderApi = async (orderId) => {
    try {
        const response = await api.put(`/order/${orderId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
}