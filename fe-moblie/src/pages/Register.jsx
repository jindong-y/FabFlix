import React from 'react'
import Idm from "../services/idm";
import {useSession} from "../hooks/Session";
import {Alert, Button, KeyboardAvoidingView, StyleSheet, Text, TextInput, View} from "react-native";
import Login from "./Login";


const Register=({navigation})=>{
    const [email, setEmail] = React.useState()
    const [password, setPassword] = React.useState()

    const submitButton = () => {
        console.log("Register button pressed")
        Idm.register(email, password)
            .then(response => {
                console.log(response)
                const {resultCode, message} = response?.data;
                if (resultCode === 110) {
                    console.log(message)
                    Alert.alert(message)
                    navigation.navigate('Login')
                } else {
                    Alert.alert(message)
                }
            })
            .catch(e => Alert.alert(e.message))
    }

    // submitButton();

    // const back=()=>{
    //     navigation.navigate('Search')
    // }

    return (
        <KeyboardAvoidingView
            behavior={Platform.OS === "ios" ? "padding" : "height"}
            style={styles.container}
        >

        <View style={styles.container}>
            <View style={styles.textBlock}>
                <Text style={styles.text} >Email</Text>
                <TextInput style={styles.input}
                           onChangeText={(text) => {
                               console.log(text)
                               setEmail(text)
                           }}/>
            </View>
            <View style={styles.textBlock}>
                <Text style={styles.text} >Password</Text>
                <TextInput style={styles.input}
                           onChangeText={(text) => setPassword(text)}
                           secureTextEntry={true}/>
            </View>
            <Button title={"Register"} onPress={submitButton}></Button>
            {/*<Button title={"back"} onPress={back}></Button>*/}

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
        backgroundColor: "#474646",
        width: '100%',

    },
    textBlock: {
        flexDirection: "row",
        alignItems: "center",
    }
    ,
    text: {
        flex: 1,
        padding: "1%",
        textAlign: 'center',
        fontSize: 20,
    },
    input: {
        flex: 1,
        borderWidth: 1,
        borderColor: "grey",

    },
    btn: {
        backgroundColor: "#20bca5",
    }
})

export default Register;