import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import BookingService from "../services/BookingService";

const SetPriceDuration = () => {
    const { id } = useParams(); // obtén el ID del booking de la URL
    const [clientPrices, setClientPrices] = useState([]);

    useEffect(() => {
        BookingService.setPriceAndDuration(id)
            .then(response => {
                setClientPrices(response.data); // [{first: 'Agustín', second: 15000.0}, ...]
            })
            .catch(error => {
                console.error("Error fetching client prices", error);
            });
    }, [id]);

    return (
        <div className="container mt-4">
            <h2 className="mb-4">Client Base Prices for Booking #{id}</h2>
            {clientPrices.length > 0 ? (
                <ul className="list-group">
                    {clientPrices.map((pair, index) => (
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
};

export default SetPriceDuration;