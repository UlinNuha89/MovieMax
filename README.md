
# MovieMax App

A simple app to see Now Playing, Popular, Upcoming and Top Rated Movie.


## Tech Stack

* MVVM
* Repo Pattern
* Coroutine Flow
* Koin Dependency Injection
* Jetpack paging
* Coil Image Loader
* Retrofit
* Shimmer Loader

## How To Use The App

* First Splashscreen will be opened and then automatically navigate to HomeActivity
* In the HomeActivity you can see list of movies
* if you click poster or info button, dialog detail will be shown and you can share poster image to your friend by clicking share button. You can add or remove your favourite movie list by clicking my list button 
* if you click see more(three dot button) in the section in HomeActivity. It will open list of movies(paginated) based on section type
## API Reference

we used [themoviedb](https://developer.themoviedb.org/) API for our app

#### Get Now Playing Movie

```http
  GET /now_playing
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `page` | `int` | Page for Response |
| `language` | `string` | Language for response |

#### Get Now Playing Movie

```http
  GET /popular
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `page` | `int` | Page for Response |
| `language` | `string` | Language for response |

#### Get Now Playing Movie

```http
  GET /upcoming
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `page` | `int` | Page for Response |
| `language` | `string` | Language for response |

#### Get Now Playing Movie

```http
  GET /top_rated
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `page` | `int` | Page for Response |
| `language` | `string` | Language for response |

## Authors

- [Ulin Nuha](https://github.com/UlinNuha89)
- [Rizky Gustiantoro](https://github.com/devotionfaith)
- [Muhammad Syihab Habibi](https://github.com/Ilthidin)
- [Muhammad Izroil](https://github.com/devalrohit)

