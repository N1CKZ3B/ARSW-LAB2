Escuela Colombiana de Ingeniería

Arquitecturas de Software – ARSW

#### Taller – programación concurrente, condiciones de carrera y sincronización de hilos. EJERCICIO INDIVIDUAL O EN PAREJAS.


-----------------------------------------------------------------------------------------
#### Realizado por: Nicolas Sebastian Achuri Macias y Ricardo Andres Villamizar Mendez
---------------------------------------------------------------------------------------


##### Parte I – Antes de terminar la clase.

Creación, puesta en marcha y coordinación de hilos.

1. Revise el programa “primos concurrentes” (en la carpeta parte1), dispuesto en el paquete edu.eci.arsw.primefinder. Este es un programa que calcula los números primos entre dos intervalos, distribuyendo la búsqueda de los mismos entre hilos independientes. Por ahora, tiene un único hilo de ejecución que busca los primos entre 0 y 30.000.000. Ejecútelo, abra el administrador de procesos del sistema operativo, y verifique cuantos núcleos son usados por el mismo.

   &emsp;&emsp;&emsp;![](img/media/parte1-1.png)


2. Modifique el programa para que, en lugar de resolver el problema con un solo hilo, lo haga con tres, donde cada uno de éstos hará la tarcera parte del problema original. Verifique nuevamente el funcionamiento, y nuevamente revise el uso de los núcleos del equipo.

```
        PrimeFinderThread pft=new PrimeFinderThread(0, 10000000);
        PrimeFinderThread pft2 = new PrimeFinderThread(10000001, 20000000);
        PrimeFinderThread pft3 = new PrimeFinderThread(20000001, 30000000);
        pft.start();
        pft2.start();
        pft3.start();
        
```

&emsp;&emsp;&emsp;![](img/media/parte1-2.png)



3. Lo que se le ha pedido es: debe modificar la aplicación de manera que cuando hayan transcurrido 5 segundos desde que se inició la ejecución, se detengan todos los hilos y se muestre el número de primos encontrados hasta el momento. Luego, se debe esperar a que el usuario presione ENTER para reanudar la ejecución de los mismo.

<div align="justify">
Se modifica el main en el sentido de poder iniciar los hilos de manera exitosa, el generar un wait y un notify al momento de sincronizar los hilos y poder generarlos en diferentes momentos por medio del uso de métodos de manera correcta
</div>

&emsp;&emsp;&emsp;![](img/media/parte1-3.png)

Se crean métodos de resume y pause thread que a su misma vez van a permitir que el programa funcione correctamente

&emsp;&emsp;&emsp;![](img/media/parte1-4.png)
---------------------------------------------------------------------------


### Parte II 


Para este ejercicio se va a trabajar con un simulador de carreras de galgos (carpeta parte2), cuya representación gráfica corresponde a la siguiente figura:

![](./img/media/image1.png)

En la simulación, todos los galgos tienen la misma velocidad (a nivel de programación), por lo que el galgo ganador será aquel que (por cuestiones del azar) haya sido más beneficiado por el *scheduling* del
procesador (es decir, al que más ciclos de CPU se le haya otorgado durante la carrera). El modelo de la aplicación es el siguiente:

![](./img/media/image2.png)

Como se observa, los galgos son objetos ‘hilo’ (Thread), y el avance de los mismos es visualizado en la clase Canodromo, que es básicamente un formulario Swing. Todos los galgos (por defecto son 17 galgos corriendo en una pista de 100 metros) comparten el acceso a un objeto de tipo
RegistroLLegada. Cuando un galgo llega a la meta, accede al contador ubicado en dicho objeto (cuyo valor inicial es 1), y toma dicho valor como su posición de llegada, y luego lo incrementa en 1. El galgo que
logre tomar el ‘1’ será el ganador.

Al iniciar la aplicación, hay un primer error evidente: los resultados (total recorrido y número del galgo ganador) son mostrados antes de que finalice la carrera como tal. Sin embargo, es posible que una vez corregido esto, haya más inconsistencias causadas por la presencia de condiciones de carrera.

Taller.

1.  Corrija la aplicación para que el aviso de resultados se muestre
    sólo cuando la ejecución de todos los hilos ‘galgo’ haya finalizado.
    Para esto tenga en cuenta:

    a.  La acción de iniciar la carrera y mostrar los resultados se realiza a partir de la línea 38 de MainCanodromo.

    b.  Puede utilizarse el método join() de la clase Thread para sincronizar el hilo que inicia la carrera, con la finalización de los hilos de los galgos.

      &emsp;&emsp;&emsp;![](img/media/parte2-join.png)

      Se usa el metodo join desde lo requerido para poder ejecutarlo correctamente despues de la linea 38, esto para sincronizar el inicio con el fin de la carrera

2.  Una vez corregido el problema inicial, corra la aplicación varias
    veces, e identifique las inconsistencias en los resultados de las
    mismas viendo el ‘ranking’ mostrado en consola (algunas veces
    podrían salir resultados válidos, pero en otros se pueden presentar
    dichas inconsistencias). A partir de esto, identifique las regiones
    críticas () del programa.

    <div align="justify">
    Se encuentra la inconsistencia del error al no haber terminado la carrera y la aparicion del mensaje de que la carrera ya ha terminado
    </div>

    

3.  Utilice un mecanismo de sincronización para garantizar que a dichas
    regiones críticas sólo acceda un hilo a la vez. Verifique los
    resultados.
      &emsp;&emsp;&emsp;![](img/media/parte2-sync.png)


      &emsp;&emsp;&emsp;![](img/media/parte2-pruebafunc.png)




4.  Implemente las funcionalidades de pausa y continuar. Con estas,
    cuando se haga clic en ‘Stop’, todos los hilos de los galgos
    deberían dormirse, y cuando se haga clic en ‘Continue’ los mismos
    deberían despertarse y continuar con la carrera. Diseñe una solución que permita hacer esto utilizando los mecanismos de sincronización con las primitivas de los Locks provistos por el lenguaje (wait y notifyAll).

   En la clase de Galgo
   
   &emsp;&emsp;&emsp;![](img/media/uso-waitynotify.png)

   En el Main

 &emsp;&emsp;&emsp;![](img/media/parte2-stopyresume.png)

   ----------------------------------------------------

   ## FUNCIONAMIENTO 

   ![image](https://github.com/user-attachments/assets/56fa8e72-4fc9-45d3-bf69-9a5222508d4e)


## Criterios de evaluación

1. Funcionalidad.

    1.1. La ejecución de los galgos puede ser detenida y resumida consistentemente.
    
    1.2. No hay inconsistencias en el orden de llegada registrado.
    
2. Diseño.   

    2.1. Se hace una sincronización de sólo la región crítica (sincronizar, por ejemplo, todo un método, bloquearía más de lo necesario).
    
    2.2. Los galgos, cuando están suspendidos, son reactivados son sólo un llamado (usando un monitor común).

