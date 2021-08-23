#GraalVM
##Ejemplo Poliglota Javascript, Java y R

###Requisitos
Instalar soporte NodeJS y R

```
gu install nodejs
gu install r
```

###Instalar

```
./build.sh
npm install
```

###Ejecutar
```
$GRAALVM_HOME/bin/node --jvm --polyglot server.js
/Users/alejandroantivero/.sdkman/candidates/java/21.1.0.r11-grl/bin/node --jvm --polyglot server.js
```