import * as React from "react"
import {View, Image, Text, ScrollView} from 'react-native'
import Movie from '../services/movie'
import {useSession} from "../hooks/Session";


const SingleMovie = ({route}) => {
    const {movie_id} = route.params
    console.log("movieID", movie_id)
    const {session} = useSession();
    const headers = {
        ...session,
    }

    const [movie, setMovie] = React.useState();
    React.useEffect(() => {
        async function fetchMovie() {
            const response = await Movie.getByID(movie_id, headers)
            setMovie(response?.data.movie);
            console.log("response", response)
        }

        fetchMovie().then(() => console.log(movie))
            .catch(e => console.log(e))
    }, []);


    return (
        <ScrollView>

            <View style={styles.container}>

                <View >
                    <Image
                        style={styles.poster}
                        source={{uri: `https://image.tmdb.org/t/p/original${movie?.poster_path}`}}/>
                </View>
                <Text style={{fontSize:20,margin:10,fontWeight: "bold", textAlign:'center',}}>{movie?.title}</Text>
                <Text>{movie?.year}</Text>
                <Text>{movie?.rating}  ({movie?.num_votes}votes)</Text>
                <Text>{movie?.director}</Text>
                <Text style={{margin:10, fontSize:17,fontWeight: "bold"}}>{movie?.overview}</Text>
                <View style={{flexDirection: 'row'}}>
                {
                    movie?.genres.map(g=>{
                        return <Text style={{margin:10, fontSize:17}}>{g.name}</Text>
                    })
                }
                </View>
                <Text style={{margin:10, fontSize:20}}>Cast:</Text>

                <View style={{flexDirection: 'row', flexWrap:'wrap'}}>
                    {
                        movie?.people.map(p=>{
                            return <Text style={{margin:10, fontSize:17}}>{p.name}</Text>
                        })
                    }
                </View>

            </View>

        </ScrollView>
    )
}

const styles = {
    container: {
        display: "flex",
        flexDirection: "vertical",
        justifyContent: "space-between",
        alignItems: "center",
        height: "100%",
        textAlign: "center",
    },
    poster: {
        width: 200,
        height: 300,
        resizeMode: "contain",
    },
    imgBox: {}
}

export default SingleMovie;