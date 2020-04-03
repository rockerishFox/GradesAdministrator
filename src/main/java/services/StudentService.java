package services;

import domain.Student;
import repositories.AbstractFileRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StudentService extends Service<String, Student> {

    private AbstractFileRepository<String,Student> studentFileRepository;

    public StudentService(AbstractFileRepository<String,Student> studentFileRepository) {
        super(studentFileRepository);
        this.studentFileRepository = studentFileRepository;
    }


    public ArrayList<String> filterByGroup(int grupa){
        ArrayList<String> array = new ArrayList<>();
        StreamSupport.stream(studentFileRepository.findAll().spliterator(),false).
                filter(x->x.getGrupa() == grupa)
                .forEach(x-> array.add(x.toString()));
        return array;
    }

}
