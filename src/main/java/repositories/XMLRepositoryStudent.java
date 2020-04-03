package repositories;

import domain.Entity;
import domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validators.StudentValidator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLRepositoryStudent extends AbstractXMLRepository<String, Student> {

    public XMLRepositoryStudent(String fileStud) {
        super(new StudentValidator(), fileStud);
    }


    @Override
    protected Element createElementfromEntity(Document document, Student student) {
        Element element = document.createElement("student");

        element.setAttribute("id", student.getId());

        Element nume = document.createElement("nume");
        nume.setTextContent(student.getNume());
        element.appendChild(nume);

        Element prenume = document.createElement("prenume");
        prenume.setTextContent(student.getPrenume());
        element.appendChild(prenume);

        Element grupa = document.createElement("grupa");
        grupa.setTextContent(((Integer) student.getGrupa()).toString());
        element.appendChild(grupa);

        Element email = document.createElement("email");
        email.setTextContent(student.getEmail());
        element.appendChild(email);

        Element profesor = document.createElement("profesor");
        profesor.setTextContent(student.getCadruDidacticIndrumatorLab());
        element.appendChild(profesor);

        return element;
    }


    @Override
    protected Student createEntityFromElement(Element element) {
        String id = element.getAttribute("id");

        String nume = element.getElementsByTagName("nume").item(0).getTextContent();

        String prenume = element.getElementsByTagName("prenume").item(0).getTextContent();

        String grupa = element.getElementsByTagName("grupa").item(0).getTextContent();

        String email = element.getElementsByTagName("email").item(0).getTextContent();

        String profesor = element.getElementsByTagName("profesor").item(0).getTextContent();

        Student s = new Student(id, nume, prenume, Integer.parseInt(grupa), email, profesor);
        return s;
    }


    @Override
    public Entity parseLine(String linie) {
        return null;
    }

    @Override
    public String outputString(Entity entity) {
        return null;
    }
}
