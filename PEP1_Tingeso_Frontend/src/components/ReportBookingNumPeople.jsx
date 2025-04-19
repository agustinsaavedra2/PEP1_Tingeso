import React, { useState } from "react";
import BookingService from "../services/BookingService"; 

const ReportBookingNumPeople = () => {
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [revenueReport, setRevenueReport] = useState([]);
    const [loading, setLoading] = useState(false);

    const handleGenerateReport = () => {
        if (!startDate || !endDate) {
            alert("Please select both start and end dates");
            return;
        }

        setLoading(true);
        
        BookingService.reportBookingNumberPeople(startDate, endDate).then((response) =>{
            setRevenueReport(response.data);
            setLoading(false);
        }).catch((error) => {
            console.log("Error generating revenue report", error);
            setLoading(false);
        })
    };

    return (
        <div>
            <h2>Revenue Report</h2>
            <div>
                <label htmlFor="startDate" style={{margin: "20px"}}>Start Date:</label>
                <input
                    type="date"
                    id="startDate"
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                />
            </div>
            <div>
                <label htmlFor="endDate" style={{margin: "20px"}}>End Date:</label>
                <input
                    type="date"
                    id="endDate"
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                />
            </div>
            <button className="btn btn-warning" onClick={handleGenerateReport} disabled={loading} style={{margin: "20px"}} >
                {loading ? "Generating..." : "Generate Report"}
            </button>

            {revenueReport && revenueReport.length > 0 && (
                <div>
                    <h3 style={{margin: "20px"}}>Report Summary</h3>
                    <table className="table table-bordered" style={{margin: "20px"}}>
                        <thead >
                            <tr>
                                <th>Group Range</th>
                                <th>Total Bookings</th>
                                <th>Total Revenue</th>
                            </tr>
                        </thead>
                        <tbody>
                            {revenueReport.map((item, index) => (
                                <tr key={index}>
                                    <td>{item.groupRange}</td>
                                    <td>{item.totalBookings}</td>
                                    <td>${item.totalRevenue.toLocaleString()}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default ReportBookingNumPeople;
