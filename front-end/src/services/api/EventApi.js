import axios from 'axios';

const BASE_URL = "http://localhost:8080/api";
const api = axios.create({
    baseURL: BASE_URL,
    timeout: 20000,
});

export const CreateEventApi = async (eventRequest) => {
    try{
        const response = await api.post("/event", eventRequest,{
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        return response.data;
        }
    catch(err){
        console.log(err);
    }
}
export const GetEventByIdApi = async (eventId) => {
    try{
        const response = await api.get(`/event/${eventId}`);
        return response.data;
    }
    catch(err){
        console.log(err);
    }
}
export const GetEventDetailByIdApi = async (eventId) => {
    try{
        const response = await api.get(`/event/${eventId}/detail`);
        return response.data;
    }
    catch(err){
        console.log(err);
    }
}
export const GetEventsApi = async () => {
    try{
        const response = await api.get("/event");
        return response.data;
    }
    catch(err){
        console.log(err);
    }
}
