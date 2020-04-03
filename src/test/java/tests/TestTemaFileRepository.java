package tests;

import domain.Student;
import domain.Tema;
import org.junit.Assert;

import org.junit.Test;
import repositories.FileRepositoryTema;
import services.config.ApplicationContext;
import validators.ValidationException;



class TestTemaFileRepository {

    private FileRepositoryTema temaFileRepository = new FileRepositoryTema(ApplicationContext.getPROPERTIES().getProperty("data.temeTest"));

    @Test
    void test() throws ValidationException {



            Student s1=new Student("1","Birle","Damaris",221,"birledama@gmail.com","Teofana");
            Tema tema=new Tema("1","descriere1", "1",13);
            temaFileRepository.save(tema);

            Iterable<Tema> all = temaFileRepository.findAll();
            Assert.assertEquals(all.spliterator().getExactSizeIfKnown(), 1);

            Tema found = temaFileRepository.findOne("1");
            Assert.assertEquals(tema, found);

            Tema upd = new Tema("1","descr", "1",10);
            temaFileRepository.update(upd);
            found = temaFileRepository.findOne("1");
            Assert.assertEquals(found.getDescriere(), "descr");
            Assert.assertEquals(found.getEndWeek(), 10);

            temaFileRepository.delete("0");
            all = temaFileRepository.findAll();
            Assert.assertEquals(all.spliterator().getExactSizeIfKnown(), 0);
        }
    }
