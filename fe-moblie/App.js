import 'react-native-gesture-handler';
import {StatusBar} from 'expo-status-bar';
import * as React from 'react';
import {StyleSheet, Text, View, TextInput} from 'react-native';
import Login from './src/pages/Login'
import {SessionProvider} from './src/hooks/Session'
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import Register from "./src/pages/Register";
import useWindowDimensions from "react-native/Libraries/Utilities/useWindowDimensions";
import Search from "./src/pages/Search";
import SingleMovie from "./src/pages/SingleMovie";


const Stack = createStackNavigator();

export default function App() {
    const windowWidth = useWindowDimensions().width;

    console.log(windowWidth)

    return (
        <NavigationContainer>
                <SessionProvider>
                    <View style={styles.container}>
                        <Stack.Navigator style={{width:350}}>
                            <Stack.Screen name="Login" component={Login}/>
                            <Stack.Screen name="Register" component={Register}/>
                            <Stack.Screen name="Search" component={Search}/>
                            <Stack.Screen name="SingleMovie" component={SingleMovie}/>

                        </Stack.Navigator>

                        <StatusBar style="auto"/>
                    </View>
                </SessionProvider>
        </NavigationContainer>
    );
}


const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        // alignItems: 'center',
        justifyContent: 'center',
        width: '100%'

    },
});
