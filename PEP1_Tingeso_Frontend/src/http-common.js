import axios from "axios"

const host = "host.docker.internal";

export default axios.create({
    baseURL: `http://${host}:8080`,
    headers: {
        'Content-Type': 'application/json'
    }
});