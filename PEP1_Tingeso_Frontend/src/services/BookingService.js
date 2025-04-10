import httpClient from "../http-common"

const createBooking = (data) => {
    return httpClient.post("/api/client/", data);
}

const getBookingById = (id) => {
    return httpClient.get(`/api/client/${id}`);
}

const getAllBookings = () => {
    return httpClient.get("/api/client/");
}

const updateBooking = (data) => {
    return httpClient.put("/api/client/", data);
}

const deleteBooking = (id) => {
    return httpClient.delete(`/api/client/${id}`);
}

export default { createBooking, getBookingById, getAllBookings, updateBooking, deleteBooking };