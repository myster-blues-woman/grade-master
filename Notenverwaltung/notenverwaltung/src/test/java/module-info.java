module test {
    requires com.example;
    requires org.junit.jupiter.api;

    opens com.example.services.test;
    opens com.example.services.test.fakes;
}