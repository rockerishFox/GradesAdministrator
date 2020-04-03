package UI;


import domain.*;
import services.NotaService;
import services.StudentService;
import services.TemaService;
import utils.Constants;
import validators.ValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;

public class UI {

    private StudentService studentService;
    private TemaService temaService;
    private NotaService notaService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


    public UI(StudentService studentService, TemaService temaService, NotaService notaService) {
        this.studentService = studentService;
        this.temaService = temaService;
        this.notaService = notaService;
    }


    private void meniu() {
        System.out.print("\n\n:------------------------- Meniu aplicatie -------------------------:\n\n");
        System.out.println("1s/1t/1n - Afisare studenti/teme/note");
        System.out.println("2s/2t/2n - Adaugare student/tema/nota");
        System.out.println("3s/3t/3n - Stergere student/tema/nota");
        System.out.println("4s/4t/4n - Cautare student/tema/nota");
        System.out.println("5s/5t/5n - Modificare student/tema/nota");

        System.out.println("f1. Filter 1 - toti studentii dintr-o anumita grupa");
        System.out.println("f2. Filter 2 - toti studentii care au predat o anumita tema");
        System.out.println("f3. Filter 3 - toti studentii care au predat o anumita tema unui anumit profesor");
        System.out.println("f4. Filter 4 - toate notele pentru o anumita tema, dintr-o saptamana data");

        System.out.print("0 - Exit\n");
    }


    public Student readStudent() throws IOException {
        System.out.println("Id student: ");
        String id = reader.readLine();

        System.out.println("Nume: ");
        String name = reader.readLine();

        System.out.println("Prenume:");
        String prenume = reader.readLine();

        System.out.println("Grupa:");
        int grupa = -1;

        while (grupa < 0) {
            try {
                grupa = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException ex) {
                System.out.println("Grupa trebuie sa fie un numar!");
            }
        }

        System.out.println("E-mail: ");
        String email = reader.readLine();

        System.out.println("Cadru didactic: ");
        String profesor = reader.readLine();

        return new Student(id, name, prenume, grupa, email, profesor);
    }

    public Tema readTema() throws IOException {
        System.out.println("Id tema:");
        String idtema = reader.readLine();

        System.out.println("Descriere tema:");
        String descriere = reader.readLine();

        System.out.println("Saptamana deadline: ");
        int deadweek = Integer.parseInt(reader.readLine());

        return new Tema(idtema, descriere, deadweek);

    }

