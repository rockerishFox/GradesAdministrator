package tests;


import domain.Student;
import org.junit.Assert;
import org.junit.Test;
import repositories.FileRepositoryStudent;
import services.config.ApplicationContext;
import validators.ValidationException;

public class TestStudentFileRepository {

    private FileRepositoryStudent studentFileRepository = new FileRepositoryStudent(ApplicationContext.getPROPERTIES().getProperty("data.studentsTest"));


    @Test
    public void testStudentFile() throws ValidationException {
        Student s2=new Student("1","Csiki","Delia",222,"csikidelia@gmail.com","Camelia");

        studentFileRepository.save(s2);

        Student stFound = studentFileRepository.findOne("1");
        Assert.assertEquals(stFound, s2);

        Student studentupd = new Student("1","Csiki","Delia",226,"csikidelia@gmail.com","Camelia");

        studentFileRepository.update(studentupd);
        Student found = studentFileRepository.findOne("1");
        Assert.assertEquals(found.getGrupa(),226);

        Iterable<Student> all = studentFileRepository.findAll();
        Assert.assertEquals(all.spliterator().getExactSizeIfKnown(), 1);

        studentFileRepository.delete("1");
        all = studentFileRepository.findAll();
        Assert.assertEquals(all.spliterator().getExactSizeIfKnown(), 0);

    }


}