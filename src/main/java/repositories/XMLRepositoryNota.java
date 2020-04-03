package repositories;

import domain.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import services.config.ApplicationContext;
import validators.NotaValidator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLRepositoryNota extends AbstractXMLRepository<Pair, Nota> {


    XMLRepositoryStudent srep;
    XMLRepositoryTema trep;

    public XMLRepositoryNota(String fileName, String fS, String fT) {
        super(new NotaValidator(), fileName);

        this.srep=new XMLRepositoryStudent(fS);
        this.trep=new XMLRepositoryTema(fT);

    }



    @Override
    protected Element createElementfromEntity(Document document, Nota nota) {
        Element element = document.createElement("nota");

        Element id_s = document.createElement("id_student");
        id_s.setTextContent(nota.getStudent().getId());
        element.appendChild(id_s);

        Element id_t = document.createElement("id_tema");
        id_t.setTextContent(nota.getTema().getId());
        element.appendChild(id_t);

        Element data = document.createElement("data");
        data.setTextContent(nota.getDate().toString());
        element.appendChild(data);

        Element profesor =document.createElement("profesor");
        profesor.setTextContent(nota.getProfesor());
        element.appendChild(profesor);

        Element valoare = document.createElement("valoare");
        valoare.setTextContent(((Float)nota.getValoare()).toString());
        element.appendChild(valoare);

        Element feedback =document.createElement("feedback");
        feedback.setTextContent(nota.getFeedBack());
        element.appendChild(feedback);

        return element;
    }

    @Override
    protected Nota createEntityFromElement(Element notaElement) {
    try{
        XMLRepositoryStudent studRepo = new XMLRepositoryStudent(ApplicationContext.getPROPERTIES().getProperty("data.studentsXML"));
        XMLRepositoryTema temaRepo = new XMLRepositoryTema(ApplicationContext.getPROPERTIES().getProperty("data.temeXML"));


        String id_s = notaElement.getElementsByTagName("id_student").item(0).getTextContent();

        String idTema = notaElement.getElementsByTagName("id_tema").item(0).getTextContent();

        String data = notaElement.getElementsByTagName("data").item(0).getTextContent();

        String profesor = notaElement.getElementsByTagName("profesor").item(0).getTextContent();

        String valoare = notaElement.getElementsByTagName("valoare").item(0).getTextContent();

        String feedback = notaElement.getElementsByTagName("feedback").item(0).getTextContent();

        Student s = (Student) studRepo.findOne(id_s);
        Tema t = (Tema) temaRepo.findOne(idTema);

        Nota n = new Nota(data, profesor, s, t);
        n.setFeedBack(feedback);
        n.setValoare(Float.parseFloat(valoare));
        return n;
    }catch(NumberFormatException | IllegalAccessException e){
        return null;
    }
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
