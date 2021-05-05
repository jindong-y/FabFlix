
## Running Tests

- First, deploy your microservice on your LOCAL machine using PORT 12345
- Run the service using one of the following:
~~~
[WINDOWS]         gradlew      test --tests TestClassName

Before you can do this for MACOS you need to make the gradlew executable 
Type this in the directory: chmod +x gradlew 
[MACOS/UNIX]     ./gradlew     test --tests TestClassName 

There are two ways to test your service 

1. Run all the tests
   HW1:
   gradlew test --tests BasicTest*
   
2. Run a specific test
   HW1 Individual Test:
   gradlew test --tests BasicTestHello
  
~~~

- This will run all tests named `TestClassName` and will output a html report and a xml report in a newly create report folder

- You will still need to delete the folder before every test, there is a commented out script in the gradlew.build folder that you can use to delete the folder automatically
