## **Accounting System API**

The current API it's build using **http4k** toolkit, which allows us to create pure, functional and immutable HTTP services.
Under the hood, at the persistence layer, I've used **jetbrains exposed**, taking advantage of the nice DSL that it provides.

For starting the application, the `main()` function from the `Program` file should be run. A **Jetty** server will start on port `9090`.

The application is build without using a **Dependency Injection** framework like **Spring**, instead I've tried to build a minimal, functional, dependency container, based on the interface `FreesoftSystem`.

