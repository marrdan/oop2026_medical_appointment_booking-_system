import db.PostegresDB;
import entities.Appointment;
import repositories.*;
import services.*;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        AppointmentRepository repo =
                new AppointmentRepositoryImpl(new PostegresDB());

        DoctorAvailabilityService availability =
                new DoctorAvailabilityService(repo);

        AppointmentService service =
                new AppointmentService(repo, availability);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                1. Book appointment
                2. Cancel appointment
                3. Doctor schedule
                4. Patient visits
                0. Exit
            """);

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Patient ID: ");
                    int p = sc.nextInt();
                    System.out.print("Doctor ID: ");
                    int d = sc.nextInt();

                    service.book(
                            new Appointment(p, d, LocalDateTime.now().plusDays(1))
                    );
                    System.out.println("Booked");
                }
                case 2 -> {
                    System.out.print("Appointment ID: ");
                    service.cancel(sc.nextInt());
                }
                case 3 -> {
                    System.out.print("Doctor ID: ");
                    service.doctorSchedule(sc.nextInt())
                            .forEach(System.out::println);
                }
                case 4 -> {
                    System.out.print("Patient ID: ");
                    service.patientVisits(sc.nextInt())
                            .forEach(System.out::println);
                }
                case 0 -> System.exit(0);
            }
        }
    }
}
