import Axios from "axios";

async function GET(props) {
    return await sendHTTP("GET", props);
}

async function POST(props) {
    const url=props?.baseURL+props?.url;
    delete props.baseURL;
    delete props.url;
    return await Axios.post(url, {...props});
}

async function DELETE(props) {
    return await sendHTTP("DELETE", props);
}

async function sendHTTP(method, props) {
    const options = {
        ...props,
        method: method
    }

    return Axios.request(options)
}

export default {
    GET,
    POST,
    DELETE
};
