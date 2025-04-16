import axios from "axios"

const DB_HOST = import.meta.env.VITE_DB_HOST;
const DB_PORT = import.meta.env.VITE_DB_PORT;

export default axios.create({
    baseURL: `http://192.168.100.121:8080`,
    headers: {
        'Content-Type': 'application/json'
    }
});