    public void run() throws IOException {
        try {
            while (true) {

                meniu();

                System.out.println(">");
                String cmd = reader.readLine();

                switch (cmd) {
                    case "0": {
                        return;
                    }
                    case "1s": {
                        for (Student st : studentService.findAll())
                            System.out.println(st);
                        break;
                    }
                    case "2s": {
                        try {
                            Student st = readStudent();
                            studentService.save(st);
                        } catch (ValidationException ex) {
                            System.out.println(ex.getMessage());
                        } catch (IllegalArgumentException ex) {
                            System.out.println(ex.getMessage());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    }
                    case "3s": {
                        System.out.println("Introduceti id:");
                        String id = reader.readLine();
                        Student st = studentService.delete(id);
                        if (st == null)
                            System.out.println("Nu exista student cu acest id!");
                        else System.out.println("Student sters!");
                        break;
                    }
                    case "4s": {
                        try {
                            System.out.println("Introduceti id-ul studentului cautat:");
                            String id = reader.readLine();
                            if (studentService.findOne(id) == null)
                                System.out.println("Nu exista student cu acest id!");
                            else System.out.println(studentService.findOne(id));
                            break;
                        } catch (NumberFormatException | IllegalAccessException e) {
                            System.out.println("d");
                        }
                    }
                    case "5s": {
                        try {
                            Student st = readStudent();
                            Student s = studentService.update(st);
                            if (s == null) System.out.println("Student modificat!");
                            else {
                                System.out.println("Nu exista student cu acest id!");
                            }
                        } catch (ValidationException ex) {
                            System.out.println(ex.getMessage());
                        } catch (IllegalArgumentException ex) {
                            System.out.println(ex.getStackTrace());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    }

                    case "1t": {
                        for (Tema t : temaService.findAll())
                            System.out.println(t);
                        break;
                    }
                    case "2t": {
                        try {
                            Tema t = readTema();
                            temaService.save(t);
                        } catch (ValidationException ex) {
                            System.out.println(ex.getMessage());
                        } catch (IllegalArgumentException ex) {
                            System.out.println(ex.getStackTrace());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    }
                    case "3t": {
                        System.out.println("Introduceti id:");
                        String id = reader.readLine();
                        Tema del = temaService.delete(id);
                        if (del == null) System.out.println("Nu exista tema cu acest id!");
                        else System.out.println("Tema stearsa!");
                        break;
                    }
                    case "4t": {
                        System.out.println("Introduceti id-ul temei cautate:");
                        String id = reader.readLine();
                        if (temaService.findOne(id) == null)
                            System.out.println("Nu exista tema cu acest id!");
                        else System.out.println(temaService.findOne(id));
                        break;
                    }
                    case "5t": {
                        try {
                            Tema t = readTema();
                            Tema rez = temaService.update(t);
                            if (rez == null)
                                System.out.println("Tema modificata");
                            else
                                System.out.println("Nu exista tema cu id-ul dat");

                        } catch (ValidationException ex) {
                            System.out.println(ex.getMessage());
                        } catch (IllegalArgumentException ex) {
                            System.out.println(ex.getStackTrace());
                        }
                        break;
                    }

                    case "1n": {
                        for (Nota n : notaService.findAll()) System.out.println(n);
                        break;
                    }
                    case "2n": {
                        System.out.println("Introduceti id student: ");
                        String id = reader.readLine();
                        Student st = studentService.findOne(id);
                        if (st == null) System.out.println("Nu exista student cu acest id");
                        while (st == null) {
                            id = reader.readLine();
                            st = studentService.findOne(id);
                        }
                        System.out.println("Introduceti id tema: ");
                        String idTema = reader.readLine();
                        Tema tema = temaService.findOne(idTema);
                        if (tema == null) System.out.println("Nu exista tema cu acest id");
                        while (tema == null) {
                            id = reader.readLine();
                            tema = temaService.findOne(id);
                        }

                        System.out.println("Introduceti nota: ");
                        float valoare = 0;
                        while (valoare == 0) {
                            try {
                                valoare = Float.parseFloat(reader.readLine());
                            } catch (NumberFormatException ex) {
                                System.out.println("Trebuie sa fie un numar rational!");
                            }
                        }
                        System.out.println("Introduceti feedback: ");
                        String feedback = reader.readLine();
                        System.out.println("Numar absente motivate ale studentului: ");
                        int motivari = -1;

                        while (motivari == -1) {
                            try {
                                motivari = Integer.parseInt(reader.readLine());
                                StructuraAnUniversitar anUniv = new StructuraAnUniversitar();

                                for (int i = 1; i <= motivari; i++) {

                                    System.out.println("Introduceti data motivarii:");
                                    LocalDate data = LocalDate.parse(reader.readLine(), Constants.DATE_FORMATTER);
                                    int saptMotivata = anUniv.getCurrentWeek(data);

                                    while (temaService.findOne(idTema).getEndWeek() < saptMotivata ||
                                            temaService.findOne(idTema).getStartWeek() > saptMotivata) {
                                        System.out.println("Data motivarii invalida!");
                                        System.out.println("Introduceti o data valida:");
                                        LocalDate data2 = LocalDate.parse(reader.readLine());
                                        saptMotivata = anUniv.getCurrentWeek(data2);
                                    }
                                }
                            } catch (NumberFormatException ex) {
                                System.out.println("Numarul de motivari trebuie sa fie mai mare sau egal cu 0!");
                            }
                        }
                        System.out.println("Profesorul a introdus nota la timp? Da/Nu");
                        String answer = reader.readLine();
                        int ok = -1;
                        if (answer.compareTo("Nu") == 0) {
                            System.out.println("Cate intarzieri a avut profesorul?");
                            while (ok == -1) {
                                try {
                                    ok = Integer.parseInt(reader.readLine());
                                } catch (NumberFormatException ex) {
                                    System.out.println(ex.getStackTrace());
                                }
                            }
                        } else ok = 0;

                        try {
                            Nota nota = notaService.noteaza(st, tema, feedback, motivari, valoare, ok);
                            if (nota != null)
                                System.out.println("Nota s-a adaugat cu succes!");
                        } catch (ValidationException ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;
                    }
                    case "3n": {
                        System.out.println("Introduceti id-ul studentului :");
                        String id = reader.readLine();
                        System.out.println("Introduceti id-ul temei:");
                        String id1 = reader.readLine();
                        Pair p = new Pair(id, id1);

                        Nota del = notaService.delete(p);
                        if (del == null) System.out.println("Nu exista nota la aceasta tema pentru acest student!");
                        else System.out.println("Nota stearsa");
                        break;
                    }
                    case "4n": {
                        System.out.println("Introduceti id-ul studentului :");
                        String id = reader.readLine();
                        System.out.println("Introduceti id-ul temei:");
                        String id1 = reader.readLine();
                        Pair p = new Pair(id, id1);

                        if (notaService.findOne(p) == null)
                            System.out.println("Nu exista nota cu acest id!");
                        else System.out.println(notaService.findOne(p));
                        break;
                    }
                    case "5n": {
                        System.out.println("Id student: ");
                        String id = reader.readLine();
                        Student st = studentService.findOne(id);
                        if (st == null) System.out.println("Nu exista student cu acest id");
                        while (st == null) {
                            id = reader.readLine();
                            st = studentService.findOne(id);
                        }
                        System.out.println("Id tema: ");
                        String idTema = reader.readLine();
                        Tema tema = temaService.findOne(idTema);
                        if (tema == null) System.out.println("Nu exista tema cu acest id");
                        while (tema == null) {
                            id = reader.readLine();
                            tema = temaService.findOne(id);
                        }

                        System.out.println("Nota: ");
                        float valoare = 0;
                        while (valoare == 0) {
                            try {
                                valoare = Float.parseFloat(reader.readLine());
                            } catch (NumberFormatException ex) {
                                System.out.println("Trebuie sa fie un numar rational!");
                            }
                        }
                        System.out.println("Feedback:");
                        String feedback = reader.readLine();
                        System.out.println("Motivari:");
                        int motivari = -1;
                        while (motivari == -1) {
                            try {
                                motivari = Integer.parseInt(reader.readLine());
                            } catch (NumberFormatException ex) {
                                System.out.println("Trebuie sa fie un numar mai mare sau egal cu 0!");
                            }
                        }
                        System.out.println("Profesorul a introdus nota la timp? Da/Nu");
                        String rasp = reader.readLine();
                        int x = -1;
                        if (rasp.compareTo("Nu") == 0) {
                            System.out.println("Cate intarzieri a avut profesorul?");
                            while (x == -1) {
                                try {
                                    x = Integer.parseInt(reader.readLine());
                                } catch (NumberFormatException ex) {
                                    System.out.println(ex.getStackTrace());
                                }
                            }
                        } else x = 0;
                        try {
                            Nota nota = notaService.noteaza(st, tema, feedback, motivari, valoare, x);
                        } catch (ValidationException ex) {
                            System.out.println(ex.getMessage());
                        } catch (RuntimeException ex) {
                            System.out.println(ex.getStackTrace());
                        }
                        break;
                    }

                    case "f1": {
                        System.out.println("Grupa:");

                        int grupa = -1;
                        while (grupa < 0) {
                            try {
                                grupa = Integer.parseInt(reader.readLine());
                            } catch (NumberFormatException ex) {
                                System.out.println("Grupa trebuie sa fie un numar!");
                            }
                        }

                        ArrayList<String> array = studentService.filterByGroup(grupa);
                        for (String st : array) {
                            System.out.println(st);
                        }
                        break;
                    }
                    case "f2": {
                        Tema t = null;
                        while (t == null) {
                            System.out.println("Id tema:");

                            String id = reader.readLine();
                            t = temaService.findOne(id);
                            if (t == null)
                                System.out.println("Nu s-a gasit tema!");
                        }
                        ArrayList<Student> array = notaService.filterByTemaId(t.getId());
                        for (Student st : array) {
                            System.out.println(st);
                        }
                        break;
                    }
                    case "f3": {
                        Tema t = null;
                        while (t == null) {
                            System.out.println("Id tema:");

                            String id = reader.readLine();
                            t = temaService.findOne(id);
                            if (t == null)
                                System.out.println("Nu s-a gasit tema!");
                        }
                        System.out.println("Profesor:");
                        String prof = reader.readLine();

                        ArrayList<Student> array = notaService.filterByTemaIdAndProf(t.getId(), prof);
                        for (Student st : array) {
                            System.out.println(st);
                        }
                        break;
                    }
                    case "f4": {
                        Tema t = null;
                        while (t == null) {
                            System.out.println("Id tema:");

                            String id = reader.readLine();
                            t = temaService.findOne(id);
                            if (t == null)
                                System.out.println("Nu s-a gasit tema!");
                        }
                        int week = -1;
                        while (week == -1) {
                            try {
                                System.out.println("Saptamana:");
                                week = Integer.parseInt(reader.readLine());
                                if (week > 14) {
                                    week = -1;
                                    System.out.println("Saptamana trebuie sa fie intre 0 si 14 !");
                                }
                            } catch (NumberFormatException x) {
                                System.out.println("Saptamana trebuie sa fie un numar > 0!");
                            }
                        }

                        ArrayList<String> array = notaService.filterByTemaIdAndWeek(t.getId(), week);
                        for (String st : array) {
                            System.out.println(st);
                        }

                        break;
                    }
                    default: {
                        System.out.println("Comanda incorecta!");
                        break;
                    }
                }
            }
        } catch (IllegalAccessException x) {
            System.out.println("fuuuuck");
        }
    }
}
