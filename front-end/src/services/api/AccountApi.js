import axios from "axios";

const BASE_URL = "http://localhost:8080/api";
const api = axios.create({
    baseURL: BASE_URL,
    timeout: 20000,
});
export const SignInApi = async (signInRequest) => {
    try{
        const response = await api.post("/account/auth/signin", signInRequest);
        return response.data;
    }
    catch(err){
        console.log(err);
    }
}
export const SignUpApi = async (signUpRequest) => {
    try{
        const response = await api.post("/account/user", signUpRequest);
        return response.data;
    }
    catch(err){
        console.log(err);
    }
}