package repositories;

import domain.Student;
import validators.StudentValidator;

public class FileRepositoryStudent extends AbstractFileRepository<String, Student> {
    public FileRepositoryStudent(String filepath) {
        super(new StudentValidator(), filepath);

    }

    @Override
    public Student parseLine(String linie) {
        String[] l = linie.split(";");
        Student st = new Student(l[0], l[1], l[2], Integer.parseInt(l[3]), l[4], l[5]);
        return st;
    }

    @Override
    public String outputString(Student entity) {
        return entity.getId() + ";" +
                entity.getNume() + ";" +
                entity.getPrenume() + ";" +
                entity.getGrupa() + ";" +
                entity.getEmail() + ";" +
                entity.getCadruDidacticIndrumatorLab();
    }
}
