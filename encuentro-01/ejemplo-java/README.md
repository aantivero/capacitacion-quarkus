#GraalVM
##Java Demo

###Requerimiento 
Tener instalado GraalVM en el equipo

###Compilar
`javac -d build src/com/epi/saludo/Saludo.java`

###Ejecutar
`java -cp ./build com.epi.saludo.Saludo`

###Crear Jar
```
jar cfvm Saludo.jar META-INF/MANIFEST.MF  manifest.txt -C build .
jar tf Saludo.jar
```

###Ejecutar Jar
```
java -jar Saludo.jar
```

###Generar Imagen Nativa
```
native-image -jar Saludo.jar
```