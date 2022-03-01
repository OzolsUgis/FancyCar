<h1 align="center">Google maps application with data caching</h1>

Building this application I faced some challanges because i had to use some technologies that i never used before, which 
made this proces interesting 

## Application preview

https://user-images.githubusercontent.com/39744745/156103712-c13370e5-1115-4406-b449-dfc3e3816199.mp4

## I was able to: 

* Download Json data with list of persons and their vehicles, when server returned error it was shown in snackbar.
* This fetched data was stored in Room local database.
* Persons list have functionality to update itself whenever onCreate method was called.
* Selected user vehicles is shown in Google Maps using new google maps compose library.
* Application asks for permission when detecting devices current location.
* Application detects devices current location. 
* When current vehicle is selected it plots nearest route from devices location to selected vehicle.

## Architecutre 

* I had dillema, how to structure my code so i choose to structure it in layers, this app contains 3 main layers : 
* Data - Where data is stored (Data layer can not access UI layer)
* Domain - Domain layer is ment for models (Clases that we see in UI), Repository(Combines data income, making "One source of truth possible"), Use_cases (Buisness logic) 
* Presentation - This layer contains all UI Data

App still have some bugs, with the reloading data functionality it happened because lack of experiance, i struggled to find a spot where to put Handler function.
And application is not polished in the permission requesting, i didnt want to focus on that because of development time. And because of the current global situation and my current 
job in military time resource is realy limited. 
