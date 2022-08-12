# Simulación de Sistemas - TP1

## Cell Index Method

El objetivo de este Trabajo Práctico es, dado un area de lado *L* que contiene *N* particulas de radio *R* donde estas estaran en contacto si la distancia entre sus bordes es menor a Rc (radio de contacto), implementamos el *Cell Index Method* que lo que busca es optimizar la busqueda de vecinos.

Para calcular los vecinos de las particulas y generar los archivos correspondientes, se utilizo Java.

Para los resultados, se utilizo Python para interpretar los datos de salida y Ovito para graficarlos.

## Uso

1) Clonar el repositorio en el directorio deseado.
2) Con el IDE que desee, abra el proyecto y asegurese que *src* sea reconocida como el módulo de Sources. Lo mismo para la *resources*.
3) Configure los argumentos de linea de comando (que se detallaran a continuación).
4) Ejecute el main de la clase *ParticlesParser*.
5) Posicionese en el directorio *graphics* y ejecute el siguiente comando:

    ```python3 main.py```

## Argumentos de linea de comando

- **RC** : Radio de contacto
- **input**: Nombre del archivo static que contendra los datos estaticos
- **dynamic**: Nombre del archivo que contendra los datos dinamicos
- **output**: Nombre del archivo de salida
- **isPeriodic**: Booleano que determina si el sistema tendra *condición periódica de contorno* o no
- **generate**: Booleano que determina si el sistema generará nuevas particulas o no
