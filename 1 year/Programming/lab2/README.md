### Лабораторная работа 2

![image](https://github.com/vnikolaenko-dev/ITMO/assets/64604542/08edf289-3284-4adf-bd11-f65b78936cc6)

- javac (из PokemonGame)
```
javac -encoding utf8 -d bin -cp "Pokemon.jar" src/attacks/*.java src/pokemons/*/*.java src/Main.java
```
- jar (из bin, предварительно кладем Pokemon.jar и MANIFEST.MF в bin)
```
jar -cfm app.jar MANIFEST.MF Pokemon.jar attacks/*.class pokemons/*/*.class Main.class
```
##### Файловая структура
![image](https://github.com/vnikolaenko-dev/ITMO/assets/64604542/f15247fe-32ef-4469-aa78-cccf79428e5d)
