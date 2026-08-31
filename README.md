README.md
# Diseño de Software - Proyecto integrador
## Información general
- **Universidad:** Universidad Espíritu Santo
- **Carrera:** Computación
- **Asignatura:** Diseño de Software
- **Código:** UCOM0310
- **Periodo:** PEL 4 - 2026
- **Estudiante:** CARLOS VILLACRESES
- **Docente:** Ph.D. Jaime Paul Sayago Heredia
## Descripción
Desarollo de un Sistema de gestion de tutorias aplicando Diseño de software.

## Objetivos
- Utilizar POO.
- Aplicar conocimientos de Diseño de Software.
- Crear un sistema escalable.
- 
## Tecnologías
- Java 21
- Apache Maven 3.9.x
- Git y GitHub
- JUnit 5

Diseño de Software · UCOM0310 · PEL 4-2026 · UEES Online

- Spring Boot, cuando corresponda
## Requisitos previos
- JDK 21 instalado.
- Maven disponible en PATH.
- Git configurado.
## Instalación
```bash
git clone https://github.com/carlos-villacreses/ucom0310-diseno-software-villacreses_sistematutorias.git
cd ucom0310-diseno-software-villacreses_sistematutorias
mvn clean test
```
## Ejecución
```bash
mvn package
java -jar target/NOMBRE-DEL-ARCHIVO.jar
```
Adapte los comandos de ejecución a la estructura real del proyecto.
## Estructura del proyecto
```text
├── docs
│   └── tutoring-uml-class-diagram (1).jpg
├── pom.xml
├── README.md
├── sistema-tutorias.code-workspace
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── tutoring
│   │               ├── exception
│   │               │   ├── EntityNotFoundException.java
│   │               │   └── ScheduleConflictException.java
│   │               ├── Main.java
│   │               ├── model
│   │               │   ├── Course.java
│   │               │   ├── Person.java
│   │               │   ├── Session.java
│   │               │   ├── SessionStatus.java
│   │               │   ├── Student.java
│   │               │   └── Tutor.java
│   │               ├── repository
│   │               │   ├── AbstractInMemoryRepository.java
│   │               │   ├── CourseRepository.java
│   │               │   ├── Repository.java
│   │               │   ├── SessionRepository.java
│   │               │   ├── StudentRepository.java
│   │               │   └── TutorRepository.java
│   │               └── service
│   │                   └── TutoringService.java
│   └── test
│       └── java
│           └── com
│               └── tutoring
│                   └── service
│                       └── TutoringServiceTest.java
└── target
    ├── classes
    │   └── com
    │       └── tutoring
    │           ├── exception
    │           │   ├── EntityNotFoundException.class
    │           │   └── ScheduleConflictException.class
    │           ├── Main.class
    │           ├── model
    │           │   ├── Course.class
    │           │   ├── Person.class
    │           │   ├── Session.class
    │           │   ├── SessionStatus.class
    │           │   ├── Student.class
    │           │   └── Tutor.class
    │           ├── repository
    │           │   ├── AbstractInMemoryRepository.class
    │           │   ├── CourseRepository.class
    │           │   ├── Repository.class
    │           │   ├── SessionRepository.class
    │           │   ├── StudentRepository.class
    │           │   └── TutorRepository.class
    │           └── service
    │               └── TutoringService.class
    ├── generated-sources
    │   └── annotations
    ├── generated-test-sources
    │   └── test-annotations
    ├── maven-status
    │   └── maven-compiler-plugin
    │       ├── compile
    │       │   └── default-compile
    │       │       ├── createdFiles.lst
    │       │       └── inputFiles.lst
    │       └── testCompile
    │           └── default-testCompile
    │               ├── createdFiles.lst
    │               └── inputFiles.lst
    ├── surefire-reports
    │   ├── com.tutoring.service.TutoringServiceTest.txt
    │   └── TEST-com.tutoring.service.TutoringServiceTest.xml
    └── test-classes
        └── com
            └── tutoring
                └── service
                    └── TutoringServiceTest.class

```
## Funcionalidades
- Implementación SOLID.
## Pruebas
Para ejecutar las pruebas:
```bash
mvn clean test
```
## Control de versiones
El proyecto utiliza la rama `main`. Las funcionalidades se desarrollan en ramas específicas y se integran
mediante revisión.

Diseño de Software · UCOM0310 · PEL 4-2026 · UEES Online

## Evidencias
https://github.com/carlos-villacreses/ucom0310-diseno-software-villacreses_sistematutorias/blob/main/docs/tutoring-uml-class-diagram%20(1).jpg

## Uso de inteligencia artificial
Se utilizó Claude Code para el desarrollo base de una aplicación java y posterior se adaptó el codigo implementando el aprendizaje de Diseño de Software.

## Autor
CARLOS VILLACRESES - CARLOS.VILLACRESES@UEES.EDU.EC
