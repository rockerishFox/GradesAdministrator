package validators;

import domain.Tema;

public class TemaValidator implements Validator<Tema> {

    @Override
    public void validate(Tema e) throws ValidationException {
        String errors="";
        if (e.getId() == null )
            errors="Id invalid!! ";
        if (e.getEndWeek()<0 || e.getEndWeek()>14)
            errors="Deadline Week error. ";
        if (e.getEndWeek()<e.getStartWeek())
            errors+="Start week mai mare decat Deadline Week!! ";
        if (errors!="" )
            throw new ValidationException(errors);
    }
}
