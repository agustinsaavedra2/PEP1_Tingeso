import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import BookingService from "../services/BookingService"

const DiscountSpecialDays = () => {
    const [discSpecialDays, setDiscSpecialDays] = useState([]);
    const {id} = useParams();

    useEffect(() => {
       BookingService.setDiscountSpecialDays(id).then((response) => {
            setDiscSpecialDays(response.data);
       }).catch((error) => {
            console.log("Error fetching discount by special days.", error);  
       })
    }, [id])

    return(
        <div className="container mt-4">
            <h2 className="mb-4">Client Discount for Special Days in Booking #{id}</h2>
            {discSpecialDays.length > 0 ? (
                <ul className="list-group">
                    {discSpecialDays.map((pair, index) => (
                        <li key={index} className="list-group-item">
                            {pair.first} - ${pair.second.toLocaleString()}
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No data available.</p>
            )}
        </div>
    );
}

export default DiscountSpecialDays;