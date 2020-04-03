package repositories;

import domain.Entity;
import validators.ValidationException;
import validators.Validator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private String filepath;

    public AbstractFileRepository(Validator<E> validator, String filepath) {
        super(validator);
        this.filepath = filepath;
    }

    public abstract E parseLine(String linie);

    public void load() {

        Path path = Paths.get(filepath);
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(linie -> {
                if (linie.length() > 0) {
                    E entity = parseLine(linie);
                    try {
                        super.save(entity);
                    } catch (ValidationException e) {
                    }
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public abstract String outputString(E entity);

    public void write() {
        Path path = Paths.get(filepath);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {

            for (E entity : super.findAll()) {
                String entToString= outputString(entity);
                bufferedWriter.write(entToString);
                bufferedWriter.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void clearFile(){
        File file = new File(this.filepath);
        Path filepath = Paths.get(this.filepath);
        try{
            new FileOutputStream(file).close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.write("");

            writer.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    @Override
    public E findOne(ID id) throws IllegalAccessException {
        return super.findOne(id);
    }

    @Override
    public Iterable<E> findAll() {
        return super.findAll();
    }

    @Override
    public E save(E entity) throws ValidationException {
        E saved = super.save(entity);
        write();
        return saved;
    }

    @Override
    public E delete(ID id) {
        E deleted = super.delete(id);
        write();
        return deleted;
    }

    @Override
    public E update(E entity) throws ValidationException {
        E updated = super.update(entity);
        write();
        return updated;
    }
}
