import * as React from "react"
import {Text, View, TextInput, Button, FlatList,Image,Alert,Pressable} from "react-native";
import {PickerIOS} from '@react-native-picker/picker';
import {useSession} from "../hooks/Session";
import Movie from "../services/movie";

const Search = ({navigation}) => {

    const [dropdown, setDropdown] = React.useState(false);
    const [title, setTitle] = React.useState();
    const [year, setYear] = React.useState();
    const [director, setDirector] = React.useState();
    const [genre, setGenre] = React.useState();
    const [hasMovie, setHasMovie] = React.useState();
    const [results,setResults]=React.useState();
    const [offset,setOffset]=React.useState(0);

    const {session} = useSession();
    const headers = {
        ...session,
    }

    const searchSubmit = async (offset) => {

        console.log("search submit triggered")
        const movies = await getMovie(10, offset);

        if (!movies) {
            setHasMovie(false);
            return 0;
        }
        setHasMovie(true);
        console.log("setMovieAmount", movies.length)
        // movieAmount = movies.length;
        // setPagination(() => {
        //         let pages = [];
        //         for (let i = 0; i < Math.ceil(movieAmount / 10); i++) {
        //             pages[i] = (
        //                 {
        //                     key: i + 1
        //                 }
        //             )
        //         }
        //         return pages;
        //     }
        // )
        // setResults(movies.slice(0, 10))
        console.log("movies size>=", movies.length)
        // setCurrentPage(1);
        console.log(movies?.map((movie)=>movie?.movie_id))
        setResults(await getThumbnailsList(movies?.map((movie)=>movie?.movie_id)));
    }

    async function getMovie(limit = 10, offset = 0) {

        let response;
        if (year && !parseInt(year)) {
            return alert("year must be a valid number")
        }
        let params = {};

        console.log("search by title....")
        if (title) params["title"] = title;
        if (year) params["year"] = year;
        if (director) params["director"] = director;
        if (genre) params["genre"] = genre;
        //if no enter
        if (Object.keys(params).length === 0) {
            console.log("no params")
            return []
        }
        ;
        params["orderby"] = "year";
        params["direction"] = "desc";
        params["limit"] = limit;
        params["offset"] = offset;
        response = await Movie.search(params, headers)


        console.log(response.data?.movies)
        return response.data?.movies;
    }


    async function getThumbnailsList(movie_ids) {
        if (movie_ids == undefined) {
            return null;
        }
        console.log("Try to get thumbnail List");
        console.log("movie_ids", movie_ids)

        let result = null;
        const response = await Movie.Thumbnails({movie_ids: movie_ids}, headers)

        if (response.status === 200) {
            console.log("got thumbnail response");
            result = response.data?.thumbnails;
        }
        console.log(result)
        return result;
    }





    function PosterList({results}) {
        console.log("in posterlist",results)

        return (
            <View style={styles.posterBox}>
                <FlatList
                    data={results}
                    keyExtractor={item => item.movie_id}
                    numColumns={2}
                    renderItem={({item}) => {
                        return <Pressable
                            onPress={()=>navigation.navigate('SingleMovie',{movie_id:item?.movie_id})}
                            style={styles.posterContent}>
                            <Image
                                resizeMode='cover'
                                style={styles.poster}
                                source={{uri:`https://image.tmdb.org/t/p/original${item?.poster_path}`}}/>
                            <Text>{item?.title?.length > 20 ? item?.title.slice(0, 20) + "..." : item?.title}</Text>
                        </Pressable>
                    }}
                    ListFooterComponentStyle={{display:results?"":'none'}}
                    ListFooterComponent={()=>(
                        <View style={{flexDirection: 'row',justifyContent: 'center',marginBottom:20}}>
                            <Button title='Prev' onPress={()=>page(-10)}/>
                            <Button title='Next' onPress={()=>page(10)}/>
                        </View>
                    )}
                />
            </View>
        )

    }
    const page=async (os)=>{

        if(offset+os<0||await searchSubmit(offset+os)===0){
            Alert.alert("Reach the limit","No more movies")
        }else {
            setOffset(offset + os)
        }

    }


    return (
        <View style={styles.container}>

            {/*<PickerIOS selectedValue={searchClass}*/}
            {/*           onValueChange={(itemValue) => setSearchClass(itemValue)}>*/}
            {/*    <PickerIOS.Item value='title' label='title'/>*/}
            {/*    <PickerIOS.Item value='year' label='year'/>*/}

            {/*</PickerIOS>*/}
            <View style={styles.searchBar}>
                <View style={{...styles.inputBox, marginHorizontal: 10}
                }>
                    <Text>Title</Text>
                    <TextInput style={styles.input} onChangeText={(text) => setTitle(text)}/>
                </View>
                <View style={{flex: 1, flexDirection: 'row'}}>
                    <Button title='Search' onPress={()=>searchSubmit(0)}/>
                    <Button color={dropdown ? '#3dd233' : 'grey'} title='V' onPress={() => setDropdown(!dropdown)}/>
                </View>
            </View>
            <View style={{...styles.dropdown, display: dropdown ? 'flex' : 'none'}}>
                <View style={styles.inputBox}>
                    <Text>Year</Text>
                    <TextInput style={styles.input} onChangeText={(text) => setYear(text)}/>
                </View>
                <View style={styles.inputBox}>
                    <Text>Director</Text>
                    <TextInput style={styles.input} onChangeText={(text) => setDirector(text)}/>
                </View>
                <View style={styles.inputBox}>
                    <Text>Genre</Text>
                    <TextInput style={styles.input} onChangeText={(text) => setGenre(text)}/>
                </View>
            </View>
            <View style={styles.result}>
                {/*<Text>list view</Text>*/}
                <PosterList results={results}/>

            </View>

        </View>


    )

}

const styles = {
    poster: {
        maxWidth:150,
        height: 200,
    },
    container: {
        flex: 1,
        justifyContent: 'center',
    },
    searchBar: {
        flex: 1,
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'space-around',
        alignItems: 'center',
        margin: 10,

    },
    inputBox: {
        flex: 2,
        justifyContent: 'space-around',
        alignItems: 'center',
        flexDirection: 'row',
    },
    input: {
        borderWidth: 1,
        borderColor: "grey",
        height: 30,
        width: '60%',

    },
    dropdown: {
        flex: 1,
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'flex-start',
        alignItems: 'center',
    },
    result: {
        flex: 8,
        flexDirection:'row',
        justifyContent:'center'
    },
    posterBox:{
        flex:1,
        // flexDirection:'row',
        justifyContent:'center',
        marginHorizontal:10,
        width:300,
        // flexWrap:'wrap'
    },
    posterContent:{
        flex:1,
        // flexDirection:'row',
        justifyContent:'center',
        marginHorizontal:10,
        width:300,
        margin:10
        // flexWrap:'wrap'
    },
}


export default Search;