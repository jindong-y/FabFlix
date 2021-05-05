import {gatewayEP, baseURL, pollLimit, pollingInterval} from "../config/config.json";
import Socket from "./Socket"

async function getReport(response,request) {

    if (response.status !== 204) {
        return response;
    }
    console.log("Sending Report request for ",request)
    const config = {
        baseURL: baseURL,
        url: gatewayEP.report,
        headers: {transaction_id: response.headers["transaction_id"]}
    }



    for (let i = 0; i < pollLimit; i++) {
        const report = await Socket.GET(config)
        if (report.status !== 204) {
            console.log("GOT report!")
            return report;
        }
        await timeout();
    }


}

async  function timeout(){
    return new Promise(resolve =>{
        setTimeout(resolve,pollingInterval);
    })
}

export default getReport;