import { useEffect, useState } from "react";
import { useParams } from "react-router-dom"; 
import BookingService from "../services/BookingService";

const DiscountNumPeople = () => {
    const {id} = useParams();
    const [discountNumPeople , setDiscountNumPeople] = useState([]);

    useEffect(() => {
        BookingService.setDiscountPeopleNumber(id).then((response) => {
            setDiscountNumPeople(response.data);
        }).catch((error) => {
            console.log("Error fetching discount by number of people.", error);
        })
    }, [id]);

    return(
        <div className="container mt-4">
            <h2 className="mb-4">Client Discount for People Number in Booking #{id}</h2>
            {discountNumPeople.length > 0 ? (
                <ul className="list-group">
                    {discountNumPeople.map((pair, index) => (
                        <li key={index} className="list-group-item">
                            {pair.first} - ${pair.second.toLocaleString()}
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No data available.</p>
            )}
        </div>
    )
}

export default DiscountNumPeople;