import axios from "axios"

const DB_HOST = import.meta.env.VITE_DB_HOST;
const DB_PORT = import.meta.env.VITE_DB_PORT;

export default axios.create({
    baseURL: `http://${DB_HOST}:${DB_PORT}`,
    headers: {
        'Content-Type': 'application/json'
    }
});