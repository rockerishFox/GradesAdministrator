package services;

import domain.Tema;
import repositories.AbstractFileRepository;

public class TemaService extends Service<String, Tema> {

    private AbstractFileRepository<String,Tema> temaFileRepository;

    public TemaService(AbstractFileRepository<String,Tema> temaFileRepository) {
        super(temaFileRepository);
        this.temaFileRepository = temaFileRepository;
    }
    public long getMaxId() {

        long id = 0;
        for (Tema tema : findAll()) {
            if (Long.parseLong(tema.getId() )> id) id = Long.parseLong(tema.getId());
        }
        return id;
    }
}
