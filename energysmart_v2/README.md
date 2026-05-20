# EnergySmart CO — Proyecto Maven

Sistema académico de gestión de consumo energético del hogar, desarrollado en Java con arquitectura MVC.

## Cómo abrir en NetBeans (Maven)

1. Abrir NetBeans
2. `File → Open Project`
3. Seleccionar la carpeta `EnergySmartCO` (donde está el `pom.xml`)
4. NetBeans lo reconoce automáticamente como proyecto Maven
5. Presionar **F6** o el botón Run ▶

## Cómo ejecutar desde la terminal

```bash
# Compilar
mvn compile

# Ejecutar la app
mvn exec:java

# Correr las pruebas
mvn test

# Generar el JAR ejecutable (queda en /target/EnergySmartCO.jar)
mvn package

# Ejecutar el JAR generado
java -jar target/EnergySmartCO.jar
```

## Credenciales de prueba

| Campo      | Valor                     |
|------------|---------------------------|
| Correo     | admin@energysmart.com     |
| Contraseña | 1234                      |

## Estructura del proyecto (estándar Maven)

```
EnergySmartCO/
├── pom.xml                          ← Configuración central de Maven
└── src/
    ├── main/java/energysmartco/
    │   ├── app/          → MainApp.java (punto de entrada)
    │   ├── model/        → Usuario, Electrodomestico
    │   ├── view/swing/   → AlertView (ventana principal Swing)
    │   ├── view/javafx/  → DashboardView (preparado para JavaFX)
    │   ├── controller/   → UserController, DeviceController
    │   ├── service/      → ConsumoService
    │   ├── dao/          → UsuarioDAO
    │   ├── database/     → DatabaseConnection (patrón Singleton)
    │   └── utils/        → Validator
    └── test/java/energysmartco/
        └── EnergySmartTest.java     ← Pruebas unitarias con JUnit 5
```
