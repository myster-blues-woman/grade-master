module test {
    requires com.example;

    opens com.example.services.test;
    opens com.example.services.test.fakes;
}