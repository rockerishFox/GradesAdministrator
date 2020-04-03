package validators;

import domain.Nota;

public class NotaValidator implements Validator<Nota> {

    @Override
    public void validate(Nota e) throws ValidationException {
        String errMsg="";
        if (e.getId() == null)
            errMsg+="Id error ";
        if (e.getProfesor() == null || "".equals(e.getProfesor()))
            errMsg+="Proffesor error ";
        if (e.getValoare() <1 || e.getValoare() >10)
            errMsg+="Nota trebuie sa fie intre 1 si 10 ";

        if (errMsg!="")
            throw new ValidationException(errMsg);
    }
}

