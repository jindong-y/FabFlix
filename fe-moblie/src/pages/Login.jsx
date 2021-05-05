import React from 'react';
import {View, Text, TextInput, KeyboardAvoidingView,StyleSheet, TouchableOpacity, Button, Alert} from "react-native"
import Idm from '../services/idm';
import {useSession} from "../hooks/Session";
import useWindowDimensions from "react-native/Libraries/Utilities/useWindowDimensions";
import Axios from "axios";
import {baseURL, idmEPs} from "../config/config.json";


const Login = ({navigation}) => {
    // Axios.request({
    //     method:"get",
    //     url:'https://httpbin.org/post'
    // })
    //     .then(response => {
    //         console.log(response)
    //     })
    //     .catch(e => Alert.alert(e))
    // Axios.post('https://httpbin.org/post', { answer: 42 })
    //     .then(response => {
    //         console.log(response)
    //     })
    //     .catch(e => Alert.alert(e))

    //
    // Axios.post('http://192.168.0.175:12345/api/g/idm/login', { answer: 42 })
    //     .then(response => {
    //         console.log(response)
    //     })
    //     .catch(e => Alert.alert(e))


    const windowWidth = useWindowDimensions().width;
    const windowHeight = useWindowDimensions().height;
    const [email, setEmail] = React.useState()
    const [password, setPassword] = React.useState()
    const {setSession,session}=useSession()

    const submitButton = () => {
        console.log("Login button pressed")
        console.log(email)
        console.log(password)
        Idm.login(email, password)
            .then(response => {
                console.log(response)
                const {resultCode, message, session_id} = response?.data;
                if (resultCode === 120) {
                    console.log(message)
                    setSession({session_id: session_id, email: email})
                    navigation.navigate('Search')
                } else {
                    Alert.alert(message)
                }
            })
            .catch(e => {
                console.log(e)
                Alert.alert(e)
            })

    }

    // submitButton();

    return (
        <KeyboardAvoidingView
            behavior={Platform.OS === "ios" ? "padding" : "height"}
            style={styles.container}
        >
        <View style={{...styles.container}}>
            {/*<View style={styles.logo}>*/}
            {/*    <Text style={{*/}
            {/*        color: 'white', fontSize: 60, textAlign: 'center'*/}

            {/*    }}>Fablix</Text>*/}
            {/*</View>*/}
            <View style={styles.content}>
                <View style={styles.textBlock}>
                    <Text style={styles.text}>Email</Text>
                    <TextInput style={styles.input}
                               textContentType="username"
                               onChangeText={(text) => {
                                   console.log(text)
                                   setEmail(text)
                               }}/>
                </View>
                <View style={styles.textBlock}>
                    <Text style={styles.text}>Password</Text>
                    <TextInput style={styles.input}
                               textContentType="password"
                               onChangeText={(text) => setPassword(text)}
                               secureTextEntry={true}/>
                </View>
                <Button title={"Login"} onPress={submitButton}/>
                <Button title={"Register"} onPress={() => navigation.navigate('Register')}/>
            </View>
            {/*<TouchableOpacity*/}
            {/*    onPress={() =>alert(login)}*/}
            {/*    style={styles.btn}>*/}
            {/*    <Text style={{color:'white',textAlign:'center'}}>Login</Text>*/}
            {/*</TouchableOpacity>*/}
        </View>
        </KeyboardAvoidingView>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: "center",
        backgroundColor: "#fff",
        width: '100%',

    },
    logo: {
        flex: 1,
        backgroundColor: '#000',
    },
    content: {
        flex: 8,
        justifyContent: 'center'
    },
    textBlock: {
        flexDirection: "row",
        alignItems: "center",
        justifyContent: 'space-around'
    }
    ,
    text: {
        flex: 1,
        textAlign: 'center',
        fontSize: 20,
    },
    input: {
        flex: 1,
        borderWidth: 1,
        borderColor: "grey",
        margin:15
    },
    btn: {
        backgroundColor: "#20bca5",
    }
})


export default Login;