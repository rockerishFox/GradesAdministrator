package validators;

import domain.Student;
import utils.Constants;

public class StudentValidator implements Validator<Student> {
    @Override
    public void validate(Student entity) throws ValidationException {
        String errors="";

        if(!entity.getId().matches(Constants.NUMBER_FORMAT))
            errors+="Id nu e numar!! ";

        if(entity.getNume().equals("")){
            errors+="Studentul nu are nume!! ";
        }
        if(entity.getPrenume().equals("")){
            errors+="Studentul nu are prenume!! ";
        }
        if(entity.getGrupa()<=0){
            errors+="Grupa invalida!! ";
        }
        if(entity.getCadruDidacticIndrumatorLab().equals("")){
            errors+="Studentul nu are profesor!! ";
        }
        if(entity.getEmail().equals("")){
            errors+="Studentul nu are adresa de email!! ";
        }
        if(!entity.getEmail().contains("@")){
            errors+="Adresa de email invalida!! ";
        }
        if (errors!="")
            throw new ValidationException(errors);
    }
}
