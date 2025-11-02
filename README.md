---

Pachetele principale

1. model/
Conține clasele entităților definite în UML:
- Passenger, Flight, Ticket, Airplane, Luggage, Staff, AirlineEmployee, AirportEmployee, FlightAssignment, NoticeBoard
- Fiecare clasă are:
  - atribute private  
  - getter/setter  
  - constructor(i)  
  - metode simple (toString() etc.)
  - tipuri corecte de date (LocalDate, LocalDateTime, etc.)

---

2. repository/
- Gestionează colecțiile de date în memorie (List, Map).
- Oferă metode de bază:
  - save()
  - findAll()
  - findById()
  - delete()

---

3. service/
- Conține logica aplicației.
- Fiecare serviciu corespunde unei entități majore (ex: PassengerService, FlightService).
- Aplică principiul *Single Responsibility*: fiecare serviciu face un singur lucru.

---

4. controller/
- Interfața dintre utilizator și aplicație.
- Fiecare controller este marcat cu:
  ```java
  @Controller
  @GetMapping
  @ResponseBody
  De ce am ales controllere diferite și nu unul singur
    1. Respectarea principiului Single Responsibility (S din SOLID)
          Fiecare controller are o singură responsabilitate:
            PassengerController — gestionează pasagerii
            FlightController — gestionează zborurile
  
    2. Claritate și separarea logicii pe domenii (Domain Separation)
          Fiecare controller corespunde unei entități majore din modelul UML:
            Flight, Passenger, Ticket, Airplane, Staff, etc.

    3. Extensibilitate (Open/Closed Principle)
          Fiecare este deschis pentru extindere, dar închis pentru modificare — alt principiu SOLID.

