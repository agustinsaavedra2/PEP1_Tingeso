import {useState, useEffect} from "react";
import BookingService from "../services/BookingService"

const AllBookings = () => {
    const [listBookings, setListBookings] = useState([]);

    useEffect(() =>{
        BookingService.getAllBookings().then((response) => {
            setListBookings(response.data);
        }).catch((error) => {
            console.log("Error fetching bookings", error);
        });

    }, []);

    return (
        <div className="container mt-4">
            <h2 className="text-center mb-4">List Bookings</h2>

        {listBookings.map((booking) => (
            <div className="card text-black bg-warning mb-3" style={{ maxWidth: "25rem", margin: "20px auto" }} key={booking.id}>
                <div className="card-body">
                    <h5 className="card-title">{booking.nameBooking}</h5>
                    <p className="card-text">Laps Number: {booking.lapsNumber} laps</p>
                    <p className="card-text">Maximum Time: {booking.maximumTime} minutes</p>
                    <p className="card-text">Booking Date: {booking.bookingDate}</p>
                    <p className="card-text">Booking Time: {booking.bookingTime}</p>
                    <p className="card-text">Total Duration: {booking.totalDuration} minutes</p>
                    <p className="card-text">Base Price: ${booking.basePrice}</p>
                    <p className="card-text">Discount by People Number: ${booking.discountByPeopleNumber}</p>
                    <p className="card-text">Discount by Frequent Customer: ${booking.discountByFrequentCustomer}</p>
                    <p className="card-text">Discount by Special Days: ${booking.discountBySpecialDays}</p>
                </div>
            </div>
        ))}
        
        </div>

    );
}

export default AllBookings;