import { useEffect, useState} from "react"
import {useParams} from "react-router-dom"
import BookingService from "../services/BookingService"

const DiscountFreqClient = () => {
    const [discFreqClient, setDiscFreqClient] = useState([]);
    const {id} = useParams();

    useEffect(() => {
        BookingService.setDiscountFreqClient(id).then((response) => {
            setDiscFreqClient(response.data);
        }).catch((error) =>{
            console.log("Error fetching discount by frequent client.", error);
        })
    });

    return(
        <div className="container mt-4">
            <h2 className="mb-4">Client Discount for Frequent Client in Booking #{id}</h2>
            {discFreqClient.length > 0 ? (
                <ul className="list-group">
                    {discFreqClient.map((pair, index) => (
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

export default DiscountFreqClient;