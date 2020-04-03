package repositories;

import domain.Entity;
import domain.Tema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validators.TemaValidator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLRepositoryTema extends AbstractXMLRepository<String, Tema> {
    public XMLRepositoryTema(String fT) {
        super(new TemaValidator(), fT);
    }


    @Override
    protected Element createElementfromEntity(Document document, Tema tema) {
        Element element = document.createElement("tema");

        element.setAttribute("id", tema.getId());

        Element descriere = document.createElement("descriere");
        descriere.setTextContent(tema.getDescriere());
        element.appendChild(descriere);

        Element deadline =document.createElement("deadlineWeek");
        deadline.setTextContent(((Integer)tema.getEndWeek()).toString());
        element.appendChild(deadline);

        Element startWeek = document.createElement("startWeek");
        startWeek.setTextContent(((Integer)tema.getStartWeek()).toString());
        element.appendChild(startWeek);

        return element;
    }

    @Override
    protected Tema createEntityFromElement(Element temaElement) {
        String id = temaElement.getAttribute("id");

        String descriere = temaElement.getElementsByTagName("descriere").item(0).getTextContent();

        String deadline = temaElement.getElementsByTagName("deadlineWeek").item(0).getTextContent();

        String startWeek = temaElement.getElementsByTagName("startWeek").item(0).getTextContent();

        Tema t = new Tema(id,descriere,Integer.parseInt(deadline));
        t.setStartWeek(Integer.parseInt(startWeek));
        return t;
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
