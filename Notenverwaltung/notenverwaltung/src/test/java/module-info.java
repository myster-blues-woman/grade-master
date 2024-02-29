module test {
    requires org.junit.jupiter.api;
    requires com.example;

    opens com.example.services.test;
    opens com.example.services.test.fakes;
}